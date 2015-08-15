
package sequence;

// The Sequence class is a generic object that can hold any kind of sequence.
// Subclasses will extend this class with specific methods for DNA, RNA, AAs.

public class Sequence {
    protected final String seq;
    private final String header;
    
    //Constructor if no header specified. Class doesn't have a no-arg constr.
    public Sequence(String seq) {
        this.seq = seq.toUpperCase();
        this.header = "";
    }
    
    //Constructor for specifying a header.
    public Sequence(String header, String seq) {
        this.seq = seq.toUpperCase();
        this.header = header;
    }
    
    public String getType() {
      return "Sequence";
    }

    public String getHeader() {
        return header;
    }

    // return the contents of the entire DNA sequence
    public String getSeq() {
        return seq;
    }
    
    // This overload for getSeq() returns a portion of the sequence within a
    //    specified range. Elements are counted beginning at 1. Arguments
    //    must be positive, nonzero integers. If the start point is higher
    //    than the end point, the bases will be returned in reverse order.

    public String getSeq(int start, int end) throws IllegalArgumentException {
        validateGetSeqArgs(start, end);

        StringBuilder seqOut = new StringBuilder();
        
        // dir represents the direction to traverse in
        int dir = 1;                 // 1 is the default (positive traversal)
        if (start == end) dir = 0;   // 0 if start and end are equal
        if (start > end) dir = -1;   // -1 if start > end (negative traversal)
        
        // set our start point -1, because array indices start at 0, not 1
        int i = start - 1;
        
        // do-while executes at least once, in case start == end
        do {            
            seqOut.append(seq.charAt(i));
            i += dir;
        }
        while (i != (end-1) + dir);
        
        // Return result as a string
        return seqOut.toString();
    }

    private void validateGetSeqArgs(int start, int end) {
        // Throw an exception if either argument is zero or negative
        if ((start * end <= 0) || (start + end < 0)) {
            throw new IllegalArgumentException("Invalid arguments: "+
                    start+", "+end+". Start and end points must be positive"+
                    ", nonzero integers.");
        }
        // Throw an exception if one or more arguments out of range
        if ((start > this.length()) || (end > this.length())) {
            throw new IllegalArgumentException("One or more arguments out"+
                    " of range: "+start+", "+end+". Sequence length is "+
                    this.length()+".");
        }
    }

    // length(): method returns the total number of characters in sequence
    public int length(){
        return seq.length();
    }
    
}// Sequence