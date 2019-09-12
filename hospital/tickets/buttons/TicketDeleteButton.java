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
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.zip.DataFormatException;

import static javax.swing.JOptionPane.showMessageDialog;

public class TicketDeleteButton implements ActionListener {
    Doctor doctorT;
    private List<Doctor> doctors;
    private List<Ticket> ticketsDoctor, tickets;
    private JTable doctorsTable, ticketsTable;

    public TicketDeleteButton(JTable doctorsTable, Doctor doctorT, JTable ticketsTable, List<Doctor> doctors, List<Ticket> tickets, List<Ticket> ticketsDoctor) {
        this.doctorT = doctorT;
        this.doctorsTable = doctorsTable;
        this.ticketsTable = ticketsTable;
        this.doctors = doctors;
        this.tickets = tickets;
        this.ticketsDoctor = ticketsDoctor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tickets.removeAll(ticketsDoctor);
        try {
            int selectedRow = ticketsTable.getSelectedRow();
            ticketsDoctor.remove(selectedRow);
            DoctorTableModel doctorTableModel = (DoctorTableModel) doctorsTable.getModel();
            String name = doctorT.getSurname() + " " + doctorT.getName().charAt(0) + "." + doctorT.getPatronymic().charAt(0) + ".";
            tickets.addAll(ticketsDoctor);
            for (Doctor doctor :
                    doctorTableModel.getDoctors(doctorsTable.getSelectedRows())) {
                doctor.setTimeOfReceipt(new TicketsCount(ticketsDoctor).timeOfReceipt(name));
                doctor.setNumberOfTickets(new TicketsCount(ticketsDoctor).ticketsNumber(name));
            }
            XmlWriter.writeToFile("file.xml", doctors, tickets);
            ticketsTable.updateUI();
            doctorsTable.updateUI();
        } catch (IndexOutOfBoundsException e1) {
            ImageIcon icon = new ImageIcon(new ImageIcon("img\\warning.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            UIManager.put("OptionPane.background", new ColorUIResource(254, 238, 193));
            UIManager.put("Panel.background", new ColorUIResource(254, 238, 193));
            showMessageDialog(null, "<html><span style='color:#b95000'>Объект удаления не выбран</span></html>", "Warning!", JOptionPane.WARNING_MESSAGE, icon);
        }
    }
}