package sequence;
/*
AASequence is a prototype class that I plan to re-write later, using a 
class for the AAs themselves. So this class is just a placeholder for now.
*/

import java.util.regex.Pattern;

public class AASequence extends Sequence {
    
    //Constructor if no header specified. Class doesn't have a no-arg constr.
    public AASequence (String seq) throws IllegalArgumentException {
        super(seq);
        if(!validateAA(seq.toUpperCase())) {
            throw new IllegalArgumentException(
 "AASequence objects must contain only alpha characters not B, J, O, U, X, Z.");
        }
    }
    
    // Constructor when header is specified
    public AASequence(String header, String seq) {
        super(header, seq);
        if(!validateAA(seq.toUpperCase())) {
            throw new IllegalArgumentException(
 "AASequence objects must contain only alpha characters not B, J, O, U, X, Z.");
        }
    }
    
    // Validate AA sequence (return true if no illegal characters found)
    public static boolean validateAA(String seq) {
        Pattern aaValidation = Pattern.compile("^[A-Z&&[^BJOUXZ]]");
        return !aaValidation.matcher(seq).find();
    }
    
    @Override
    public String getType() {
      return "AA";
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
    
}// AASequence