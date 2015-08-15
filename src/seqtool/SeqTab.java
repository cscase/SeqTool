package seqtool;

import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import sequence.Sequence;

/**
 * @author scottcase
 *         This class extends the JavaFX Tab class, and defines a tab to
 *         display a
 *         Sequence object for our application.
 */
class SeqTab extends Tab {
    // This is the Sequence associated with this UI tab.
    private final Sequence seq;

    // The Pane and TextArea where the Sequence will be displayed.
    private VBox seqPane;

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

    public Sequence getTabSeq() {
        return this.seq;
    }

    private void setup() {
        setTabColor();
        addContextMenu();
        TextArea taSeq = addTextArea();

        // Write the text of the sequence into the TextArea
        taSeq.appendText(this.seq.getSeq());

        // Pull the scroll bar back up to the start, since it autoscrolls down
        taSeq.setScrollTop(Double.MAX_VALUE);
    }

    private TextArea addTextArea() {
        TextArea taSeq = new TextArea();

        // Assign the sequence TextArea as the content of this tab
        super.setContent(taSeq);

        taSeq.setEditable(false);
        taSeq.setWrapText(true);
        taSeq.setPadding(new Insets(5, 5, 5, 5));

        return taSeq;
    }

    private void addContextMenu() {
        MenuItem tabClose = new MenuItem("Close");
        tabClose.setOnAction(e -> this.getTabPane().getTabs().remove(this));
        ContextMenu tabMenu = new ContextMenu(tabClose);
        this.setContextMenu(tabMenu);
    }

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
