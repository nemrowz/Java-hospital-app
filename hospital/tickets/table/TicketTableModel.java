package hospital.tickets.table;

import hospital.tickets.Ticket;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TicketTableModel implements TableModel {

    private List<Ticket> tickets;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");

    public TicketTableModel(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public int getRowCount() {
        return tickets.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "№";
            case 1:
                return "Начало приема";
            case 2:
                return "Длительность";
            case 3:
                return "Свободен ли талон";
            case 4:
                return "ФИО";
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return format.format(tickets.get(rowIndex).getTicketStartTime());
            case 2:
                return tickets.get(rowIndex).getReceiptDuration();
            case 3:
                if (tickets.get(rowIndex).isTicketFree())
                    return "Да";
                else return "Нет";
            case 4:
                return tickets.get(rowIndex).getPacientName();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
}