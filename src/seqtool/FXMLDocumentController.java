/**
 * @author scottcase
 */

package seqtool;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sequence.DNASequence;
import sequence.RNASequence;
import sequence.Sequence;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private TextField tfHeader;

    @FXML
    private Label textSeqType;

    @FXML
    private Label textSeqLength;

    @FXML
    private Label labelGCContent;

    @FXML
    private Label textGCContent;

    @FXML
    private TabPane tabPane;

    @FXML
    // Event handler for menu bar item File -> Open
    private void MenuFileOpenAction() {
        fileChooser.setTitle("Open a File");
        File fastaFile = fileChooser.showOpenDialog(null);
        if (fastaFile != null) {
            try {
                Fasta fasta = new Fasta(fastaFile);
                for (int i = 0; i < fasta.getSeqCount(); i++) {
                    // Name the tab with the filename plus "(# of #)" if there are multiple sequences in this file
                    String tabName = fasta.getSeqCount() > 1
                            ? fasta.getFileName() + " (" + (i + 1) + " of " + fasta.getSeqCount() + ")"
                            : fasta.getFileName();

                    // Create a new tab using the tab name and the sequence
                    tabPane.getTabs().add(new SeqTab(tabName, fasta.getMember
                            (i)));
                }
            } catch (FileNotFoundException | ParseException ex) {
                System.out.println("OH NOES: " + ex.getMessage());
            }
        }
    }

    @FXML
    // Event handler for menu bar File -> Close
    private void MenuFileCloseAction() {
        if (!tabPane.getTabs().isEmpty()) {
            tabPane.getTabs().remove(tabPane.getSelectionModel()
                    .getSelectedIndex());
            updateInfoBox();
        }
    }

    @FXML
    // Event handler for menu bar File -> Exit
    private void MenuFileExitAction() {
        System.exit(0);
    }

    @FXML
    private void tfHeaderUpdateAction() {
        int currentTabIndex = tabPane.getSelectionModel().getSelectedIndex();
        SeqTab currentTab = (SeqTab) tabPane.getTabs().get(currentTabIndex);
        Sequence currentSeq = currentTab.getTabSeq();

        currentSeq.setHeader(tfHeader.getText());
    }

    // This routine refreshes the info displayed at the top, above the tab pane
    private void updateInfoBox() {
        int currentTabIndex = tabPane.getSelectionModel().getSelectedIndex();
        if (currentTabIndex == -1) {
            clearInfoBox();
            hideTypeInfo();
            return;
        }
        SeqTab currentTab = (SeqTab) tabPane.getTabs().get(currentTabIndex);
        Sequence currentSeq = currentTab.getTabSeq();

        // Display header
        tfHeader.setText(currentSeq.getHeader());
        tfHeader.setEditable(true);

        // Display sequence type
        textSeqType.setText(currentSeq.getType());

        // Display sequence length
        textSeqLength.setText(Integer.toString(currentSeq.length()));

        // Refresh type-specific data in info box
        updateTypeInfo(currentSeq);

    }

    // This method updates the type-specific section of the info pane (DNA, RNA)
    private void updateTypeInfo(Sequence seq) {
        switch (seq.getType()) {
            case "DNA":
                labelGCContent.setVisible(true);
                textGCContent.setVisible(true);
                textGCContent.setText(Double.toString(((DNASequence) seq)
                        .gcContent()) + "%");
                break;
            case "RNA":
                labelGCContent.setVisible(true);
                textGCContent.setVisible(true);
                textGCContent.setText(Double.toString(((RNASequence) seq)
                        .gcContent()) + "%");
                break;
            default:
                hideTypeInfo();
        }
    }

    // This method hides the type-specific info pane items when
    // they're not applicable
    private void hideTypeInfo() {
        labelGCContent.setVisible(false);
        textGCContent.setVisible(false);
        textGCContent.setText("");
    }

    // This method blanks the info box, used when no sequence is selected
    private void clearInfoBox() {
        tfHeader.setText("");
        tfHeader.setEditable(false);
        textSeqType.setText("");
        textSeqLength.setText("");
        textGCContent.setText("");
    }

    @FXML
    // Menu bar Help - About event handler
    public void menuHelpAboutAction() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("About this tool");
        window.setResizable(false);

        String about = "SeqTool\nAuthor: Scott Case\nSeqTool is a tool for viewing DNA/RNA/other sequences and " +
                "learning useful things about them. Moreover, it is an exercise in programming for me. It works with" +
                ".fasta files. (See the .fasta file format entry on wikipedia for a description of this format.)";

        TextArea taAboutBox = new TextArea(about);
        taAboutBox.setWrapText(true);

        Button btClose = new Button("Close");
        btClose.setDefaultButton(true);
        btClose.setOnAction(e -> window.close());
        BorderPane box = new BorderPane();
        box.setCenter(taAboutBox);
        box.setBottom(btClose);
        BorderPane.setAlignment(btClose, Pos.CENTER);
        Scene scene = new Scene(box, 400, 380);
        window.setScene(scene);
        window.show();
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    updateInfoBox();
                });
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }
}