package seqtool;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
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
    private void MenuFileOpenAction() {
        fileChooser.setTitle("Open a File");
        File fastaFile = fileChooser.showOpenDialog(null);
        if (fastaFile != null) {
            try {
                Fasta fasta = new Fasta(fastaFile);
                for (int i = 0; i < fasta.getSeqCount(); i++) {
                    // Name the tab with the filename plus "(# of #)" if there
                    //    are multiple sequences in this file
                    String tabName = fasta.getSeqCount() > 1
                            ? fasta.getFileName() + " (" + (i + 1) + " of "
                            + fasta.getSeqCount() + ")"
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
    private void MenuFileCloseAction() {
        if (!tabPane.getTabs().isEmpty()) {
            tabPane.getTabs().remove(tabPane.getSelectionModel()
                    .getSelectedIndex());
            updateInfoBox();
        }
    }

    @FXML
    private void MenuFileExitAction() {
        System.exit(0);
    }

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

        // Display sequence type
        textSeqType.setText(currentSeq.getType());

        // Display sequence length
        textSeqLength.setText(Integer.toString(currentSeq.length()));

        // Refresh type-specific data in info box
        updateTypeInfo(currentSeq);

    }

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

    private void hideTypeInfo() {
        labelGCContent.setVisible(false);
        textGCContent.setVisible(false);
        textGCContent.setText("");
    }

    private void clearInfoBox() {
        tfHeader.setText("");
        textSeqType.setText("");
        textSeqLength.setText("");
        textGCContent.setText("");
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