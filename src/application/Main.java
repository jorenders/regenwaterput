package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Map<String, String> configuratie = new HashMap<String, String>();
		Connection connection;
		try {
			//connection = DriverManager.getConnection("jdbc:sqlite:" + "C:\\PrivateWS\\regenwaterput\\trunk\\regenwaterput.db");
			connection = DriverManager.getConnection("jdbc:sqlite:" + "regenwaterput.db");
			Statement stmt = connection.createStatement();
			stmt.setQueryTimeout(30);
			ResultSet rs = stmt.executeQuery("select * from configuratie");
			
			while(rs.next())
            {
				
                String sKey = rs.getString("KEY");
                String sValue = rs.getString("VALUE");
                System.out.println("KEY: " + sKey);
                configuratie.put(sKey, sValue);
            }
			
			connection.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		

		
		primaryStage.setTitle("Regenwaterput");
		/*        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("");
            }
        });*/
        
        
        
		try {
	        
	        Pane root = new Pane();

	        StackPane holder = new StackPane();
	        Canvas canvas = new Canvas(600,  400);

	        holder.getChildren().add(canvas);
	        root.getChildren().add(holder);
	        holder.setStyle("-fx-background-color: black");
	        Scene scene = new Scene(root, 600, 400);   
	        
	        double volumeRegenwaterput = VolumeManager.berekenVolumeCilinder(Double.parseDouble(configuratie.get("DIAMETER")), Double.parseDouble(configuratie.get("DIEPTE")));
	        Gauge gauge = GaugeBuilder.create()
	        		.skinType(SkinType.SLIM)
	                .title("REGENPUT")  
	                .unit("LITER")
	                .maxValue(volumeRegenwaterput)
	                .animated(true)
	                .autoScale(true)
	                .build();  

	        gauge.setValue(12);
	        root.getChildren().add(gauge);
	        //root.getChildren().add(btn);
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
