package application;

import java.time.Month;
import java.util.Map;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HistorischeData {
	final static String maand_Januari = "Januari";
	final static String maand_Februari = "Februari";
	final static String maand_Maart = "Maart";
	final static String maand_April = "April";
	
	private DataManager dataManager;

	public void startScreen() {
		try {
			dataManager = new DataManager();
			Stage stage = new Stage();
			stage.setTitle("Regenwaterput historische data");
	        Pane root = new Pane();
	        GridPane pane = new GridPane();
	        pane.getColumnConstraints().add(new ColumnConstraints(600));
	        pane.getRowConstraints().add(new RowConstraints(375));
	        pane.getRowConstraints().add(new RowConstraints(25));
	        StackPane holder = new StackPane();
	        
	        Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();

			stage.setX(bounds.getMinX());
			stage.setY(bounds.getMinY());
			stage.setWidth(bounds.getWidth());
			stage.setHeight(bounds.getHeight());
			
			
	        Canvas canvas = new Canvas(bounds.getWidth(), bounds.getHeight());

	        
	        holder.getChildren().add(canvas);
	        root.getChildren().add(holder);
	        holder.setStyle("-fx-background-color: black");
	           
	        
	        stage.setScene(new Scene(root, 0, 0));
	        
	        Button btn = new Button();
	        //btn.setStyle("-fx-alignment: center; -fx-text-fill: white; -fx-background-color: #ecebe9, rgb(255,0,0,0)");
	        btn.setText("Start configuration");
	        btn.setDefaultButton(true);
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	 
	        @Override
	            public void handle(ActionEvent event) {
	                System.out.println("CLick");
	                stage.hide();
	                ScreenManager screenManager = new ScreenManager();
	                screenManager.startDashboard(ScreenType.REALTIME);
	            }
	        });
	        BarChart<String,Number> jaarOverzicht = createBcJaaroverzicht();
	        jaarOverzicht.setMaxWidth(javafx.scene.layout.Region.USE_PREF_SIZE);
	        jaarOverzicht.setMinWidth((bounds.getWidth()/20)*18);
	        jaarOverzicht.setMaxHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
	        jaarOverzicht.setMinHeight((bounds.getHeight()/20)*16);
	        System.out.println("X: " + jaarOverzicht.getScaleX());
	        System.out.println("Y: " + jaarOverzicht.getPrefHeight());
	        jaarOverzicht.setLayoutX((bounds.getWidth()/20)*1);
	        jaarOverzicht.setLayoutY(bounds.getHeight()/3);
	        jaarOverzicht.setLayoutX(1);
	        jaarOverzicht.setLayoutY(bounds.getHeight()/20);
	        //btn.setLayoutX((bounds.getWidth()/2)-(btn.getWidth()/2));
	        VBox vbox2 = new VBox(10);
	        vbox2.setAlignment(Pos.CENTER);
	        vbox2.setStyle("-fx-border-style: solid;"
	        		                + "-fx-border-width: 1;"
	        		                + "-fx-border-color: yellow");

	        btn.setAlignment(Pos.BOTTOM_CENTER);
	        vbox2.getChildren().add(btn);
	        //pane.add(jaarOverzicht, 0, 0);
	        //pane.add(btn, 0, 1);
	        root.getChildren().addAll(jaarOverzicht, vbox2);
	        
	        
	        
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public BarChart<String,Number> createBcJaaroverzicht() {
		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Waterverbruik overzicht");
        dataManager.openConnectionDB();
        Set<Integer> jaartallen = dataManager.haalJaartallenOp();
        
        xAxis.setLabel("Maanden");       
        yAxis.setLabel("Kubieke meter (m3)");

        jaartallen.forEach( (z) -> {
        	
        	@SuppressWarnings("rawtypes")
			XYChart.Series serie = new XYChart.Series<String, Long>();
            Map<Month, Long> maandGegevens = dataManager.haalJaaroverzichtOpVanBepaaldJaar(z);
            serie.setName(z.toString());
            maandGegevens.forEach( (k,v) -> {
            	System.out.println("Key: " + k + ": Value: " + v);
            	serie.getData().add(new XYChart.Data<String, Long>(Maand.getNaamById(k.getValue()), v));
            });
            bc.getData().addAll(serie);
        });
        
        for (Series<String,Number> serie: bc.getData()){
            for (XYChart.Data<String, Number> item: serie.getData()){
                item.getNode().setOnMousePressed((MouseEvent event) -> {
                    System.out.println("you clicked "+item.toString()+serie.toString());
                });
            }
        }
		return bc;
	}
	
	@SuppressWarnings("unchecked")
	public BarChart<String,Number> createBcMaandoverzicht(int jaar) {
		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Waterverbruik overzicht");
        dataManager.openConnectionDB();
        
        
        xAxis.setLabel("Maanden");       
        yAxis.setLabel("Kubieke meter (m3)");
 
       /* XYChart.Series series1 = new XYChart.Series();
        series1.setName("2014");       
        series1.getData().add(new XYChart.Data(maand_Januari, random.nextDouble() * 50 + 0));
        series1.getData().add(new XYChart.Data(maand_Februari, random.nextDouble() * 50 + 0));
        series1.getData().add(new XYChart.Data(maand_Maart, random.nextDouble() * 50 + 0));
        series1.getData().add(new XYChart.Data(maand_April, random.nextDouble() * 50 + 0));
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2015");
        series2.getData().add(new XYChart.Data(maand_Januari, random.nextDouble() * 50 + 0));
        series2.getData().add(new XYChart.Data(maand_Februari, random.nextDouble() * 50 + 0));
        series2.getData().add(new XYChart.Data(maand_Maart, random.nextDouble() * 50 + 0));
        series2.getData().add(new XYChart.Data(maand_April, random.nextDouble() * 50 + 0));
        */
        XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
        Map<Month, Long> jaartallen = dataManager.haalJaaroverzichtOpVanBepaaldJaar(2016);
        jaartallen.forEach( (k,v) -> {
        	System.out.println("Key: " + k + ": Value: " + v);
        	series3.getData().add(new XYChart.Data<String, Number>(Maand.getNaamById(k.getValue()), v));
        	
        });
        
        bc.getData().addAll(series3);
		return bc;
	}
}