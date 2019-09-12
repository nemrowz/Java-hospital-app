package hospital.tickets;

import java.util.List;

public class TicketsCount {
    private List<Ticket> tickets;

    public TicketsCount(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public int ticketsNumber(String name) {
        int ticketsNumber = 0;
        for (Ticket ticket : tickets) {
            if (name.equalsIgnoreCase(ticket.getDoctorName()) && ticket.isTicketFree())
                ticketsNumber++;
        }
        return ticketsNumber;
    }

    public double timeOfReceipt(String name) {
        double timeOfReceipt = 0;
        for (Ticket ticket : tickets) {
            if (name.equalsIgnoreCase(ticket.getDoctorName()) && !ticket.isTicketFree())
                timeOfReceipt += ticket.getReceiptDuration() / 60.0;
        }
        return Math.round(timeOfReceipt * 100.0) / 100.0;
    }
}