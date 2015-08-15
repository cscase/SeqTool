package seqtool;

/**
 * @author scottcase
 * This class represents a .fasta file as an object. A fasta object will
 * contain all of the sequence objects found within a fasta file and their
 * types. It also can return some information about the file itself, such as
 * file size in bytes, path. Because the getMemberList() method returns a ref
 * to the seqMembers ArrayList, the class is not immutable.
 */

import sequence.DNASequence;
import sequence.RNASequence;
import sequence.Sequence;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

class Fasta {
    // The seqMembers ArrayList contains all of the Sequences from the file
    private ArrayList<Sequence> seqMembers;

    // A reference to the actual .fasta file
    private File fastaFile;

    // Does not have a no-arg constructor; requires a File at minimum.

    // Constructor when file is provided: reads file, parses it, creates objects
    public Fasta(File fastaFile) throws FileNotFoundException, ParseException {
        seqMembers = openFasta(fastaFile);
        this.fastaFile = fastaFile;
    }
  
/*
TODO: Create constructor for creating a new brand new fastaFile file with a
file name and one or more Sequence objects. The new object is written to a
new fastaFile file.
*/

    // This method opens a file and returns an ArrayList of Sequences
    public static ArrayList<Sequence> openFasta(File fastaFile)
            throws FileNotFoundException,
                   ParseException {
        ArrayList<Sequence> seqOut = new ArrayList<>();
        StringBuilder readBuffer = new StringBuilder();
        String header = "";
        int seqCount = 0;
        int linesRead = 0;

        Scanner input = new Scanner(fastaFile);

        while (input.hasNext()) {
            // Read a line
            String readQueue = input.nextLine();
            linesRead++;

            if (isHeader(readQueue)) {
                if (readBuffer.length() > 0) {
                    seqCount++;
                    seqOut.add(
                            parseToSequence(header, readBuffer.toString()));
                    flushBuffer(readBuffer);
                    header = readQueue.substring(1);
                }

                else {
                    if (!header.isEmpty()) {
                        throw new ParseException("Invalid .fasta format: " +
                                "There should only be one header line per " +
                                "sequence.",
                                linesRead);
                    }
                    header = readQueue.substring(1);
                }
            }
            else {
                // Not a header line, just append it to read buffer
                readBuffer.append(readQueue.toUpperCase());
            }

        }//while

        seqOut.add(parseToSequence(header, readBuffer.toString()));

        return seqOut;
    }

    private static void flushBuffer(StringBuilder readBuffer) {
        readBuffer.delete(0, readBuffer.length());
    }

    private static boolean isHeader(String line){
        return (line.charAt(0) == '>');
    }

    // Decides which type of Sequence is fitting and returns one
    private static Sequence parseToSequence(String header, String seq) {
        if (DNASequence.validateDNA(seq)) {
            return new DNASequence(header, seq);
        }
        if (RNASequence.validateRNA(seq)) {
            return new RNASequence(header, seq);
        }
        return new Sequence(header, seq);
    }

    // Returns a reference to the SeqMembers private ArrayList
    public ArrayList<Sequence> getMemberList() {
        return seqMembers;
    }

    // Returns a copy of the File reference
    public File getFile() {
        return new File(fastaFile.getPath());
    }

    // Returns file length in bytes
    public long getFileSize() {
        return fastaFile.length();
    }

    // Returns the name of the file itself
    public String getFileName() {
        return fastaFile.getName();
    }

    // Returns the number of member sequences contained in the Fasta
    public int getSeqCount() {
        return seqMembers.size();
    }

    public Sequence getMember(int i) {
        return seqMembers.get(i);
    }

}