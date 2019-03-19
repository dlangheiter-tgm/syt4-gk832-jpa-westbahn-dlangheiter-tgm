package model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
public class Strecke {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ID;

	@ManyToOne
	@UniqueElements
	private Bahnhof start;

	@ManyToOne
	@UniqueElements
	private Bahnhof ende;

	public Long getID() {
		return ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
	}

	public Bahnhof getStart() {
		return start;
	}

	public void setStart(Bahnhof start) {
		this.start = start;
	}

	public Bahnhof getEnde() {
		return ende;
	}

	public void setEnde(Bahnhof ende) {
		this.ende = ende;
	}
}
