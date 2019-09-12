package hospital.tickets.buttons;

import hospital.doctors.Doctor;
import hospital.doctors.table.DoctorTableModel;
import hospital.inNout.XmlWriter;
import hospital.tickets.Ticket;
import hospital.tickets.TicketsCount;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
import static javax.swing.JOptionPane.showMessageDialog;

public class TicketAddButton implements ActionListener {
    private Doctor doctor;
    private List<Doctor> doctors;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
    private List<Ticket> ticketsDoctor, tickets;
    private JTable doctorsTable, ticketsTable;
    private JTextField timeFiled, durationField, nameField;

    public TicketAddButton(JTable doctorsTable, Doctor doctor, JTable ticketsTable, List<Doctor> doctors, List<Ticket> tickets, List<Ticket> ticketsDoctor, JTextField timeFiled,
                           JTextField durationField, JTextField nameField) {
        this.doctorsTable = doctorsTable;
        this.doctor = doctor;
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
            int k = 0;

            for (Ticket ticket : ticketsDoctor) {
                if (!LocalTime.parse(timeFiled.getText(), format).equals(doctor.getReceptionStart()) || !LocalTime.parse(timeFiled.getText(), format).equals(doctor.getEndOfReception()))
                    if (ticket.getTicketStartTime().equals(LocalTime.parse(timeFiled.getText(), format))
                            || (LocalTime.parse(timeFiled.getText(), format).isBefore(ticket.getTicketStartTime().plus(ticket.getReceiptDuration(), MINUTES))
                            && LocalTime.parse(timeFiled.getText(), format).isAfter(doctor.getReceptionStart())))
                        k++;
            }
            String doctorName = doctor.getSurname() + " " + doctor.getName().charAt(0) + "." + doctor.getPatronymic().charAt(0) + ".";
            if (k == 0) {
                try {
                    Ticket newTicket = new Ticket();
                    boolean isFreeTicket;
                    String pacientName;
                    if (nameField.getText().isEmpty()) pacientName = "";
                    else pacientName = nameField.getText();
                    isFreeTicket = pacientName.equals("");
                    LocalTime ticketTime = LocalTime.parse(timeFiled.getText(), format);
                    int duration = Integer.valueOf(durationField.getText());

                    double time = 0.8 * Doctor.getDoctorTime(doctor);
                    newTicket.setTicket(doctorName, ticketTime, duration, isFreeTicket, pacientName);
                    double newTime = new TicketsCount(ticketsDoctor).timeOfReceipt(doctorName) + duration / 60.0;
                    if (newTime <= time) {
                        if (!newTicket.getTicketStartTime().isAfter(doctor.getEndOfReception())
                                && !newTicket.getTicketStartTime().isBefore(doctor.getReceptionStart())
                                && !newTicket.getTicketStartTime().plus(duration, MINUTES).isAfter(doctor.getEndOfReception())) {
                            ticketsDoctor.add(newTicket);
                            tickets.addAll(ticketsDoctor);
                            DoctorTableModel doctorTableModel = (DoctorTableModel) doctorsTable.getModel();
                            for (Doctor doctorD :
                                    doctorTableModel.getDoctors(doctorsTable.getSelectedRows())) {
                                doctorD.setTimeOfReceipt(new TicketsCount(ticketsDoctor).timeOfReceipt(doctorName));
                                doctorD.setNumberOfTickets(new TicketsCount(ticketsDoctor).ticketsNumber(doctorName));
                            }
                            XmlWriter.writeToFile("file.xml", doctors, tickets);
                            ticketsTable.updateUI();
                            doctorsTable.updateUI();
                        } else {
                            ImageIcon icon = new ImageIcon(new ImageIcon("img\\error.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
                            UIManager.put("OptionPane.background", new ColorUIResource(250, 208, 204));
                            UIManager.put("Panel.background", new ColorUIResource(250, 208, 204));
                            showMessageDialog(null, "<html><span style='color:#DA1414'>Ошибка добавления талона," +
                                    "<br> врач в это время не работает</span></html>", "Error!", JOptionPane.ERROR_MESSAGE, icon);
                        }
                    } else {
                        ImageIcon icon = new ImageIcon(new ImageIcon("img\\info.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
                        UIManager.put("OptionPane.background", new ColorUIResource(208, 225, 252));
                        UIManager.put("Panel.background", new ColorUIResource(208, 225, 252));
                        showMessageDialog(null, "<html><span style='color:#2e5aac'>Талоны закончились!</span></html>", "Info!", JOptionPane.INFORMATION_MESSAGE, icon);
                    }
                } catch (NumberFormatException ignored) {
                }
            } else {
                tickets.addAll(ticketsDoctor);
                XmlWriter.writeToFile("file.xml", doctors, tickets);
                ticketsTable.updateUI();
                doctorsTable.updateUI();
                ImageIcon icon = new ImageIcon(new ImageIcon("img\\warning.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
                UIManager.put("OptionPane.background", new ColorUIResource(254, 238, 193));
                UIManager.put("Panel.background", new ColorUIResource(254, 238, 193));
                showMessageDialog(null, "<html><span style='color:#b95000'>Такой талон уже есть," +
                        " <br> или врач в это время занят!</span></html>", "Warning!", JOptionPane.WARNING_MESSAGE, icon);
            }
        } catch (Exception e2) {
            ImageIcon icon = new ImageIcon(new ImageIcon("img\\error.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            UIManager.put("OptionPane.background", new ColorUIResource(250, 208, 204));
            UIManager.put("Panel.background", new ColorUIResource(250, 208, 204));
            showMessageDialog(null, "<html><span style='color:#DA1414'>Ошибка добавления объекта," +
                    " проверьте введенные данные!</span></html>", "Error!", JOptionPane.ERROR_MESSAGE, icon);
        }
    }
}