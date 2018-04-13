package anylyzer.controllers;

import parser.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AnswerCardController implements Initializable {
    @FXML
    public TableView<StudentQuestionAnswers> tableViewDetailsStudentAnswers;
    @FXML
    public TableColumn<StudentQuestionAnswers,Integer> tableColumnQuestionNumber2;
    @FXML
    public TableColumn<StudentQuestionAnswers,Character> tableColumnAnswer2;

    @FXML
    private TextField textFieldStudentIndex;

    @FXML
    private ComboBox <Question> questions;
    @FXML
    private ComboBox <Answer> choiceBoxAnswers;
    @FXML
    private ComboBox <Answer>choiceBoxCorrectAnswer;
    @FXML private TableView<ResponseCard> tableViewStudentAnswers;
    @FXML private TableColumn<ResponseCard, String> tableColumnIndex ;


    @FXML private TableView<StudentQuestionAnswers> tableViewAnswersOnCurrentQuestion;
    @FXML private TableColumn<StudentQuestionAnswers, Integer> tableColumnQuestionNumber ;
    @FXML private TableColumn<StudentQuestionAnswers,Character > tableColumnAnswer ;

    ObservableList<StudentQuestionAnswers> obsList = FXCollections.observableArrayList();
    ObservableList<ResponseCard> obsList2 = FXCollections.observableArrayList();
    ObservableList<StudentQuestionAnswers> obsList3 = FXCollections.observableArrayList();

    private Test currentTest;
    private ResponseCardSet responseCardSet;


    public void initialize(URL location, ResourceBundle resources) {
        this.tableColumnQuestionNumber.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        this.tableColumnAnswer.setCellValueFactory(new PropertyValueFactory<>("questionAnswer"));
        this.tableColumnIndex.setCellValueFactory(new PropertyValueFactory<>("studentIndex"));
        this.tableColumnQuestionNumber2.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        this.tableColumnAnswer2.setCellValueFactory(new PropertyValueFactory<>("questionAnswer"));


    }

    void changeComboBox(){
        try {

            if (questions.getSelectionModel().getSelectedItem().getQuestionAnswers() != null) {
                choiceBoxAnswers.getItems().clear();
                choiceBoxCorrectAnswer.getItems().clear();
                choiceBoxAnswers.getItems().addAll(questions.getSelectionModel().getSelectedItem().getQuestionAnswers());
                choiceBoxCorrectAnswer.getItems().addAll(questions.getSelectionModel().getSelectedItem().getQuestionAnswers());
            }
        }catch (NullPointerException e){

        }

    }

    public void init(Test selectedItem) {
        currentTest = selectedItem;
        responseCardSet = new ResponseCardSet(currentTest.getTestName()+"1");
        //questions.setItems(FXCollections.<Test>observableArrayList((Callback<Test, Observable[]>) currentTest.getListOfTestQuestions()));
        questions.getItems().addAll( currentTest.getListOfTestQuestions());
        List<Question> data =  currentTest.getListOfTestQuestions();
        questions.valueProperty().addListener((obs, oldVal, newVal) -> changeComboBox());
        tableViewStudentAnswers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ResponseCard>() {

            @Override
            public void changed(ObservableValue<? extends ResponseCard> observable, ResponseCard oldValue, ResponseCard newValue) {
                tableViewDetailsStudentAnswers.getItems().clear();
                ResponseCard c = tableViewStudentAnswers.getSelectionModel().getSelectedItem();
                for(int i=0;i<c.getStudentResponses().size();i++){
                    obsList3.add(new StudentQuestionAnswers(i+1,c.getStudentAnswer(i+1)));
                    tableViewDetailsStudentAnswers.setItems(obsList3);
                }

            }
        });        questions.valueProperty().addListener((obs, oldVal, newVal) -> changeComboBox());

    }

    @FXML
    private void buttonAddAnswer_onAction(){

        try{
            if(choiceBoxCorrectAnswer.getSelectionModel().getSelectedItem()!=null){
                obsList.add(new StudentQuestionAnswers(questions.getSelectionModel().getSelectedItem().getNumber(),choiceBoxCorrectAnswer.getSelectionModel().getSelectedItem().getSymbol()));
                questions.getItems().remove(questions.getSelectionModel().getSelectedItem());
                tableViewAnswersOnCurrentQuestion.setItems(obsList);

            }
        }catch (NullPointerException n){

        }


    }

    @FXML
    void buttonDeleteAddedAnswer_onAction(){
        if(tableViewAnswersOnCurrentQuestion.getSelectionModel().getSelectedItem()!=null){
            for(Question q : currentTest.getListOfTestQuestions()){
                if(q.getNumber()==tableViewAnswersOnCurrentQuestion.getSelectionModel().getSelectedItem().getQuestionNumber()){
                    questions.getItems().add(q);
                }
            }

            tableViewAnswersOnCurrentQuestion.getItems().remove(tableViewAnswersOnCurrentQuestion.getSelectionModel().getSelectedItem());
        }
    }
    @FXML
    void buttonAddAnswerCard_onAction(){
        if(tableViewAnswersOnCurrentQuestion.getItems().size()==currentTest.getNumberOfQuestions()){


            ResponseCard r = new ResponseCard(currentTest.getNumberOfQuestions(),textFieldStudentIndex.getText());
            System.out.println(textFieldStudentIndex.getText());
            for(StudentQuestionAnswers s : tableViewAnswersOnCurrentQuestion.getItems())
                r.addStudentResponse(s.getQuestionNumber(),s.getQuestionAnswer());

            obsList2.add(r);
            tableViewStudentAnswers.setItems(obsList2);
            tableViewAnswersOnCurrentQuestion.getItems().clear();
            choiceBoxAnswers.getItems().clear();
            choiceBoxCorrectAnswer.getItems().clear();
            questions.getItems().addAll( currentTest.getListOfTestQuestions());

        }

    }
    @FXML
    void buttonDeleteCheckedAnswerCard_onAction(){
        if(tableViewAnswersOnCurrentQuestion.getSelectionModel().getSelectedItem()!=null){
            tableViewStudentAnswers.getItems().remove((tableViewStudentAnswers.getSelectionModel().getSelectedItem()));
        }
    }

    @FXML
    void buttonAddAnswerCardSet_onAction(){
        responseCardSet.setListOfStudentsResponses(tableViewStudentAnswers.getItems());
        tableViewAnswersOnCurrentQuestion.getItems().clear();
        tableViewDetailsStudentAnswers.getItems().clear();
        tableViewStudentAnswers.getItems().clear();
        XLSXParser x = new XLSXParser();
        x.saveToXlsxFile(responseCardSet,responseCardSet.getTestName());

    }



}
