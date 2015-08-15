package seqtool;

/**
 *
 * @author scottcase
 * This class represents a .fasta file as an object. A fasta object will
 * contain all of the sequence objects found within a fasta file and their
 * types. It also can return some information about the file itself, such as
 * file size in bytes, path. Because the getMemberList() method returns a ref
 * to the seqMembers ArrayList, the class is not immutable.
 */

import sequence.Sequence;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import sequence.DNASequence;
import sequence.RNASequence;

import static sequence.DNASequence.validateDNA;
import static sequence.RNASequence.validateRNA;

public class Fasta {
  // The seqMembers ArrayList contains all of the Sequences from the file
  private ArrayList<Sequence> seqMembers;
  
  // A reference to the actual .fasta file
  private File fastaFile;
  
  // Does not have a no-arg constructor; requires a File at minimum.
  
  // Constructor when file is provided: reads file, parses it, creates objects
  public Fasta (File fastaFile) throws FileNotFoundException, ParseException {
      seqMembers = openFasta(fastaFile);
      this.fastaFile = fastaFile;    
  }
  
/*
TODO: Create constructor for creating a new brand new fastaFile file with a
file name and one or more Sequence objects. The new object is
written to a new fastaFile file.
*/

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
    

// This method opens a file, parses it and returns an ArrayList of Sequences
  public static ArrayList<Sequence> openFasta(File fastaFile) 
              throws FileNotFoundException, ParseException {    
    ArrayList<Sequence> seqOut = new ArrayList<>();    // to be returned
    StringBuilder readBuffer = new StringBuilder();    // seq read buffer
    String header = "";
    int seqCount = 0;
    int linesRead = 0;

    Scanner input = new Scanner(fastaFile);
    
    while (input.hasNext()) {
      // Read a line
      String readQueue = input.nextLine();
      linesRead++;

      // Is it a header line?
      if (readQueue.charAt(0) == '>') {
        // And if so, do we already have seq data pending?
        if (readBuffer.length() > 0) {
          // If so, increment the number of sequences in the file
          seqCount++;

          // Then make an object out of the existing header and sequence
          seqOut.add(
            parseToSequence(header, readBuffer.toString()));

          // Clear out the read buffer
          readBuffer.delete(0, readBuffer.length()-1);

          // Then put this new header line in the header string
          header = readQueue.substring(1);
        }

        else {
          // Line is a header, but we don't yet have any seq data in buffer

          // If this was the second header line in a row, throw an exception
          if (!header.isEmpty()) {
            throw new ParseException("Invalid .fasta format: " + 
                    "There should only be one header line per sequence.", 
                    linesRead);
          }

          // Otherwise, hold this line in the header string
          header = readQueue.substring(1);
        }
      }

      else {
        // The line was not a header line, so just append it to readBuffer
        readBuffer.append(readQueue.toUpperCase());
      }

    }//while
    seqOut.add(parseToSequence(header, readBuffer.toString()));

    return seqOut;
  } // openFasta method
  
  // This method decides which type of Sequence is fitting and returns one
  public static Sequence parseToSequence(String header, String seq) {
    if (validateDNA(seq)) {
      return new DNASequence(header, seq);
    }
    if (validateRNA(seq)) {
      return new RNASequence(header, seq);
    }
    return new Sequence(header, seq);
  }
    
} // Fasta class