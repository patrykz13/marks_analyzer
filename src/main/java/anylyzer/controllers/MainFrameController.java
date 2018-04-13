package anylyzer.controllers;

import anylyzer.AnalyzerApp;
import parser.ResponseCardSet;
import parser.Test;
import parser.XLSXParser;
import parser.XmlParser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class MainFrameController  implements Initializable {


    String xlsxPath;
    String xmlPath;
    @FXML private TableView<Test> xmlTable;
    @FXML private TableView<ResponseCardSet> xlsxTable;
    @FXML private TableColumn<ResponseCardSet, String> tableColumnXLSX ;
    @FXML private TableColumn<Test, Integer> tableColumnQuestionsXML;
    @FXML private TableColumn<Test, String> tableColumTestXML;
    private ResponseCardSet responseCardSet;
    private Test test;



    @FXML
    private void buttonChooseXLSXFile_onAction(){
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users\\Patryk Zdral\\IdeaProjects\\zad1StudentMarksAnalyzer\\files"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xlsx Files","*.xlsx"));
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile !=null){
            xlsxPath = selectedFile.toString();
            XLSXParser x = new XLSXParser();
            ObservableList<ResponseCardSet> data = xlsxTable.getItems();
            data.add( x.readFromXlsxFile(xlsxPath));
        }

    }

    @FXML
    private void buttonChooseXMLFile_onAction(){
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users\\Patryk Zdral\\IdeaProjects\\zad1StudentMarksAnalyzer\\files"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xlsx Files","*.xml"));
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile !=null){
            xmlPath = selectedFile.toString();
            XmlParser x = new XmlParser();
            ObservableList<Test> data = xmlTable.getItems();

            data.add( x.readFromXMLFile(xmlPath));
        }

    }

    @FXML
    private void buttonCreateTest_onAction() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/TestCreateFrame.fxml"));
            Parent root1 = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(InitApplicationController.class.getName()).log(Level.SEVERE, null, ioEcx);

        }
    }

    @FXML
    private void buttonShowHistograms_onAction(){
        Parent root;
        try {

            if(responseCardSet.getTestName().toLowerCase().contains(test.getTestName().toLowerCase())&&test!=null&&responseCardSet!=null){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/HistogramFrame.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                HistogramController controller  =
                        fxmlLoader.getController();
                controller.initData(xlsxTable.getSelectionModel().getSelectedItem(),xmlTable.getSelectionModel().getSelectedItem());

                stage.show();
            }
            else{
                showMessageBox(Alert.AlertType.ERROR, "Błąd","choose correct XML AND XLSX file").showAndWait();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }


    }
    @FXML
    void buttonCreateNewResponseCard_onAction(){
        try {

            if(xmlTable.getSelectionModel().getSelectedItem()!=null){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/AnswerCardCreate.fxml"));
                Parent root1 = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                AnswerCardController controller  =
                        loader.getController();
                controller.init(xmlTable.getSelectionModel().getSelectedItem());
                stage.show();
            }
            else{
               showMessageBox(Alert.AlertType.ERROR, "Błąd","chooseXMLFileFromList").showAndWait();
            }


        } catch (IOException ioEcx) {
            Logger.getLogger(InitApplicationController.class.getName()).log(Level.SEVERE, null, ioEcx);

        }

    }

    public void initialize(URL location, ResourceBundle resources) {
        this.tableColumnXLSX.setCellValueFactory(new PropertyValueFactory<ResponseCardSet, String>("testName"));
        this.tableColumTestXML.setCellValueFactory(new PropertyValueFactory<Test, String>("testName"));
        this.tableColumnQuestionsXML.setCellValueFactory(new PropertyValueFactory<Test, Integer>("numberOfQuestions"));

        xmlTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Test>() {

            @Override
            public void changed(ObservableValue<? extends Test> observable, Test oldValue, Test newValue) {
                test = xmlTable.getSelectionModel().getSelectedItem();
            }
        });

        xlsxTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ResponseCardSet>() {

            @Override
            public void changed(ObservableValue<? extends ResponseCardSet> observable, ResponseCardSet oldValue, ResponseCardSet newValue) {
                responseCardSet = xlsxTable.getSelectionModel().getSelectedItem();
            }
        });

    }

    private Alert showMessageBox(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }
}
