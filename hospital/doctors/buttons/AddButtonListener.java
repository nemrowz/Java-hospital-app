package hospital.doctors.buttons;

import hospital.doctors.Doctor;
import hospital.tickets.Ticket;
import hospital.inNout.*;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class AddButtonListener implements ActionListener {
    private JTable table;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
    private JTextField specField, surnameField, nameField,
            patrField, boolField, areaField,
            cabinetField, rStartField, rEndField;
    private List<Doctor> doctors;
    private List<Ticket> tickets = TicketsXmlReader.readFromFile("file.xml");

    public AddButtonListener(JTable table, List<Doctor> doctors, JTextField specField,
                             JTextField surnameField, JTextField nameField, JTextField patrField, JTextField boolField,
                             JTextField areaField, JTextField cabinetField, JTextField rStartField, JTextField rEndField) {
        this.table = table;
        this.doctors = doctors;
        this.specField = specField;
        this.surnameField = surnameField;
        this.nameField = nameField;
        this.patrField = patrField;
        this.boolField = boolField;
        this.areaField = areaField;
        this.cabinetField = cabinetField;
        this.rStartField = rStartField;
        this.rEndField = rEndField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int k = 0;
        for (Doctor doctor : doctors) {
            if (surnameField.getText().equals(doctor.getSurname()) && nameField.getText().equals(doctor.getName()) && patrField.getText().equals(doctor.getPatronymic()))
                k++;
        }
        if (k == 0) {
            try {
                Doctor newDoctor = new Doctor();
                boolean isNarrowSpec = boolField.getText().equalsIgnoreCase("да");
                int areaNumber = 0;
                if (!isNarrowSpec) areaNumber = Integer.valueOf(areaField.getText());
                newDoctor.setDoctor(specField.getText(), surnameField.getText(), nameField.getText(),
                        patrField.getText(), isNarrowSpec, areaNumber,
                        Integer.valueOf(cabinetField.getText()), LocalTime.parse(rStartField.getText(), format), LocalTime.parse(rEndField.getText(), format));
                doctors.add(newDoctor);
                XmlWriter.writeToFile("file.xml", doctors, tickets);
                table.updateUI();
            } catch (NullPointerException | NumberFormatException | DateTimeParseException e1) {
                ImageIcon icon = new ImageIcon(new ImageIcon("img\\error.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
                UIManager.put("OptionPane.background", new ColorUIResource(250, 208, 204));
                UIManager.put("Panel.background", new ColorUIResource(250, 208, 204));
                showMessageDialog(null, "<html><span style='color:#DA1414'>Ошибка добавления объекта," +
                        " проверьте введенные данные!</span></html>", "Error!", JOptionPane.ERROR_MESSAGE, icon);
            }
        } else {
            ImageIcon icon = new ImageIcon(new ImageIcon("img\\info.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            UIManager.put("OptionPane.background", new ColorUIResource(208, 225, 252));
            UIManager.put("Panel.background", new ColorUIResource(208, 225, 252));
            showMessageDialog(null, "<html><span style='color:#2e5aac'>Такой объект уже есть!</span></html>", "Info!", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
}