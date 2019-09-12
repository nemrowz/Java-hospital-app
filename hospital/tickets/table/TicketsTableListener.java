package hospital.tickets.table;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TicketsTableListener implements MouseListener {

    private TicketTableModel ticketTableModel;
    private JTable table;
    private JTextField timeFiled, durationField, nameField;

    public TicketsTableListener(JTable table, TicketTableModel ticketTableModel, JTextField timeFiled,
                                JTextField durationField, JTextField nameField) {
        this.table = table;
        this.ticketTableModel = ticketTableModel;
        this.timeFiled = timeFiled;
        this.durationField = durationField;
        this.nameField = nameField;
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
        getSelectedData(ticketTableModel);
    }

    public void getSelectedData(TicketTableModel ticketTableModel) {
        int selectedRow = table.getSelectedRow();
        timeFiled.setText((String) ticketTableModel.getValueAt(selectedRow, 1));
        durationField.setText(String.valueOf(ticketTableModel.getValueAt(selectedRow, 2)));
        nameField.setText((String) ticketTableModel.getValueAt(selectedRow, 4));
    }
}