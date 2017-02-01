package application;

import java.util.Random;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class RealtimeDashboard {
	
	private static final Random RND = new Random();
	AnimationTimer timer;
	long lastTimerCall;
	
	public void startDashboard() {
		try {
			Stage stage = new Stage();
			stage.setTitle("Regenwaterput realtime");
	        Pane root = new Pane();
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
	        //Scene scene = new Scene(root, 600, 400);   
	        
	        VolumeManager volumeManager = new VolumeManager();
	        double volumeRegenwaterput = volumeManager.volumeCilinder();
	        
	        Gauge inhoudRegenput = GaugeBuilder.create()
	        		.skinType(SkinType.SLIM)
	                .title("REGENPUT")  
	                .unit("INHOUD VAN DE PUT (L)")
	                .maxValue(volumeRegenwaterput)
	                .animated(true)
	                .autoScale(true)
	                .maxSize(Double.MAX_VALUE, Double.MAX_VALUE)
	                .maxHeight(Double.MAX_VALUE)
	                .layoutY(canvas.getHeight()/3)
	                .layoutX((canvas.getWidth()/20)*1)
	                .gradientBarEnabled(true)
                    .gradientBarStops(new Stop(0.0, Color.RED),
                                      new Stop(0.25, Color.ORANGE),
                                      new Stop(0.5, Color.YELLOW),
                                      new Stop(0.75, Color.YELLOWGREEN),
                                      new Stop(1.0, Color.rgb(0,255,0)))
	                .areasVisible(true)
	                .decimals(0)
	                .build();  
	        
	        Gauge dagelijkseHoeveelheid = GaugeBuilder.create()
	        		.skinType(SkinType.SLIM)
	                .title("REGENPUT")  
	                .unit("DAGELIJKSE EVOLUTIE")
	                .animated(true)
	                .autoScale(true)
	                .maxValue(500)
	                .minValue(-500)
	                .layoutX((canvas.getWidth()/20)*16)
	                .layoutY(canvas.getHeight()/3)
	                .build();

	        inhoudRegenput.setValue(1);
	        dagelijkseHoeveelheid.setValue(-120);
	        
	        Button btn = new Button();
	        btn.setDefaultButton(true);
	        btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	        btn.setText("Historische gegevens");
	        btn.setLayoutX((canvas.getWidth()/20)*10);
	        btn.setLayoutY(canvas.getHeight()/2);
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	                System.out.println("CLick");
	                HistorischeData screenHistorischeData = new HistorischeData();
	                stage.hide();
	                screenHistorischeData.startScreen();
	                
	            }
	        });
	        
	        root.getChildren().addAll(inhoudRegenput, dagelijkseHoeveelheid,btn);
	        stage.setScene(new Scene(root, 0, 0));
	        stage.show();
	        
	        lastTimerCall = System.nanoTime();
	        
	        
	        timer = new AnimationTimer() {
	            @Override public void handle(long now) {
	                if (now > lastTimerCall + 3_000_000_000l) {
	                	inhoudRegenput.setValue(RND.nextDouble() * inhoudRegenput.getRange() + inhoudRegenput.getMinValue());
	                    lastTimerCall = now;
	                }
	            }
	        };
	        
	        timer.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}