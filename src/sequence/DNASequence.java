package sequence;
/*
DNASequence is a sequence of DNA nucleotides (A, C, G, T) and associated
methods.
*/

import java.util.regex.Pattern;

public class DNASequence extends Sequence {
    
    //Constructor if no header specified. Class doesn't have a no-arg constr.
    public DNASequence (String seq) throws IllegalArgumentException {
        super(seq);
        if(!validateDNA(seq.toUpperCase())) {
            throw new IllegalArgumentException(
                    "DNASequence objects may only contain A, T, G, C.");
        }
    }
    
    // Constructor when header is specified
    public DNASequence(String header, String seq) {
        super(header, seq);
        if(!validateDNA(seq.toUpperCase())) {
            throw new IllegalArgumentException(
                    "\nDNASequence objects may only contain A, T, G, C.");
        }
    }
    
    // Validate DNA sequence - return true if no illegal chars found
    public static boolean validateDNA(String seq) {
        Pattern dnaValidation = Pattern.compile("[^ACGT]");
        return !dnaValidation.matcher(seq).find();
    }
    
    @Override
    public String getType() {
      return "DNA";
    }
   
    // aCount(): counts the number of A nucleotides in sequence
    public int aCount() {
        int count = 0;
        for (int i = 0; i < seq.length(); i++){
            if (seq.charAt(i) == 'A') {
                count++;
            }
        }
        return count;
    }
    
    // cCount(): counts the number of C nucleotides in sequence
    public int cCount() {
        int count = 0;
        for (int i = 0; i < seq.length(); i++){
            if (seq.charAt(i) == 'C') {
                count++;
            }
        }
        return count;
    }

    // gCount(): counts the number of G nucleotides in sequence
    public int gCount() {
        int count = 0;
        for (int i = 0; i < seq.length(); i++){
            if (seq.charAt(i) == 'G') {
                count++;
            }
        }
        return count;
    }
    
    // tCount(): counts the number of T nucleotides in sequence
    public int tCount() {
        int count = 0;
        for (int i = 0; i < seq.length(); i++){
            if (seq.charAt(i) == 'T') {
                count++;
            }
        }
        return count;
    }
    
    // return the total number of each nucleotide in the sequence, for all 4
    // (I wrote this method for a problem on Rosalind.info, commenting it out)
//    public String nComposition() {
//        return String.format("%d %d %d %d", 
//        this.aCount(), this.cCount(), this.gCount(), this.tCount());
//    }
    
    // return the percent GC content in the sequence
    public double gcContent() {
        double gcContent = (double)(this.gCount() + this.cCount())
                / (double)(this.length());
        gcContent *= 100;
        return Double.valueOf(String.format("%.6f", gcContent));
    }
    
    // static method to return the complement of the string arg
    public static String Complement(String dna) throws IllegalArgumentException
    {
        StringBuilder complement = new StringBuilder();
        for (int i = 0; i < dna.length(); i++){
            complement.append(nComplement(dna.charAt(i)));
        }
        return complement.toString();
    }

    // static method returns the complement of a char arg
    private static char nComplement(char n) throws IllegalArgumentException {
        n = Character.toUpperCase(n);
        if (n=='A') return 'T';
        if (n=='C') return 'G';
        if (n=='G') return 'C';
        if (n=='T') return 'A';
        throw new IllegalArgumentException(
                "Invalid argument "+n+". Valid arguments are A, C, G, T.");
    }
    
}// DNASequence