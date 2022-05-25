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

        for (int startPos = 0; startPos < array.length; startPos++) {

            for (int sequenceLength = 1; sequenceLength <= (array.length - startPos) / 2; sequenceLength++) {

                boolean sequencesAreEqual = true;
                for (int i = 0; i < sequenceLength; i++) {
                    if (array[startPos + i] != array[startPos + sequenceLength + i]) {
                        sequencesAreEqual = false;
                        break;
                    }
                }
                if (sequencesAreEqual) {
                    return sequenceLength;
                }
            }
        }
        return -1;
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
            PrintWriter printWriter = new PrintWriter(new File("/Users/kara/Desktop/Period_Lengths.csv"));

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
