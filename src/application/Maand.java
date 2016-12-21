package application;

public enum Maand {
	JANUARI("1"), 
	FEBRUARI("2"),
	MAART("3"),
	APRIL("4"),
	MEI("5"),
	JUNI("6"),
	JULI("7"),
	AUGUSTUS("8"),
	SEPTEMBER("9"),
	OKTOBER("10"),
	NOVEMBER("11"),
	DECEMBER("12");
	
	private String maandnummer;

	private Maand(String maandnummer) {
		this.maandnummer = maandnummer;
	}

	public String getMaandnummer() {
		return maandnummer;
	}
	
	public static Maand getById(Long id) {
	    for(Maand e : values()) {
	        if(e.maandnummer.equals(id.toString())) return e;
	    }
	    return null;
	}
	
	public static String getNaamById(Integer id) {
	    for(Maand e : values()) {
	        if(e.maandnummer.equals(id.toString())) return e.toString();
	    }
	    return null;
	}
}
