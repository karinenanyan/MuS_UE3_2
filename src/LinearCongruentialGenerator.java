import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class LinearCongruentialGenerator {

    private int m, a, c, i;

    public LinearCongruentialGenerator(int modulo, int multiplier, int increment, int seed) {
        this.m = modulo;
        this.a = multiplier;
        this.c = increment;
        this.i = seed;
    }

    public double randomNumber() {
        i = (a * i + c) % m;
        return i;
    }

    // Method to find the period lengths of the generated random numbers

    public static int findRepeatingSequence(double[] array) {

        int sequenceLength = 1;

        for (int startPos = 1; startPos < array.length; startPos++)
        {
            if (array[0] != array[startPos])
            {
                sequenceLength++;
            }

            if (array[0] == array[startPos])
            {
                return sequenceLength;
            }
        }
        return sequenceLength;
    }

    public static void main(String[] args) throws IOException {


        // Finding out the period length of the random number generator (with 100 numbers being generated)
        // for seed values of 1 to 200, each with c = 12 and c = 13

        int randomNumbers = 100;
        int maxSeedValue = 200;


        double[] arrayIncrement12 = new double[randomNumbers];
        double[] arrayIncrement13 = new double[randomNumbers];
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

        // Exporting the period lengths for the generated random numbers
        // for seed values of 1 to 200, each with c = 12 and c = 13 for the purposes of analysis

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

        // Normalizing the generated random numbers to the range [0,1].

        double max = Arrays.stream(arrayIncrement12).max().getAsDouble();
        double min = Arrays.stream(arrayIncrement12).min().getAsDouble();

        for(int i = 0; i < arrayIncrement12.length; i++) {
            arrayIncrement12[i] = (arrayIncrement12[i] - min)/(max - min);
        }

        // Exporting the normalized random numbers for the purposes of the analysis

        try {
            PrintWriter printWriter = new PrintWriter(new File("Normalized_Random_numbers.csv"));

            printWriter.write("Number");
            printWriter.println();

            for(int i = 0; i < arrayIncrement12.length; i++) {
                printWriter.write(Double.toString(arrayIncrement12[i]));
                printWriter.println();

            }

            printWriter.flush();
            printWriter.close();

        } catch (IOException ioException) {

        }

    }
}
