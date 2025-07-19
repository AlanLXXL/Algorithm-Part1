package assignment0;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        // Initialise words and index
        String champion = null;
        int i = 1;

        // Loop through words
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = word;
            }
            i++;
        }

        // Output the champion
        if (champion != null) {
            StdOut.println(champion);
        }

    }
}
