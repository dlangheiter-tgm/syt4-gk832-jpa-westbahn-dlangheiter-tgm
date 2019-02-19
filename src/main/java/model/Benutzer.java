package model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;

@Entity
public class Benutzer {

	@Id
	@GeneratedValue
	private Long ID;

	private String vorName;

	private String nachName;

	@Email
	private String eMail;

	private String passwort;

	private String smsNummer;

	private Long verbuchtePraemienMeilen;

	@OneToMany
	private Collection<Ticket> tickets;

	@OneToMany
	private Collection<Reservierung> reservierungen;

}
