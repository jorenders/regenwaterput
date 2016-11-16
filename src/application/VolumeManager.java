package application;

public class VolumeManager {
	
	static double pi = 3.14159265359;
	
	public static double berekenVolumeCilinder(double straal, double diepte) {
		double result = 0;
		result = pi * (straal * straal) * diepte;
		return result;
	}
}
