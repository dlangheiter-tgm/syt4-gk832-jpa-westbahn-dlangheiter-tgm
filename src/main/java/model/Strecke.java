package model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Strecke {

	@Id
	private Long ID;

	@ManyToOne
	@UniqueElements
	private Bahnhof start;

	@ManyToOne
	@UniqueElements
	private Bahnhof ende;

}
