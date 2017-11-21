package ltterm.lineChart;

/**
 *
 * @author Burak Demirci
 */

import java.util.ArrayList;
import java.util.StringTokenizer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration;

public class lineChart {

    private LineChart<Number, Number> chart;
    private static int chartLine=0;
    private LTTermLineChart getVal = new LTTermLineChart();
    private XYChart.Series<Number, Number> dataSeries;

    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private Timeline animation;
    private double sequence = 0;
    private double y = 10.0;
    private double MAX_DATA_POINTS = 25.0, MAX = 50.0, MIN = 0.0;
    private static ArrayList<String> seriesHeadName =new ArrayList<>();
    private static ArrayList<XYChart.Series<Number, Number>>seriesHead=new ArrayList<>();
            
    public lineChart( ArrayList<String> args) throws Exception{
        
        int i=0;
        while (i < args.size()) {
            seriesHeadName.add(args.get(i));
            chartLine++;
            seriesHead.add(new XYChart.Series<>());
            i++;
        }
       
        /*---------------------------------------*/
        animation = new Timeline();
        animation.getKeyFrames()
                .add(new KeyFrame(Duration.millis(1000),
                                (ActionEvent actionEvent) -> plotTime()));
        animation.setCycleCount(Animation.INDEFINITE);
        
        
        /*---------------------------------------*/
        Stage primaryStage= new Stage();
        primaryStage.setScene(new Scene(createContent(),850,500));
        primaryStage.setTitle("LTTerm");
        primaryStage.show();
        play();
    }
    
    public Parent createContent() {

        xAxis = new NumberAxis(0, MAX_DATA_POINTS + 1, 2);
        yAxis = new NumberAxis(MIN - 1, MAX + 1, 1);
        chart = new LineChart<>(xAxis, yAxis);

        // setup chart
        chart.setAnimated(true);
        chart.setLegendVisible(true);
        chart.setTitle("LTTerm");
        xAxis.setForceZeroInRange(false);

        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, "", null));
         
        // add starting data
        for(int i=0; i<chartLine; i++){
            seriesHead.get(i).setName(seriesHeadName.get(i));
            chart.getData().add(seriesHead.get(i));
        }
        
        return chart;
    }

    private void plotTime() {
        
        sequence++;
        for(int i=0; i<chartLine; i++){
            if(!getNextValue().isEmpty())
                seriesHead.get(i).getData().add(new XYChart.Data<Number,Number>(sequence,
                        getNextValue().get(i)));
        }
        
        /* Resize chart */
        if(!getNextValue().isEmpty()){
            if (sequence > MAX_DATA_POINTS - 1) {
                xAxis.setLowerBound(xAxis.getLowerBound() + 1);
                xAxis.setUpperBound(xAxis.getUpperBound() + 1);
            }
        
            if (sequence > MAX_DATA_POINTS) {
                for (int i = 0; i < chartLine; i++) {
                    seriesHead.get(i).getData().remove(0);
                }
            }
        }    
    }

    private ArrayList<Double> getNextValue() {
        
        ArrayList<String> dataStr=  getVal.setValtoChart();
        ArrayList<Double> dataDbl=new ArrayList<>();
        if(!dataStr.isEmpty()){
            for(int i=0; i<chartLine; i++) {
                dataDbl.add(Double.parseDouble(dataStr.get(i)));
                if(dataDbl.get(i).compareTo(MAX)>0){
                    MAX=dataDbl.get(i);
                    yAxis.setUpperBound(MAX+2);
                }
            }
        }
        return dataDbl;
    }

    public void play() {
        animation.play();
    }
}
