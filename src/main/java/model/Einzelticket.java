package model;

import javax.persistence.Entity;

@Entity
public class Einzelticket extends Ticket {

	private TicketOption ticketOption;

	public Einzelticket() {}

	public Einzelticket(TicketOption ticketOption) {
		super();
		this.ticketOption = ticketOption;
	}
}
