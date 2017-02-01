package application;
	
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class RegenwaterputMeter extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		
		primaryStage.hide();
		ScreenManager screenManager = new ScreenManager();
		screenManager.startDashboard(ScreenType.REALTIME);
		//RealtimeDashboard realtimeDashboard = new RealtimeDashboard();
		//realtimeDashboard.startDashboard(primaryStage);		
	}
	
	public static void main(String[] args) {
		launch(args);
		DataManager.shutdown();
	}
}