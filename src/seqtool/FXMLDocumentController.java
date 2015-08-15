package seqtool;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import sequence.Sequence;

public class FXMLDocumentController implements Initializable {

  @FXML
  private MenuBar menuBar;
  
  @FXML
  private TabPane tabPane;
  
  @FXML
  private TextField tfHeader;
   
  private FileChooser fileChooser = new FileChooser();
  
  @FXML
  private void MenuFileOpenAction(ActionEvent event) {
    fileChooser.setTitle ("Open a File");
    File fastaFile = fileChooser.showOpenDialog(null);
    if (fastaFile != null) {
      try {
        Fasta fasta = new Fasta(fastaFile);
        for (int i = 0; i < fasta.getSeqCount(); i++) {
          // Name the tab with the filename plus "(# of #)" if there
          //    are multiple sequences in this file
          String tabName = fasta.getSeqCount() > 1 
                  ? fasta.getFileName() + " (" + (i+1) + " of " 
                        + fasta.getSeqCount() + ")"
                  : fasta.getFileName();
          
          // Create a new tab using the tab name and the sequence          
          tabPane.getTabs().add(new SeqTab(tabName, fasta.getMember(i)));
        }
      } catch (FileNotFoundException | ParseException ex) {
        System.out.println("OH NOES: "+ex.getMessage());
      }
    }
  }
  
  @FXML
  private void MenuFileCloseAction(ActionEvent event) {
    if (!tabPane.getTabs().isEmpty()){
      tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
    }
  }
    
  @FXML
  private void MenuFileExitAction(ActionEvent event) {
      System.exit(0);
  }

  @Override
  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    tabPane.getSelectionModel().selectedItemProperty().addListener( 
            (v, oldValue,newValue) -> {
              updateInfoBox();
              });

  }
  
  public void updateInfoBox() {
    // Show the header for the sequence
    int currentTabIndex = tabPane.getSelectionModel().getSelectedIndex();
    SeqTab currentTab = (SeqTab)tabPane.getTabs().get(currentTabIndex);
    Sequence currentSeq = currentTab.getTabSeq();
    tfHeader.setText(currentSeq.getHeader());
  }
}