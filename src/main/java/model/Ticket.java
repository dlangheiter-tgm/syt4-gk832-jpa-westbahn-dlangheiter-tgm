package model;

import javax.persistence.*;

@Entity
public abstract class Ticket {

	@Id
	@GeneratedValue
	protected Long ID;

	@ManyToOne
	protected Strecke strecke;

	@Transient
	protected Zahlung zahlung;

}
