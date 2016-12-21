package application;

import java.util.Random;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
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
	        Canvas canvas = new Canvas(600,  400);

	        holder.getChildren().add(canvas);
	        root.getChildren().add(holder);
	        holder.setStyle("-fx-background-color: black");
	        Scene scene = new Scene(root, 600, 400);   
	        
	        VolumeManager volumeManager = new VolumeManager();
	        double volumeRegenwaterput = volumeManager.volumeCilinder();
	        
	        Gauge gauge = GaugeBuilder.create()
	        		.skinType(SkinType.SLIM)
	                .title("REGENPUT")  
	                .unit("INHOUD VAN DE PUT (L)")
	                .maxValue(volumeRegenwaterput)
	                .animated(true)
	                .autoScale(true)
	                .maxSize(Double.MAX_VALUE, Double.MAX_VALUE)
	                .maxHeight(Double.MAX_VALUE)
	                .layoutY(scene.getHeight()/6)
	                .layoutX(20)
	                //.areas(new Section(0.0, ondergrens, Color.rgb(255,0,0)))
	                //.areas(new Section(ondergrens, maximumgrens, Color.rgb(0,255,0)))
	                .gradientBarEnabled(true)
                    .gradientBarStops(new Stop(0.0, Color.RED),
                                      new Stop(0.25, Color.ORANGE),
                                      new Stop(0.5, Color.YELLOW),
                                      new Stop(0.75, Color.YELLOWGREEN),
                                      new Stop(1.0, Color.rgb(0,255,0)))
	                .areasVisible(true)
	                .decimals(0)
	                .build();  
	        
	        Gauge gauge2 = GaugeBuilder.create()
	        		.skinType(SkinType.SLIM)
	                .title("REGENPUT")  
	                .unit("DAGELIJKSE EVOLUTIE")
	                .animated(true)
	                .autoScale(true)
	                .layoutX((scene.getWidth()/2)+20)
	                .layoutY(scene.getHeight()/6)
	                .maxValue(500)
	                .minValue(-500)
	                .build();

	        gauge.setValue(1);
	        gauge2.setValue(-120);
	        
	        Button btn = new Button();
	        btn.setText("Start configuration");
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	                System.out.println("CLick");
	                HistorischeData screenHistorischeData = new HistorischeData();
	                stage.hide();
	                screenHistorischeData.startScreen();
	                
	            }
	        });
	        
	        root.getChildren().addAll(gauge, gauge2,btn);
	        //root.getChildren().add(gauge2);
	        //root.getChildren().add(btn);
	        stage.setScene(scene);
	        stage.show();
	        
	        lastTimerCall = System.nanoTime();
	        
	        
	        timer = new AnimationTimer() {
	            @Override public void handle(long now) {
	                if (now > lastTimerCall + 3_000_000_000l) {
	                    gauge.setValue(RND.nextDouble() * gauge.getRange() + gauge.getMinValue());
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