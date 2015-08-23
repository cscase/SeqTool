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

/** This class extends Sequence and describes a sequence that is specifically a
 * DNA sequence, meaning it has nucleotides A, C, G, and T.
 */

public class DNASequence extends Sequence {

    //Constructor if no header specified. Class doesn't have a no-arg constr.
    public DNASequence(String seq) throws IllegalArgumentException {
        super(seq);
        if (!validateDNA(seq.toUpperCase())) {
            throw new IllegalArgumentException(
                    "DNASequence objects may only contain A, T, G, C.");
        }
    }

    // Constructor when header is specified
    public DNASequence(String header, String seq) {
        super(header, seq);
        if (!validateDNA(seq.toUpperCase())) {
            throw new IllegalArgumentException(
                    "\nDNASequence objects may only contain A, T, G, C.");
        }
    }

    // Validate DNA sequence - return true if no illegal chars found
    public static boolean validateDNA(String seq) {
        Pattern dnaValidation = Pattern.compile("[^ACGT]");
        return !dnaValidation.matcher(seq).find();
    }

    // return the complement of a string
    public static String complement(String dna) throws
            IllegalArgumentException {
        StringBuilder complement = new StringBuilder();
        for (int i = 0; i < dna.length(); i++) {
            complement.append(nComplement(dna.charAt(i)));
        }
        return complement.toString();
    }

    // return the reverse complement of a string
    public static String revComplement(String dna){
        StringBuilder reverse = new StringBuilder(dna).reverse();
        return complement(reverse.toString());
    }

    // static method returns the complement of a char arg
    private static char nComplement(char n) throws IllegalArgumentException {
        n = Character.toUpperCase(n);
        if (n == 'A') return 'T';
        if (n == 'C') return 'G';
        if (n == 'G') return 'C';
        if (n == 'T') return 'A';
        throw new IllegalArgumentException(
                "Invalid argument " + n + ". Valid arguments are A, C, G, T.");
    }

    @Override
    public String getType() {
        return "DNA";
    }

    // aCount(): counts the number of A nucleotides in sequence
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

    public int tCount() {
        int count = 0;
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == 'T') {
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

}// DNASequence