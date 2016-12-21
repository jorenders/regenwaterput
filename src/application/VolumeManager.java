package application;

public class VolumeManager {
	
	private Double diameter;
	private Double diepte;
	private Double straal;
	
	public Double getDiameter() {
		return diameter;
	}

	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}

	public Double getDiepte() {
		return diepte;
	}

	public void setDiepte(Double diepte) {
		this.diepte = diepte;
	}

	public Double getStraal() {
		return straal;
	}

	public void setStraal(Double straal) {
		this.straal = straal;
	}
	
	public VolumeManager() {	
		DataManager dataManager = new DataManager();
		dataManager.openConnectionDB();
		
		diameter = Double.parseDouble(dataManager.haalConfiguratieParameterOp(ConfiguratieType.DIAMETER));
		straal = diameter / 2;
		diepte = Double.parseDouble(dataManager.haalConfiguratieParameterOp(ConfiguratieType.DIEPTE));
			
		dataManager.closeConnectionDB();	
			
	}

	public double volumeCilinder() {
		double result = 0;
		result = ((Math.PI * (straal * straal) * diepte)) * 1000;
		return result;
	}
}