package hospital.doctors.buttons;

import hospital.doctors.Doctor;
import hospital.inNout.TicketsXmlReader;
import hospital.doctors.table.DoctorTableModel;
import hospital.tickets.buttons.*;
import hospital.tickets.table.*;
import hospital.tickets.Ticket;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class ReadButtonListener implements ActionListener {
    private JTable table;
    private List<Doctor> doctors;

    public ReadButtonListener(JTable table, List<Doctor> doctors) {
        this.table = table;
        this.doctors = doctors;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            TableModel model = table.getModel();
            if (model instanceof DoctorTableModel) {
                Doctor doctor = doctors.get(table.getSelectedRow());
                String name = doctor.getSurname() + " " + doctor.getName().charAt(0) + "." + doctor.getPatronymic().charAt(0) + ".";
                Font font = new Font("", Font.BOLD, 12);
                JFrame frame = new JFrame("Талоны");
                frame.setIconImage(new ImageIcon("img\\ticket.png").getImage());
                frame.getContentPane().setBackground(new Color(238, 101, 89));
                frame.setBounds(400, 50, 700, 700);
                JLabel lblText = new JLabel("Талоны:    " + name);
                lblText.setHorizontalAlignment(JTextField.CENTER);
                lblText.setForeground(new Color(250, 208, 204));
                lblText.setFont(new Font("", Font.PLAIN, 26));
                frame.add(lblText, BorderLayout.NORTH);

                List<Ticket> tickets = TicketsXmlReader.readFromFile("file.xml");
                List<Ticket> doctorTickets = new ArrayList<>();
                for (Ticket ticket : tickets) {
                    if (name.equalsIgnoreCase(ticket.getDoctorName()))
                        doctorTickets.add(ticket);
                }
                tickets.removeAll(doctorTickets);
                TicketTableModel ticketTableModel = new TicketTableModel(doctorTickets);
                JTable ticketsTable = new JTable(ticketTableModel);

                Border tableBorder = BorderFactory.createLineBorder(new Color(120, 169, 247), 1);
                ticketsTable.setBorder(tableBorder);
                ticketsTable.setFont(font);
                ticketsTable.setForeground(new Color(25, 50, 91));
                ticketsTable.setBackground(new Color(178, 206, 251));
                ticketsTable.setSelectionForeground(new Color(178, 206, 251));
                ticketsTable.setSelectionBackground(new Color(66, 133, 244));

                JTableHeader header = ticketsTable.getTableHeader();
                Border headerBorder = BorderFactory.createLineBorder(new Color(66, 133, 244), 1);
                header.setBorder(headerBorder);
                header.setBorder(null);
                header.setBackground(new Color(90, 148, 245));
                header.setForeground(new Color(208, 225, 252));
                header.setFont(new Font("", Font.BOLD, 12));

                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < 5; i++)
                    ticketsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                Border border = BorderFactory.createLineBorder(new Color(234, 67, 52), 1);
                JScrollPane scrollPane = new JScrollPane(ticketsTable);
                scrollPane.setBorder(border);
                scrollPane.getViewport().setBackground(new Color(254, 250, 236));

                frame.add(scrollPane, BorderLayout.CENTER);
                JPanel panel = new JPanel();
                JPanel fldsPanel = new JPanel();
                JPanel btnsPanel = new JPanel();
                btnsPanel.setBackground(new Color(236, 84, 70));
                fldsPanel.setBackground(new Color(236, 84, 70));
                fldsPanel.setLayout(new GridLayout());
                panel.setBackground(new Color(236, 84, 70));

                Border fieldsBorder = BorderFactory.createLineBorder(new Color(220, 165, 4), 1);
                JTextField timeFiled = new JTextField("Начало приема");
                timeFiled.setFont(font);
                timeFiled.setBorder(fieldsBorder);
                timeFiled.setForeground(new Color(157, 118, 3));
                timeFiled.setBackground(new Color(253, 222, 130));
                timeFiled.setHorizontalAlignment(JLabel.CENTER);

                JTextField durationField = new JTextField("Длительность");
                durationField.setForeground(new Color(157, 118, 3));
                durationField.setFont(font);
                durationField.setBorder(fieldsBorder);
                durationField.setBackground(new Color(253, 222, 130));
                durationField.setHorizontalAlignment(JLabel.CENTER);

                JTextField nameField = new JTextField("ФИО");
                nameField.setForeground(new Color(157, 118, 3));
                nameField.setFont(font);
                nameField.setBorder(fieldsBorder);
                nameField.setBackground(new Color(253, 222, 130));
                nameField.setHorizontalAlignment(JLabel.CENTER);

                fldsPanel.add(timeFiled);
                fldsPanel.add(durationField);
                fldsPanel.add(nameField);

                JButton addButton = new JButton("<html><span style='color:#1A542A'>Добавить талон</span></html>");
                addButton.setBorderPainted(false);
                addButton.addActionListener(new TicketAddButton(table, doctor, ticketsTable, doctors, tickets, doctorTickets, timeFiled, durationField, nameField));
                addButton.setBackground(new Color(128, 201, 148));
                JButton changeButton = new JButton("<html><span style='color:#216934'>Изменить талон</span></html>");
                changeButton.addActionListener(new ChangeTicketButton(table, ticketsTable, doctors, tickets, doctorTickets, timeFiled, durationField, nameField));
                changeButton.setBorderPainted(false);
                changeButton.setBackground(new Color(154, 211, 169));
                JButton deleteButton = new JButton("<html><span style='color:#277E3E'>Удалить талон</span></html>");
                deleteButton.setBorderPainted(false);
                deleteButton.addActionListener(new TicketDeleteButton(table, doctor, ticketsTable, doctors, tickets, doctorTickets));
                deleteButton.setBackground(new Color(179, 222, 191));

                btnsPanel.add(addButton);
                btnsPanel.add(changeButton);
                btnsPanel.add(deleteButton);

                panel.setLayout(new GridLayout(0, 1));
                panel.add(fldsPanel);
                panel.add(btnsPanel);
                frame.add(panel, BorderLayout.SOUTH);
                ticketsTable.addMouseListener(new TicketsTableListener(ticketsTable, ticketTableModel, timeFiled, durationField, nameField));
                ticketsTable.updateUI();
                table.updateUI();
                frame.validate();
                frame.setVisible(true);
            }
        } catch (IndexOutOfBoundsException e1) {
            ImageIcon icon = new ImageIcon(new ImageIcon("img\\warning.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            UIManager.put("OptionPane.background", new ColorUIResource(254, 238, 193));
            UIManager.put("Panel.background", new ColorUIResource(254, 238, 193));
            showMessageDialog(null, "<html><span style='color:#b95000'>Врач не выбран!</span></html>", "Warning!", JOptionPane.WARNING_MESSAGE, icon);
        }
    }
}