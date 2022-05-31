import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class LinearCongruentialGenerator {

    private int m, a, c, i;

    public LinearCongruentialGenerator(int modulo, int multiplier, int increment, int seed) {
        this.m = modulo;
        this.a = multiplier;
        this.c = increment;
        this.i = seed;
    }

    public int randomNumber() {
        i = (a * i + c) % m;
        return i;
    }

    public static int findRepeatingSequence(int[] array) {

        int sequenceLenght = 1;

        for (int startPos = 1; startPos < array.length; startPos++)
        {
            if (array[0] != array[startPos])
            {
                sequenceLenght++;
            }

            if (array[0] == array[startPos])
            {
                return sequenceLenght;
            }
        }
        return sequenceLenght;
    }

    public static void main(String[] args) throws IOException {

        int randomNumbers = 200;
        int maxSeedValue = 200;


        int[] arrayIncrement12 = new int[randomNumbers];
        int[] arrayIncrement13 = new int[randomNumbers];
        int[] periodLengthsIncrement12 = new int[maxSeedValue];
        int[] periodLengthsIncrement13 = new int[maxSeedValue];


        for(int i = 0; i < maxSeedValue; i++) {

            LinearCongruentialGenerator lcg1 = new LinearCongruentialGenerator(64, 5, 12, i+1);
            for (int j = 0; j < randomNumbers; j++) {
                arrayIncrement12[j] = lcg1.randomNumber();
                periodLengthsIncrement12[i] = findRepeatingSequence(arrayIncrement12);
            }

            LinearCongruentialGenerator lcg2 = new LinearCongruentialGenerator(64, 5, 13, i+1);
            for (int k = 0; k < randomNumbers; k++) {
                arrayIncrement13[k] = lcg2.randomNumber();
                periodLengthsIncrement13[i] = findRepeatingSequence(arrayIncrement13);
            }
        }

        try {
            PrintWriter printWriter = new PrintWriter(new File("Period_Lengths.csv"));

            printWriter.write("Seed,Increment12,Increment13");
            printWriter.println();

            for(int i = 0; i < periodLengthsIncrement12.length; i++) {
                printWriter.write(Integer.toString(i+1));
                printWriter.write(",");
                printWriter.write(Integer.toString(periodLengthsIncrement12[i]));
                printWriter.write(",");
                printWriter.write(Integer.toString(periodLengthsIncrement13[i]));
                printWriter.println();

            }

            printWriter.flush();
            printWriter.close();

        } catch (IOException ioException) {

        }

    }
}
