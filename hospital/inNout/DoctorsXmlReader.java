package hospital.inNout;

import hospital.doctors.Doctor;

import javax.swing.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class DoctorsXmlReader {
    public static List<Doctor> readFromFile(String fileName) {
        List<Doctor> doctors = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
        try {
            InputStream stream = new FileInputStream(fileName);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(stream);
            Doctor doctor = null;
            int elementType;
            String tagName;
            while (reader.hasNext()) {
                elementType = reader.next();
                switch (elementType) {
                    case XMLStreamReader.START_ELEMENT: {
                        tagName = reader.getLocalName();
                        switch (tagName) {
                            case "doctor":
                                doctor = new Doctor();
                                String speciality = reader.getAttributeValue(null, "speciality");
                                String surname = reader.getAttributeValue(null, "surname");
                                String name = reader.getAttributeValue(null, "name");
                                String patronymic = reader.getAttributeValue(null, "patronymic");
                                String isNarrowSpecialist = reader.getAttributeValue(null, "isNarrowSpecialist");
                                String areaNumber = reader.getAttributeValue(null, "areaNumber");
                                String cabinetNumber = reader.getAttributeValue(null, "cabinetNumber");
                                String receptionStart = reader.getAttributeValue(null, "receptionStart");
                                String endOfReception = reader.getAttributeValue(null, "endOfReception");
                                doctor.setDoctor(speciality, surname, name, patronymic, Boolean.valueOf(isNarrowSpecialist),
                                        Integer.valueOf(areaNumber), Integer.valueOf(cabinetNumber), LocalTime.parse(receptionStart, format),
                                        LocalTime.parse(endOfReception, format));
                                break;
                        }
                        break;
                    }
                    case XMLStreamReader.END_ELEMENT: {
                        tagName = reader.getLocalName();
                        switch (tagName) {
                            case "doctor":
                                doctors.add(doctor);
                                break;
                        }
                        break;
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException | XMLStreamException | DateTimeParseException | NumberFormatException e) {
            showMessageDialog(null, "", "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return doctors;
    }
}