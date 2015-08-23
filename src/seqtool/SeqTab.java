package seqtool;

import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import sequence.DNASequence;
import sequence.RNASequence;
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
    TextArea taSeq;

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
        taSeq = addTextArea();

        // Write the text of the sequence into the TextArea
        taSeq.appendText(this.seq.getSeq());

        // Pull the scroll bar back up to the start, since it autoscrolls down
        taSeq.setScrollTop(Double.MAX_VALUE);
    }

    // This method adds the sequence display TextArea to the tab and sets it up
    private TextArea addTextArea() {
        TextArea taSeq = new TextArea();

        super.setContent(taSeq);

        taSeq.setEditable(false);
        taSeq.setWrapText(true);
        taSeq.setPadding(new Insets(5, 5, 5, 5));
        if (this.getTabSeq().getType() == "DNA" || this.getTabSeq().getType() == "RNA") {
            addTypeSpecificContextMenu(taSeq);
        }

        return taSeq;
    }

    private void addTypeSpecificContextMenu(TextArea taSeq) {
        MenuItem getRevComplementToClipboard = new MenuItem("Rev. complement -> Clipboard");
        getRevComplementToClipboard.setOnAction(e -> getRevComplementToClipboardAction());
        MenuItem getRevComplementToNewTab = new MenuItem("Rev. complement -> New tab");
        getRevComplementToNewTab.setOnAction(e -> getRevComplementToNewTabAction());
        ContextMenu taSeqContextMenu = new ContextMenu(getRevComplementToClipboard, getRevComplementToNewTab);
        taSeq.setContextMenu(taSeqContextMenu);
    }

    private void getRevComplementToClipboardAction() {
        String targetSeq = (this.taSeq.getSelectedText().isEmpty())
                ? this.getTabSeq().getSeq()
                : this.taSeq.getSelectedText();
        String reverseComplement;
        if (this.getTabSeq().getType() == "DNA") {
            reverseComplement = DNASequence.revComplement(targetSeq);
        }
        else {
            reverseComplement = RNASequence.revComplement(targetSeq);
        }
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(reverseComplement);
        clipboard.setContent(content);
    }

    private void getRevComplementToNewTabAction() {
        String targetSeq = (this.taSeq.getSelectedText().isEmpty())
                ? this.getTabSeq().getSeq()
                : this.taSeq.getSelectedText();
        String reverseComplement;
        if (this.getTabSeq().getType() == "DNA") {
            reverseComplement = DNASequence.revComplement(targetSeq);
        }
        else {
            reverseComplement = RNASequence.revComplement(targetSeq);
        }
        this.getTabPane().getTabs().add(new SeqTab("untitled seq", Fasta.parseToSequence("", reverseComplement)));
    }

    // Context menu for tab
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