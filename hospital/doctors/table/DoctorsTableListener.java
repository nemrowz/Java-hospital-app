package hospital.doctors.table;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DoctorsTableListener implements MouseListener {
    private DoctorTableModel doctorTableModel;
    private JTable table;
    private JTextField specField, surnameField, nameField,
            patrField, boolField, areaField,
            cabinetField, rStartField, rEndField;

    public DoctorsTableListener(JTable table, DoctorTableModel doctorTableModel, JTextField specField,
                                JTextField surnameField, JTextField nameField, JTextField patrField, JTextField boolField,
                                JTextField areaField, JTextField cabinetField, JTextField rStartField, JTextField rEndField) {
        this.table = table;
        this.doctorTableModel = doctorTableModel;
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

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        getSelectedData(doctorTableModel);
        table.updateUI();
    }

    public void getSelectedData(DoctorTableModel doctorTableModel) {
        int selectedRow = table.getSelectedRow();
        specField.setText((String) doctorTableModel.getValueAt(selectedRow, 1));
        surnameField.setText((String) doctorTableModel.getValueAt(selectedRow, 2));
        nameField.setText((String) doctorTableModel.getValueAt(selectedRow, 3));
        patrField.setText((String) doctorTableModel.getValueAt(selectedRow, 4));
        boolField.setText((String) doctorTableModel.getValueAt(selectedRow, 5));
        areaField.setText(String.valueOf(doctorTableModel.getValueAt(selectedRow, 6)));
        cabinetField.setText(String.valueOf(doctorTableModel.getValueAt(selectedRow, 7)));
        rStartField.setText((String) doctorTableModel.getValueAt(selectedRow, 8));
        rEndField.setText((String) doctorTableModel.getValueAt(selectedRow, 9));
    }
}