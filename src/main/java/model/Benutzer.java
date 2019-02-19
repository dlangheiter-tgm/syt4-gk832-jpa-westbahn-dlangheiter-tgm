package model;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class Benutzer {

	@Id
	private Long ID;

	private String vorName;

	private String nachName;

	@Email
	private String eMail;

	private String passwort;

	private String smsNummer;

	private Long verbuchtePraemienMeilen;

	@OneToMany
	private Ticket[] tickets;

	@ManyToMany
	private Reservierung[] reservierungen;

}
