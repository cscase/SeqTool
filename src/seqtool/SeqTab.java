package seqtool;
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

import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import sequence.Sequence;

/**
 *         This class extends the JavaFX Tab class, and defines a tab to
 *         display a Sequence object for our application. It associates a
 *         specific sequence object with each tab and names the tabs
 *         based on the file name.
 */
class SeqTab extends Tab {
    // Sequence associated with this UI tab.
    private final Sequence seq;

    // Constructors (2): A Sequence must be provided. Tab label text is
    // optional.
    public SeqTab(Sequence aSeq) {
        super();
        this.seq = aSeq;
        setup();
    }

    public SeqTab(String text, Sequence aSeq) {
        super(text);
        this.seq = aSeq;
        setup();
    }

    // This getter returns a ref. to the sequence associated with the tab
    public Sequence getTabSeq() {
        return this.seq;
    }

    // This method performs some initialization tasks and is called by both of
    // the constructors after they finish

    private void setup() {
        setTabColor();
        addContextMenu();
        TextArea taSeq = addTextArea();

        // Write the text of the sequence into the TextArea
        taSeq.appendText(this.seq.getSeq());

        // Pull the scroll bar back up to the start, since it autoscrolls down
        taSeq.setScrollTop(Double.MAX_VALUE);
    }

    // This method adds the sequence display TextArea to the tab ans sets it up
    private TextArea addTextArea() {
        TextArea taSeq = new TextArea();

        super.setContent(taSeq);

        taSeq.setEditable(false);
        taSeq.setWrapText(true);
        taSeq.setPadding(new Insets(5, 5, 5, 5));

        return taSeq;
    }

    // This method adds a context menu to the tab with a "Close" option
    // I later figured out how to add the 'X' to the tabs themselves for
    // closing, but I figured it doesn't hurt to have another option and left
    // this in.

    private void addContextMenu() {
        MenuItem tabClose = new MenuItem("Close");
        tabClose.setOnAction(e -> this.getTabPane().getTabs().remove(this));
        ContextMenu tabMenu = new ContextMenu(tabClose);
        this.setContextMenu(tabMenu);
    }

    // This method sets the color of the tab based on the type of sequence
    // associated with it
    private void setTabColor() {
        // Set the tab color based on what type of sequence it holds
        switch (seq.getType()) {
            case "DNA":
                this.setStyle("-fx-background-color: lightgreen;");
                break;
            case "RNA":
                this.setStyle("-fx-background-color: pink;");
                break;
            case "Sequence":
                this.setStyle("-fx-background-color: lightblue;");
                break;
        }
    }
}