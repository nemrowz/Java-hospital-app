package hospital.tickets.buttons;

import hospital.doctors.Doctor;
import hospital.inNout.XmlWriter;
import hospital.doctors.table.DoctorTableModel;
import hospital.tickets.Ticket;
import hospital.tickets.TicketsCount;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
import static javax.swing.JOptionPane.showMessageDialog;

public class ChangeTicketButton implements ActionListener {
    private List<Doctor> doctors;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
    private List<Ticket> ticketsDoctor, tickets;
    private JTable doctorsTable, ticketsTable;
    private JTextField timeFiled, durationField, nameField;

    public ChangeTicketButton(JTable doctorsTable, JTable ticketsTable, List<Doctor> doctors, List<Ticket> tickets, List<Ticket> ticketsDoctor, JTextField timeFiled,
                              JTextField durationField, JTextField nameField) {
        this.doctorsTable = doctorsTable;
        this.ticketsTable = ticketsTable;
        this.doctors = doctors;
        this.tickets = tickets;
        this.ticketsDoctor = ticketsDoctor;
        this.durationField = durationField;
        this.timeFiled = timeFiled;
        this.nameField = nameField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            tickets.removeAll(ticketsDoctor);
            int selectedRow = ticketsTable.getSelectedRow();
            Doctor doc = Ticket.getSelectedDoctor(ticketsDoctor.get(selectedRow).getDoctorName(), doctors);
            double time = 0.8 * Doctor.getDoctorTime(doc);
            boolean isTicketFree;
            String pacientName = nameField.getText();
            if (!pacientName.equals("")) {
                isTicketFree = false;
            } else {
                isTicketFree = true;
                pacientName = "";
            }
            LocalTime ticketStartTime = LocalTime.parse(timeFiled.getText(), format);
            int duration = Integer.valueOf(durationField.getText());
            double newTime = new TicketsCount(ticketsDoctor).timeOfReceipt(ticketsDoctor.get(selectedRow).getDoctorName()) + duration / 60.0;
            if (newTime <= time) {
                if (!ticketStartTime.isAfter(doc.getEndOfReception())
                        && !ticketStartTime.isBefore(doc.getReceptionStart())
                        && !ticketStartTime.plus(duration, MINUTES).isAfter(doc.getEndOfReception())) {
                    try {
                        ticketsDoctor.get(selectedRow).setTicketStartTime(ticketStartTime);
                        ticketsDoctor.get(selectedRow).setTicketFree(isTicketFree);
                        ticketsDoctor.get(selectedRow).setPacientName(pacientName);
                        ticketsDoctor.get(selectedRow).setReceiptDuration(duration);
                        DoctorTableModel doctorTableModel = (DoctorTableModel) doctorsTable.getModel();
                        String name = ticketsDoctor.get(selectedRow).getDoctorName();
                        tickets.addAll(ticketsDoctor);
                        for (Doctor doctor :
                                doctorTableModel.getDoctors(doctorsTable.getSelectedRows())) {
                            doctor.setTimeOfReceipt(new TicketsCount(ticketsDoctor).timeOfReceipt(name));
                            doctor.setNumberOfTickets(new TicketsCount(ticketsDoctor).ticketsNumber(name));
                        }
                        XmlWriter.writeToFile("file.xml", doctors, tickets);
                        ticketsTable.updateUI();
                        doctorsTable.updateUI();
                    } catch (IndexOutOfBoundsException | NullPointerException | DateTimeParseException ignored) {
                    }
                } else {
                    ImageIcon icon = new ImageIcon(new ImageIcon("img\\info.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
                    UIManager.put("OptionPane.background", new ColorUIResource(208, 225, 252));
                    UIManager.put("Panel.background", new ColorUIResource(208, 225, 252));
                    showMessageDialog(null, "<html><span style='color:#2e5aac'>Врач в это время не работает!</span></html>",
                            "Info!", JOptionPane.INFORMATION_MESSAGE, icon);
                }
            } else {
                ImageIcon icon = new ImageIcon(new ImageIcon("img\\info.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
                UIManager.put("OptionPane.background", new ColorUIResource(208, 225, 252));
                UIManager.put("Panel.background", new ColorUIResource(208, 225, 252));
                showMessageDialog(null, "<html><span style='color:#2e5aac'>Талоны закончились!</span></html>", "Info!", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        } catch (IndexOutOfBoundsException e2) {
            ImageIcon icon = new ImageIcon(new ImageIcon("img\\warning.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            UIManager.put("OptionPane.background", new ColorUIResource(254, 238, 193));
            UIManager.put("Panel.background", new ColorUIResource(254, 238, 193));
            showMessageDialog(null, "<html><span style='color:#b95000'>Объект изменения не выбран!</span></html>", "Warning!", JOptionPane.WARNING_MESSAGE, icon);
        } catch (NumberFormatException | DateTimeParseException | NullPointerException e2) {
            ImageIcon icon = new ImageIcon(new ImageIcon("img\\error.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            UIManager.put("OptionPane.background", new ColorUIResource(250, 208, 204));
            UIManager.put("Panel.background", new ColorUIResource(250, 208, 204));
            showMessageDialog(null, "<html><span style='color:#DA1414'>Ошибка изменения объекта!</span></html>", "Warning!", JOptionPane.ERROR_MESSAGE, icon);
        }

    }
}