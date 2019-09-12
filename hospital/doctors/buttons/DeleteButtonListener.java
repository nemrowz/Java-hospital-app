package hospital.doctors.buttons;

import hospital.doctors.Doctor;
import hospital.tickets.Ticket;
import hospital.inNout.TicketsXmlReader;
import hospital.inNout.XmlWriter;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class DeleteButtonListener implements ActionListener {
    private JTable table;
    private List<Doctor> doctors;
    private List<Ticket> tickets = TicketsXmlReader.readFromFile("file.xml");

    public DeleteButtonListener(JTable table, List<Doctor> doctors) {
        this.doctors = doctors;
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int selectedRow = table.getSelectedRow();
            doctors.remove(selectedRow);
            XmlWriter.writeToFile("file.xml", doctors, tickets);
            table.updateUI();
        } catch (IndexOutOfBoundsException e1) {
            ImageIcon icon = new ImageIcon(new ImageIcon("img\\warning.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            UIManager.put("OptionPane.background", new ColorUIResource(254, 238, 193));
            UIManager.put("Panel.background", new ColorUIResource(254, 238, 193));
            showMessageDialog(null, "<html><span style='color:#b95000'>Объект удаления не выбран</span></html>", "Warning!", JOptionPane.WARNING_MESSAGE, icon);
        }
    }
}