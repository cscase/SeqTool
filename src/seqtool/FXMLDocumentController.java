package seqtool;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import sequence.Sequence;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private MenuBar menuBar;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label textSeqType;

    @FXML
    private TextField tfHeader;

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

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    updateInfoBox();
                });

    }

    private void updateInfoBox() {
        int currentTabIndex = tabPane.getSelectionModel().getSelectedIndex();
        SeqTab currentTab = (SeqTab) tabPane.getTabs().get(currentTabIndex);
        Sequence currentSeq = currentTab.getTabSeq();

        // Display header
        tfHeader.setText(currentSeq.getHeader());

        // Display sequence type
        textSeqType.setText(currentSeq.getType());

    }
}