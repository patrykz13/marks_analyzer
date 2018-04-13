package anylyzer.controllers;

import parser.Answer;
import parser.Question;
import parser.Test;
import parser.XmlParser;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
/**
 * <p>TestCreateController class.</p>
 *
 * @author Patryk Zdral
 * @version $Id: $Id
 */
public class TestCreateController {
    private int increment;
    private Test test;
    @FXML
    private Label labelQuestion,labelA,labelB,labelC,labelD,labelCorrectAnswer;

    @FXML
    private Button buttonAddQuestion,buttonEnd;

    @FXML
    private ChoiceBox choiceBoxCorrectAnswer;
    @FXML
    private TextField textFieldQuestion,textFieldTestName,textFieldA,textFieldB,textFieldC,textFieldD;

    @FXML
    void buttoStartCreating_onAction(){
        increment =0;


        String name = textFieldTestName.getText();
        if(!name.isEmpty()){
            test = new Test(name);
            labelQuestion.setVisible(true);
            textFieldQuestion.setVisible(true);
            labelA.setVisible(true);
            labelB.setVisible(true);
            labelC.setVisible(true);
            labelD.setVisible(true);
            textFieldA.setVisible(true);
            textFieldB.setVisible(true);
            textFieldC.setVisible(true);
            textFieldD.setVisible(true);
            buttonAddQuestion.setVisible(true);
            buttonEnd.setVisible(true);
            choiceBoxCorrectAnswer.setVisible(true);
            labelCorrectAnswer.setVisible(true);



        }
        else{
            showMessageBox(Alert.AlertType.ERROR, "Błąd",
                    "Input test title").showAndWait();
        }

    }
    @FXML
    void buttonAddQuestion_onAction(){
        String textFieldQ=textFieldQuestion.getText();
        String textField1=textFieldA.getText();
        String textField2=textFieldA.getText();
        String textField3=textFieldA.getText();
        String textField4=textFieldA.getText();
        String textChoice= (String) choiceBoxCorrectAnswer.getValue();
        if(!textField1.isEmpty()&&!textField2.isEmpty()&&!textField3.isEmpty()&&!textField4.isEmpty()&&!textFieldQ.isEmpty()&&!textChoice.isEmpty()){
            Character text = textChoice.charAt(0);
            Answer q1 = new Answer();
            Answer q2= new Answer();
            Answer q3= new Answer();
            Answer q4= new Answer();

            switch(text){
                case 'A': {
                    q1 = new Answer('A',textField1,true);
                    q2 = new Answer('B',textField2,false);
                    q3 = new Answer('C',textField3,false);
                    q4 = new Answer('D',textField4,false);
                    break;

                }
                case 'B': {
                    q1 = new Answer('A',textField1,false);
                    q2 = new Answer('B',textField2,true);
                    q3 = new Answer('C',textField3,false);
                    q4 = new Answer('D',textField4,false);
                    break;

                }
                case 'C': {
                    q1 = new Answer('A',textField1,false);
                    q2 = new Answer('B',textField2,false);
                    q3 = new Answer('C',textField3,true);
                    q4 = new Answer('D',textField4,false);
                    break;
                }
                case 'D': {
                    q1 = new Answer('A',textField1,false);
                    q2 = new Answer('B',textField2,false);
                    q3 = new Answer('C',textField3,false);
                    q4 = new Answer('D',textField4,true);
                    break;

                }

            }
            List<Answer> listOfAnswers = new ArrayList<Answer>(Arrays.asList(q1,q2,q3,q4));
            Question question = new Question(textFieldQ,listOfAnswers,++increment);
            test.addToList(question);
            textFieldA.clear();
            textFieldB.clear();
            textFieldC.clear();
            textFieldD.clear();
            textFieldQuestion.clear();
            choiceBoxCorrectAnswer.getSelectionModel().clearSelection();


        }
        else{
            showMessageBox(Alert.AlertType.ERROR, "Błąd",
                    "Input all question data").showAndWait();
        }
    }
    @FXML
    void buttonEnd_onAction(){
        XmlParser xml = new XmlParser();
        xml.saveToXMLFile(test,textFieldTestName.getText());
        labelQuestion.setVisible(false);
        textFieldQuestion.setVisible(false);
        labelA.setVisible(false);
        labelB.setVisible(false);
        labelC.setVisible(false);
        labelD.setVisible(false);
        textFieldA.setVisible(false);
        textFieldB.setVisible(false);
        textFieldC.setVisible(false);
        textFieldD.setVisible(false);
        buttonAddQuestion.setVisible(false);
        buttonEnd.setVisible(false);
        choiceBoxCorrectAnswer.setVisible(false);
        labelCorrectAnswer.setVisible(false);
    }

    private Alert showMessageBox(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }

}
