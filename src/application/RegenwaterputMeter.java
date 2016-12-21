package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

public class RegenwaterputMeter extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.hide();
		ScreenManager screenManager = new ScreenManager();
		screenManager.startDashboard(ScreenType.REALTIME);
		//RealtimeDashboard realtimeDashboard = new RealtimeDashboard();
		//realtimeDashboard.startDashboard(primaryStage);		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
