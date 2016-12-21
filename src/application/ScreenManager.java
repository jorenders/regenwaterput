package application;

public class ScreenManager {	
	public void startDashboard(ScreenType screen) {
		if (ScreenType.REALTIME.equals(screen)) {
			RealtimeDashboard dashboard = new RealtimeDashboard();
			dashboard.startDashboard();
		} else if (ScreenType.HISTORISCH.equals(screen)) {
			HistorischeData dashboard = new HistorischeData();
			dashboard.startScreen();
		} else {
			/*HistorischeData dashboard = new HistorischeData();
			dashboard.startScreen();*/
		}
	}
}