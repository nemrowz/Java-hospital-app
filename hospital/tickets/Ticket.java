package hospital.tickets;

import hospital.doctors.Doctor;

import javax.swing.*;
import java.time.LocalTime;
import java.util.List;

public class Ticket {
    private String doctorName;
    private LocalTime ticketStartTime;
    private int receiptDuration;
    private boolean isTicketFree;
    private String pacientName;

    public String getDoctorName() {
        return doctorName;
    }

    public LocalTime getTicketStartTime() {
        return ticketStartTime;
    }

    public int getReceiptDuration() {
        return receiptDuration;
    }

    public boolean isTicketFree() {
        return isTicketFree;
    }

    public String getPacientName() {
        return pacientName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setTicketStartTime(LocalTime ticketStartTime) {
        this.ticketStartTime = ticketStartTime;
    }

    public void setReceiptDuration(int receiptDuration) {
        this.receiptDuration = receiptDuration;
    }

    public void setTicketFree(boolean isTicketFree) {
        this.isTicketFree = isTicketFree;
    }

    public void setPacientName(String pacientName) {
        this.pacientName = pacientName;
    }

    public void setTicket(String doctorName, LocalTime ticketStartTime, int receiptDuration, boolean isTicketFree, String pacientName) {
        this.doctorName = doctorName;
        this.pacientName = pacientName;
        this.isTicketFree = isTicketFree;
        this.ticketStartTime = ticketStartTime;
        this.receiptDuration = receiptDuration;
    }

    public static Doctor getSelectedDoctor(String ticketDoctor, List<Doctor> doctors) {
        Doctor doc = doctors.get(0);
        for (Doctor doctor : doctors) {
            String doctorName = doctor.getSurname() + " " + doctor.getName().charAt(0) + "." + doctor.getPatronymic().charAt(0) + ".";
            if (ticketDoctor.equals(doctorName))
                doc = doctor;
        }
        return doc;
    }
}