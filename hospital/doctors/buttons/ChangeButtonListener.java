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
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class ChangeButtonListener implements ActionListener {
    private JTable table;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
    private JTextField specField, boolField, areaField,
            cabinetField, rStartField, rEndField;
    private List<Doctor> doctors;
    private List<Ticket> tickets = TicketsXmlReader.readFromFile("file.xml");

    public ChangeButtonListener(JTable table, List<Doctor> doctors, JTextField specField,
                                JTextField boolField, JTextField areaField, JTextField cabinetField,
                                JTextField rStartField, JTextField rEndField) {
        this.doctors = doctors;
        this.table = table;
        this.specField = specField;
        this.boolField = boolField;
        this.areaField = areaField;
        this.cabinetField = cabinetField;
        this.rStartField = rStartField;
        this.rEndField = rEndField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int selectedRow = table.getSelectedRow();
            boolean isNarrowSpec = boolField.getText().equalsIgnoreCase("да");
            int areaNumber = 0;
            if (!isNarrowSpec) areaNumber = Integer.valueOf(areaField.getText());
            doctors.get(selectedRow).setSpeciality(specField.getText());
            doctors.get(selectedRow).setNarrowSpecialist(isNarrowSpec);
            doctors.get(selectedRow).setAreaNumber(areaNumber);
            doctors.get(selectedRow).setCabinetNumber(Integer.valueOf(cabinetField.getText()));
            doctors.get(selectedRow).setReceptionStart(LocalTime.parse(rStartField.getText(), format));
            doctors.get(selectedRow).setEndOfReception(LocalTime.parse(rEndField.getText(), format));
            XmlWriter.writeToFile("file.xml", doctors, tickets);
            table.updateUI();
        } catch (IndexOutOfBoundsException | NullPointerException | DateTimeParseException e1) {
            ImageIcon icon = new ImageIcon(new ImageIcon("img\\error.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            UIManager.put("OptionPane.background", new ColorUIResource(250, 208, 204));
            UIManager.put("Panel.background", new ColorUIResource(250, 208, 204));
            showMessageDialog(null, "<html><span style='color:#DA1414'>Ошибка изменения объекта," +
                    " проверьте введенные данные!</span></html>", "Error!", JOptionPane.ERROR_MESSAGE, icon);
        } catch (NumberFormatException e2) {
            ImageIcon icon = new ImageIcon(new ImageIcon("img\\warning.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            UIManager.put("OptionPane.background", new ColorUIResource(254, 238, 193));
            UIManager.put("Panel.background", new ColorUIResource(254, 238, 193));
            showMessageDialog(null, "<html><span style='color:#b95000'>Объект изменения не выбран!</span></html>", "Warning!", JOptionPane.WARNING_MESSAGE, icon);
        }
    }
}