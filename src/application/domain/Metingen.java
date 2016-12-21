package application.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="METINGEN")
public class Metingen {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="DATUM")
	private LocalDateTime datum;
	
	@Column(name="WAARDE")
	private Long waarde;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDatum() {
		return datum;
	}

	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}

	public Long getWaarde() {
		return waarde;
	}

	public void setWaarde(Long waarde) {
		this.waarde = waarde;
	}	
}
