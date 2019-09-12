package hospital.doctors;

import hospital.tickets.table.TicketTableModel;

import javax.swing.*;
import java.time.LocalTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Doctor {
    private String speciality;
    private String surname;
    private String name;
    private String patronymic;
    private boolean isNarrowSpecialist;
    private int areaNumber;
    private int cabinetNumber;
    private LocalTime receptionStart;
    private LocalTime endOfReception;
    private int numberOfTickets;
    private double timeOfReceipt;

    public String getSpeciality() {
        return speciality;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public boolean isNarrowSpecialist() {
        return isNarrowSpecialist;
    }

    public int getAreaNumber() {
        return areaNumber;
    }

    public int getCabinetNumber() {
        return cabinetNumber;
    }

    public LocalTime getReceptionStart() {
        return receptionStart;
    }

    public LocalTime getEndOfReception() {
        return endOfReception;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public double getTimeOfReceipt() {
        return timeOfReceipt;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setNarrowSpecialist(boolean isNarrowSpecialist) {
        this.isNarrowSpecialist = isNarrowSpecialist;
    }

    public void setAreaNumber(int areaNumber) {
        this.areaNumber = areaNumber;
    }

    public void setCabinetNumber(int cabinetNumber) {
        this.cabinetNumber = cabinetNumber;
    }

    public void setReceptionStart(LocalTime receptionStart) {
        this.receptionStart = receptionStart;
    }

    public void setEndOfReception(LocalTime endOfReception) {
        this.endOfReception = endOfReception;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public void setTimeOfReceipt(double timeOfReceipt) {
        this.timeOfReceipt = timeOfReceipt;
    }

    public void setDoctor(String speciality, String surname, String name,
                          String patronymic, boolean isNarrowSpecialist, int areaNumber,
                          int cabinetNumber, LocalTime receptionStart, LocalTime endOfReception) {
        this.speciality = speciality;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.isNarrowSpecialist = isNarrowSpecialist;
        this.areaNumber = areaNumber;
        this.cabinetNumber = cabinetNumber;
        this.receptionStart = receptionStart;
        this.endOfReception = endOfReception;
    }

    public static double getDoctorTime(Doctor doctor) {
        double doctorT;
        doctorT = doctor.getReceptionStart().until(doctor.getEndOfReception(), MINUTES) / 60.0;
        return doctorT;
    }
}