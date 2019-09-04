package hospital;

import hospital.doctors.buttons.*;
import hospital.doctors.Doctor;
import hospital.inNout.*;
import hospital.doctors.table.*;
import hospital.tickets.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

class Form extends JFrame {

    Form() {
        super("Поликлиника");
        super.setIconImage(new ImageIcon("img\\hospital.png").getImage());
        super.getContentPane().setBackground(new Color(90, 148, 245));
        setBounds(0, 50, 1535, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblText = new JLabel("Врачи");
        lblText.setHorizontalAlignment(JTextField.CENTER);
        lblText.setForeground(new Color(208, 225, 252));
        lblText.setFont(new Font("", Font.BOLD, 36));
        add(lblText, BorderLayout.NORTH);
        List<Doctor> doctors = DoctorsXmlReader.readFromFile("file.xml");
        List<Ticket> tickets = TicketsXmlReader.readFromFile("file.xml");
        for (Doctor doctor : doctors) {
            String name = doctor.getSurname() + " " + doctor.getName().charAt(0) + "." + doctor.getPatronymic().charAt(0) + ".";
            doctor.setTimeOfReceipt(new TicketsCount(tickets).timeOfReceipt(name));
            doctor.setNumberOfTickets(new TicketsCount(tickets).ticketsNumber(name));
        }
        DoctorTableModel model = new DoctorTableModel(doctors);
        Border tableBorder = BorderFactory.createLineBorder(new Color(239, 114, 104), 1);
        JTable table = new JTable(model);
        Font font = new Font("", Font.BOLD, 12);
        table.setFont(font);
        table.setForeground(new Color(146, 42, 33));
        table.setBackground(new Color(247, 185, 179));
        table.setBorder(tableBorder);
        table.setSelectionBackground(new Color(239, 114, 104));
        table.setSelectionForeground(new Color(250, 208, 204));
        table.getColumnModel().getColumn(0).setPreferredWidth(1);
        table.getColumnModel().getColumn(6).setPreferredWidth(1);
        table.getColumnModel().getColumn(7).setPreferredWidth(1);
        JTableHeader header = table.getTableHeader();
        Border headerBorder = BorderFactory.createLineBorder(new Color(237, 91, 78), 1);
        header.setBorder(headerBorder);
        header.setForeground(new Color(250, 208, 204));
        header.setBackground(new Color(237, 91, 78));
        header.setFont(new Font("", Font.BOLD, 12));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 12; i++)
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        Border border = BorderFactory.createLineBorder(new Color(66, 133, 244), 1);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(border);
        scrollPane.getViewport().setBackground(new Color(254, 250, 236));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        JPanel btnsPanel = new JPanel();
        JPanel fldsPanel = new JPanel();
        fldsPanel.setLayout(new GridLayout());
        panel.setBorder(border);
        btnsPanel.setBackground(new Color(66, 133, 244));
        fldsPanel.setBackground(new Color(66, 133, 244));
        panel.setBackground(new Color(66, 133, 244));

        Border fieldsBorder = BorderFactory.createLineBorder(new Color(220, 165, 4), 1);
        JTextField specField = new JTextField("Специальность");
        specField.setFont(font);
        specField.setForeground(new Color(157, 118, 3));
        specField.setBorder(fieldsBorder);
        specField.setBackground(new Color(253, 222, 130));
        specField.setHorizontalAlignment(JLabel.CENTER);

        JTextField surnameField = new JTextField("Фамилия");
        surnameField.setFont(font);
        surnameField.setForeground(new Color(125, 94, 3));
        surnameField.setBorder(fieldsBorder);
        surnameField.setBackground(new Color(251, 196, 36));
        surnameField.setHorizontalAlignment(JLabel.CENTER);

        JTextField nameField = new JTextField("Имя");
        nameField.setFont(font);
        nameField.setForeground(new Color(125, 94, 3));
        nameField.setBorder(fieldsBorder);
        nameField.setBackground(new Color(251, 196, 36));
        nameField.setHorizontalAlignment(JLabel.CENTER);

        JTextField patrField = new JTextField("Отчество");
        patrField.setFont(font);
        patrField.setForeground(new Color(125, 94, 3));
        patrField.setBorder(fieldsBorder);
        patrField.setBackground(new Color(251, 196, 36));
        patrField.setHorizontalAlignment(JLabel.CENTER);

        JTextField boolField = new JTextField("Узкий специалист");
        boolField.setFont(font);
        boolField.setForeground(new Color(157, 118, 3));
        boolField.setBorder(fieldsBorder);
        boolField.setBackground(new Color(253, 222, 130));
        boolField.setHorizontalAlignment(JLabel.CENTER);

        JTextField areaField = new JTextField("№ участка");
        areaField.setFont(font);
        areaField.setForeground(new Color(157, 118, 3));
        areaField.setBorder(fieldsBorder);
        areaField.setBackground(new Color(253, 222, 130));
        areaField.setHorizontalAlignment(JLabel.CENTER);

        JTextField cabinetField = new JTextField("№ кабинета");
        cabinetField.setFont(font);
        cabinetField.setForeground(new Color(157, 118, 3));
        cabinetField.setBorder(fieldsBorder);
        cabinetField.setBackground(new Color(253, 222, 130));
        cabinetField.setHorizontalAlignment(JLabel.CENTER);

        JTextField rStartField = new JTextField("Начало приема");
        rStartField.setFont(font);
        rStartField.setForeground(new Color(157, 118, 3));
        rStartField.setBorder(fieldsBorder);
        rStartField.setBackground(new Color(253, 222, 130));
        rStartField.setHorizontalAlignment(JLabel.CENTER);

        JTextField rEndField = new JTextField("Конец приема");
        rEndField.setFont(font);
        rEndField.setForeground(new Color(157, 118, 3));
        rEndField.setBorder(fieldsBorder);
        rEndField.setBackground(new Color(253, 222, 130));
        rEndField.setHorizontalAlignment(JLabel.CENTER);

        fldsPanel.add(specField);
        fldsPanel.add(surnameField);
        fldsPanel.add(nameField);
        fldsPanel.add(patrField);
        fldsPanel.add(boolField);
        fldsPanel.add(areaField);
        fldsPanel.add(cabinetField);
        fldsPanel.add(rStartField);
        fldsPanel.add(rEndField);

        JButton readButton = new JButton("<html><span style='color:#75221B'>Просмотреть талоны</span></html>");
        readButton.setBorderPainted(false);
        readButton.addActionListener(new ReadButtonListener(table, doctors));
        readButton.setBackground(new Color(239, 114, 104));
        JButton addButton = new JButton("<html><span style='color:#1A542A'>Добавить врача</span></html>");
        addButton.addActionListener(new AddButtonListener(table, doctors, specField, surnameField, nameField, patrField, boolField, areaField, cabinetField, rStartField, rEndField));
        addButton.setBorderPainted(false);
        addButton.setBackground(new Color(128, 201, 148));
        JButton changeButton = new JButton("<html><span style='color:#216934'>Изменить врача</span></html>");
        changeButton.addActionListener(new ChangeButtonListener(table, doctors, specField, boolField, areaField, cabinetField, rStartField, rEndField));
        changeButton.setBorderPainted(false);
        changeButton.setBackground(new Color(154, 211, 169));
        JButton deleteButton = new JButton("<html><span style='color:#277E3E'>Удалить врача</span></html>");
        deleteButton.addActionListener(new DeleteButtonListener(table, doctors));
        deleteButton.setBorderPainted(false);
        deleteButton.setBackground(new Color(179, 222, 191));

        btnsPanel.add(readButton);
        btnsPanel.add(addButton);
        btnsPanel.add(changeButton);
        btnsPanel.add(deleteButton);

        panel.add(fldsPanel);
        panel.add(btnsPanel);
        add(panel, BorderLayout.SOUTH);
        table.addMouseListener(new DoctorsTableListener(table, model, specField,
                surnameField, nameField,
                patrField, boolField, areaField,
                cabinetField, rStartField, rEndField));
        table.updateUI();
        validate();
        setVisible(true);
    }
}