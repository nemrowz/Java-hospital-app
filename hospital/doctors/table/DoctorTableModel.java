package hospital.doctors.table;

import hospital.doctors.Doctor;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DoctorTableModel implements TableModel {
    private List<Doctor> doctors;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");

    public DoctorTableModel(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    @Override
    public int getRowCount() {
        return doctors.size();
    }

    @Override
    public int getColumnCount() {
        return 12;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "№";
            case 1:
                return "Специальность";
            case 2:
                return "Фамилия";
            case 3:
                return "Имя";
            case 4:
                return "Отчество";
            case 5:
                return "Узкий специалист";
            case 6:
                return "№ участка";
            case 7:
                return "№ кабинета";
            case 8:
                return "Начала приема";
            case 9:
                return "Окончания приема";
            case 10:
                return "Свободные талоны";
            case 11:
                return "Общее время приёма";
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
                return doctors.get(rowIndex).getSpeciality();
            case 2:
                return doctors.get(rowIndex).getSurname();
            case 3:
                return doctors.get(rowIndex).getName();
            case 4:
                return doctors.get(rowIndex).getPatronymic();
            case 5:
                if (doctors.get(rowIndex).isNarrowSpecialist())
                    return "Да";
                else return "Нет";
            case 6:
                if (doctors.get(rowIndex).getAreaNumber() != 0)
                    return doctors.get(rowIndex).getAreaNumber();
                else return "";
            case 7:
                return doctors.get(rowIndex).getCabinetNumber();
            case 8:
                return format.format(doctors.get(rowIndex).getReceptionStart());
            case 9:
                return format.format(doctors.get(rowIndex).getEndOfReception());
            case 10:
                return doctors.get(rowIndex).getNumberOfTickets();
            case 11:
                return doctors.get(rowIndex).getTimeOfReceipt();
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

    public List<Doctor> getDoctors(int[] indexes) {
        List<Doctor> result = new ArrayList<>();
        for (int index : indexes) {
            result.add(doctors.get(index));
        }
        return result;
    }
}