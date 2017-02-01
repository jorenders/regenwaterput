package application;

public enum ConfiguratieType {
	DIAMETER("DIAMETER"), DIEPTE("DIEPTE");
	
	private String naam;

	private ConfiguratieType(String naam) {
		this.naam = naam;
	}

	public String getNaam() {
		return naam;
	}
}