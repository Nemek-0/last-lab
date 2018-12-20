/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lastlab;

import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 *
 * @author Serge
 */
public class FXMLDocumentController implements Initializable {
    
     //Окно
    @FXML private AnchorPane pane;
    
    
    //Переменые
    
    @FXML private TextField textFieldBeta;
    @FXML private TextField textFieldGamma;
       
    //Размеры плоскости
    @FXML private TextField textFieldA;
    @FXML private TextField textFieldB;
    @FXML private TextField textFieldC;
    @FXML private TextField textFieldD;
   


    //плоскости и оси
    @FXML private Pane lineChartXY;
    @FXML private LineChart lineChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis; 
      
    
    @FXML private TextField textFieldN;
    @FXML private TextField textFieldM;
    @FXML private TextField textFieldX0;
    @FXML private TextField textFieldP;
    @FXML
    private Label labelError;   
    
   
    double beta;
    double gamma;
        
    double a;
    double b;
    double c;
    double d;
        
    double n;
    double m;
    double x0;
    double p;
    
    
    
    private void test(double x, double y, Color color) {
        if (a > x || x > b) return;
        if (c > y || y > d) return;
        Circle circle = new Circle(posX(x), posY(y), 2);
        circle.setFill(color);
        lineChartXY.getChildren().add(circle);
    }
    
    @FXML
    private void handleButtonAction()  {
        lineChartXY.getChildren().clear();
        LineChart lineChart = this.lineChart;
        lineChartXY.getChildren().add(lineChart);
        if(this.isTrueParametrs()){
            updateParametrs();
            this.labelError.setText("");
            Calculation();  
        }
        else{
           updateTextFields();
           this.labelError.setText("Неверные параметры"); 
        } 
        
    }
    
    private void Calculation() {
        double step = (b - a) / this.lineChartXY.getWidth();
        for (double alpha = a; alpha < b; alpha += step) {
            double xn = x0;
            for (int i = 0; i < m; i++) {
                    xn = f(xn, alpha);         
                }
                for (int j = 0; j < n; j++) {
                    xn = f(xn, alpha);
                    if (j % p == 0 && j != 0) {
                        test(alpha, xn, Color.RED);
                    }
                    else {
                        test(alpha, xn, Color.BLACK);
                    }
                }
            }
            System.out.println("Готов");
        }
  
    
    private double f(double x, double alpha) {
        return (alpha * Math.sin(Math.tan(this.beta * x)) * Math.sin(this.gamma * x));
    }
    
    
    
    private double posX(double x) {
        double stepX = this.lineChartXY.getWidth() / (b - a);
        return (x - a) * stepX;
}
    
    private double posY(double y) {
        double stepY = this.lineChartXY.getHeight() / (d - c);
        return (y - c) * stepY;
    }
   
    
     //-------------------------ВСЯЧИНА------------------------------
    //Рисует графики на плоскости 
    
    
    
    private void updateParametrs() {
        
        
        this.beta = Double.parseDouble(this.textFieldBeta.getText());
        this.gamma = Double.parseDouble(this.textFieldGamma.getText());

        this.a = Double.parseDouble(this.textFieldA.getText());
        this.b = Double.parseDouble(this.textFieldB.getText());
        this.c = Double.parseDouble(this.textFieldC.getText());
        this.d = Double.parseDouble(this.textFieldD.getText());

        this.n = Double.parseDouble(this.textFieldN.getText());
        this.m = Double.parseDouble(this.textFieldM.getText());
        this.p = Double.parseDouble(this.textFieldP.getText());
        this.x0 = Double.parseDouble(this.textFieldX0.getText());
        
        this.xAxis.setLowerBound(this.a);
        this.xAxis.setUpperBound(this.b);
        this.yAxis.setLowerBound(this.c);
        this.yAxis.setUpperBound(this.d);
        
       
    }
    
    private void updateTextFields() {
        
        this.textFieldBeta.setText(String.valueOf(beta));
        this.textFieldGamma.setText(String.valueOf(gamma));
        
        
        this.textFieldA.setText(String.valueOf(a));
        this.textFieldB.setText(String.valueOf(b));
        this.textFieldC.setText(String.valueOf(c));
        this.textFieldD.setText(String.valueOf(d));
        
        this.textFieldN.setText(String.valueOf(n));
        this.textFieldM.setText(String.valueOf(m));
        this.textFieldP.setText(String.valueOf(p));
        this.textFieldX0.setText(String.valueOf(x0));
    }
    
    private boolean isTrueParametrs() {
        try {
            
            double beta = Double.parseDouble(this.textFieldBeta.getText());
            double gamma = Double.parseDouble(this.textFieldGamma.getText());

            double a = Double.parseDouble(this.textFieldA.getText());
            double b = Double.parseDouble(this.textFieldB.getText());
            double c = Double.parseDouble(this.textFieldC.getText());
            double d = Double.parseDouble(this.textFieldD.getText());

            double n = Double.parseDouble(this.textFieldN.getText());
            double m = Double.parseDouble(this.textFieldM.getText());
            double x0 = Double.parseDouble(this.textFieldX0.getText());
            double p = Double.parseDouble(this.textFieldP.getText());

            if( beta > 100 || gamma > 100 ) return false;
            if( beta < -100 || gamma < -100 ) return false;
            if(a > 100 || b > 100 || c > 100 || d > 100)  return false ;
            if(a < -100 || b < -100 || c < -100 || d < -100) return false;
            if(x0 < c || x0 > 25) return false;
            if(p < 1 || p > 25) return false;
            if(m < 1 || m > 500) return false;
            if(n < 1 || n > 500) return false;

            return true;

        } catch (NumberFormatException e) {
            System.out.println("Ввели некоректные значения");
            return false;
        }
    }
  
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       this.lineChart.getYAxis().setAutoRanging(false);
       this.lineChart.getXAxis().setAutoRanging(false);
       updateParametrs();
       
    }    
    
}
