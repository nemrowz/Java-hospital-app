package hospital.inNout;

import hospital.doctors.Doctor;
import hospital.tickets.Ticket;

import javax.swing.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeParseException;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class XmlWriter {
    public static void writeToFile(String fileName, List<Doctor> doctors, List<Ticket> tickets) {
        try {
            OutputStream out = new FileOutputStream(fileName);
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlStreamWriter = factory.createXMLStreamWriter(out);
            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
            xmlStreamWriter.writeStartElement("doctors");
            for (Doctor doctor : doctors) {
                xmlStreamWriter.writeStartElement("doctor");
                xmlStreamWriter.writeAttribute("speciality", doctor.getSpeciality());
                xmlStreamWriter.writeAttribute("surname", doctor.getSurname());
                xmlStreamWriter.writeAttribute("name", doctor.getName());
                xmlStreamWriter.writeAttribute("patronymic", doctor.getPatronymic());
                xmlStreamWriter.writeAttribute("isNarrowSpecialist", String.valueOf(doctor.isNarrowSpecialist()));
                xmlStreamWriter.writeAttribute("areaNumber", String.valueOf(doctor.getAreaNumber()));
                xmlStreamWriter.writeAttribute("cabinetNumber", String.valueOf(doctor.getCabinetNumber()));
                xmlStreamWriter.writeAttribute("receptionStart", String.valueOf(doctor.getReceptionStart()));
                xmlStreamWriter.writeAttribute("endOfReception", String.valueOf(doctor.getEndOfReception()));
                xmlStreamWriter.writeStartElement("tickets");
                String name = doctor.getSurname() + " " + doctor.getName().charAt(0) + "." + doctor.getPatronymic().charAt(0) + ".";
                for (Ticket ticket : tickets) {
                    if (ticket.getDoctorName().equals(name)) {
                        xmlStreamWriter.writeStartElement("ticket");
                        xmlStreamWriter.writeAttribute("doctorName", ticket.getDoctorName());
                        xmlStreamWriter.writeAttribute("ticketStartTime", String.valueOf(ticket.getTicketStartTime()));
                        xmlStreamWriter.writeAttribute("receiptDuration", String.valueOf(ticket.getReceiptDuration()));
                        xmlStreamWriter.writeAttribute("isTicketFree", String.valueOf(ticket.isTicketFree()));
                        xmlStreamWriter.writeAttribute("pacientName", ticket.getPacientName());
                        xmlStreamWriter.writeEndElement();
                    }
                }
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeEndElement();
            }
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.close();
            out.close();
        } catch (XMLStreamException | DateTimeParseException | NumberFormatException | IOException e) {
            showMessageDialog(null, "", "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
}