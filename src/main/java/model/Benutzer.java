package model;

import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@NamedQuery(
		name="Benutzer.getReservierungenByEmail",
		query = "SELECT reservierungen FROM Benutzer WHERE eMail=:email"
)
@NamedQuery(
		name = "Benutzer.getAllWithMonatskarte",
		query = "SELECT b FROM Benutzer b LEFT JOIN b.tickets t WHERE t.typ=1" // ZeitkartenTyp.MONATSKARTE = 1
)
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

	public Benutzer() {
		this.reservierungen = new ArrayList<>();
		this.tickets = new ArrayList<>();
	}

	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Ticket> tickets;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "benutzer")
	private Collection<Reservierung> reservierungen;

	public Long getID() {
		return ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
	}

	public String getVorName() {
		return vorName;
	}

	public void setVorName(String vorName) {
		this.vorName = vorName;
	}

	public String getNachName() {
		return nachName;
	}

	public void setNachName(String nachName) {
		this.nachName = nachName;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getSmsNummer() {
		return smsNummer;
	}

	public void setSmsNummer(String smsNummer) {
		this.smsNummer = smsNummer;
	}

	public Long getVerbuchtePraemienMeilen() {
		return verbuchtePraemienMeilen;
	}

	public void setVerbuchtePraemienMeilen(Long verbuchtePraemienMeilen) {
		this.verbuchtePraemienMeilen = verbuchtePraemienMeilen;
	}

	public Collection<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Collection<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Collection<Reservierung> getReservierungen() {
		return reservierungen;
	}

	public void setReservierungen(Collection<Reservierung> reservierungen) {
		this.reservierungen = reservierungen;
	}

	@Override
	public String toString() {
		return "Benutzer{" +
				"ID=" + ID +
				", vorName='" + vorName + '\'' +
				", nachName='" + nachName + '\'' +
				", eMail='" + eMail + '\'' +
				", passwort='" + passwort + '\'' +
				", smsNummer='" + smsNummer + '\'' +
				", verbuchtePraemienMeilen=" + verbuchtePraemienMeilen +
				", tickets=" + tickets +
				", reservierungen=" + reservierungen +
				'}';
	}
}
