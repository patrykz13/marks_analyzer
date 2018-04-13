package anylyzer.controllers;
import java.net.URL;
import java.util.*;

import parser.ResponseCardSet;
import parser.Statistic;
import parser.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class HistogramController implements Initializable {


    @FXML
    CategoryAxis xAxisPointChart, xAxisMarksChart, xAxisAverageAnswerChart;
    @FXML
    NumberAxis yAxisPointChart, yAxisMarksChart, yAxisAverageAnswerChart;

    @FXML
    private BarChart<String, Number> pointsChart, marksChart;
    @FXML
    private BarChart<String, Double> averageAnswerChart;




    @FXML
    private ObservableList<Integer> numbers = FXCollections.observableArrayList();



    @FXML
    private void initialize() {


       // pointsChart.getData().add(series);

    }
    @FXML
    void initData(ResponseCardSet responseCardSet, Test test){



        Statistic st = new Statistic();

        st.createPointStatistic(responseCardSet,test);
        st.initilizeMarks(responseCardSet,test);
        XYChart.Series series = new XYChart.Series();
        for(Integer i =0;i<st.getNumberOfQuestions()+1;i++)
            series.getData().add(new XYChart.Data(i.toString(),st.numberOfPersonForPoints[i] ));

        xAxisPointChart.setLabel("points number");
        yAxisPointChart.setLabel("people number");
        pointsChart.getData().addAll(series);
        pointsChart.setTitle("Histogram o average points");


        st.createMarkStatistic(responseCardSet,test);
        series = new XYChart.Series();
        st.initilizeMarks(responseCardSet,test);
        int o=0;
        for(Double i =2.0;i<=5.5;i=i+0.5){
            if(i==2.5) i=3.0;
            series.getData().add(new XYChart.Data(i.toString(),st.numberOfPersonForMark[o] ));
            o++;
        }
        marksChart.getData().addAll(series);
        marksChart.setTitle("Histogram of average Mark");
        xAxisMarksChart.setLabel("marks");
        yAxisMarksChart.setLabel("people number");


        st.createAvearge(responseCardSet,test);
        series = new XYChart.Series();
        st.initilizeMarks(responseCardSet,test);

        for(int i =0;i<st.getNumberOfQuestions();i++){
            series.getData().add(new XYChart.Data(String.valueOf(i+1),(st.getGoodAnswersOnQuestions(i))));
        }
        averageAnswerChart.getData().addAll(series);
        averageAnswerChart.setTitle("histogram of percent correct answers to questions\n");
        xAxisAverageAnswerChart.setLabel("Question numbers");
        yAxisAverageAnswerChart.setLabel("percent of good answers");

    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}
