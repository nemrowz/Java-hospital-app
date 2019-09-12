package hospital.inNout;

import hospital.tickets.Ticket;

import javax.swing.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class TicketsXmlReader {
    public static List<Ticket> readFromFile(String fileName) {
        List<Ticket> tickets = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
        try {
            InputStream stream = new FileInputStream(fileName);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(stream);
            Ticket ticket = null;
            int elementType;
            String tagName;
            while (reader.hasNext()) {
                elementType = reader.next();
                switch (elementType) {
                    case XMLStreamReader.START_ELEMENT: {
                        tagName = reader.getLocalName();
                        switch (tagName) {
                            case "ticket":
                                ticket = new Ticket();
                                String doctorName = reader.getAttributeValue(null, "doctorName");
                                ;
                                String pacientName = reader.getAttributeValue(null, "pacientName");
                                String isTicketFree = reader.getAttributeValue(null, "isTicketFree");
                                String receiptDuration = reader.getAttributeValue(null, "receiptDuration");
                                String ticketStartTime = reader.getAttributeValue(null, "ticketStartTime");
                                ticket.setTicket(doctorName, LocalTime.parse(ticketStartTime, format), Integer.valueOf(receiptDuration), Boolean.valueOf(isTicketFree), pacientName);
                                break;
                        }
                        break;
                    }
                    case XMLStreamReader.END_ELEMENT: {
                        tagName = reader.getLocalName();
                        switch (tagName) {
                            case "ticket":
                                tickets.add(ticket);
                                break;
                        }
                        break;
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException | XMLStreamException | DateTimeParseException | NumberFormatException e) {
            showMessageDialog(null, "", "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return tickets;
    }
}