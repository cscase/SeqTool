package sequence;

/**
 * @author scottcase
 * COSC 2033 Section AA, Summer 2015
 * Week 15
 * Personal programming assignment
 * Author Scott Case
 * Modified 08 / 19 / 2015
 *
Quick summary:
This program opens .fasta files (see wikipedia entry for info about this simple
file format) and parses their contents into an object class called Sequence,
or one of two subclasses for RNA or DNA sequences, as appropriate. Each
sequence is displayed on its own tab, and information about the selected
sequence is displayed about the tab pane. I'm planning to add more
functionality in the future that will make it more useful, so this is just a
starting point.

I'm submitting the .jar itself, which contains both the .class files and the
source code for the project. The source code is in the src folder in the .jar.
I'm also submitting some .fasta files that can be opened for testing the
project and seeing how it works. Finally, I'm also including UML diagrams.
 */

import java.util.regex.Pattern;

/** This class is a subclass of Sequence, and applies specifically to RNA
 * sequences.
 */


public class RNASequence extends Sequence {

    //Constructor if no header specified. Class doesn't have a no-arg constr.
    public RNASequence(String seq) throws IllegalArgumentException {
        super(seq);
        if (!validateRNA(seq.toUpperCase())) {
            throw new IllegalArgumentException(
                    "RNASequence objects may only contain A, C, G, U.");
        }
    }

    // Constructor when header is specified
    public RNASequence(String header, String seq) {
        super(header, seq);
        if (!validateRNA(seq.toUpperCase())) {
            throw new IllegalArgumentException(
                    "RNASequence objects may only contain A, C, G, U.");
        }
    }


    // Validate RNA sequence (4 nucleotides allowed, no extraneous chars)
    public static boolean validateRNA(String seq) {
        Pattern rnaValidation = Pattern.compile("[^ACGU]");
        return !rnaValidation.matcher(seq).find();
    }

    // static method to return the complement of the string arg
    public static String Complement(String rna) throws
            IllegalArgumentException {
        StringBuilder complement = new StringBuilder();
        for (int i = 0; i < rna.length(); i++) {
            complement.append(nComplement(rna.charAt(i)));
        }
        return complement.toString();
    }

    // static method returns the complement of a char arg
    private static char nComplement(char n) throws IllegalArgumentException {
        n = Character.toUpperCase(n);
        if (n == 'A') return 'U';
        if (n == 'C') return 'G';
        if (n == 'G') return 'C';
        if (n == 'U') return 'A';
        throw new IllegalArgumentException(
                "Invalid argument " + n + ". Valid arguments are A, C, G, U.");
    }

    @Override
    public String getType() {
        return "RNA";
    }

    public int aCount() {
        int count = 0;
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == 'A') {
                count++;
            }
        }
        return count;
    }

    public int cCount() {
        int count = 0;
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == 'C') {
                count++;
            }
        }
        return count;
    }

    public int gCount() {
        int count = 0;
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == 'G') {
                count++;
            }
        }
        return count;
    }

    public int uCount() {
        int count = 0;
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == 'U') {
                count++;
            }
        }
        return count;
    }

    // return the percent GC content in the sequence
    public double gcContent() {
        double gcContent = (double) (this.gCount() + this.cCount())
                / (double) (this.length());
        gcContent *= 100;
        return Double.valueOf(String.format("%.6f", gcContent));
    }

}// RNASequence