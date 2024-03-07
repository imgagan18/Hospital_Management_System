package HospitalManagementSystem;

import javax.print.Doc;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";

    private static final String username="root";

    private static final String password="pass123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection,scanner);
            Doctors doctors=new Doctors(connection);

            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. ADD  patient");
                System.out.println("2. View Patients");
                System.out.println("3. View doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. EXIT");
                System.out.println("enter your choice: ");
                int choice=scanner.nextInt();

                switch (choice){
                    case 1:
                        //add patient
                        patient.AddPatient();
                        System.out.println();
                        break;
                    case 2:
                        //view patient
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        // view doctors
                        doctors.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        //book appointment
                        BookAppointment(patient,doctors,connection,scanner);
                        System.out.println();
                        break;

                    case 5:
                        System.out.println("THANK YOU FOR USING THIS SYSTEM :)");
                        return;
                    default:
                        System.out.println("Enter a valid choice");
                        break;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void BookAppointment(Patient patient,Doctors doctors,Connection connection,Scanner scanner){
        System.out.println("Enter patient ID");
        int patientID=scanner.nextInt();
        System.out.println("Enter Doctors ID");
        int doctorID=scanner.nextInt();
        System.out.println("enter the appointment date (YYYY-MM-DD)");
        String appointmentDate=scanner.next();
        if(patient.getPatientByID(patientID) && doctors.getDoctorsByID((doctorID))){//woolens true or false
       if(checkDoctorAvailability(doctorID,appointmentDate,connection)){
          String appointmentQuery="INSERT INTO appointments(patient_id,doctors_id,appointments_date) VALUES (?,?,?)";
          try {
              PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
              preparedStatement.setInt(1, patientID);
              preparedStatement.setInt(2, doctorID);
              preparedStatement.setString(3, appointmentDate);
              int rowsAffected=preparedStatement.executeUpdate();
              if (rowsAffected>0){
                  System.out.println("appointment booked!!");
              }else {
                  System.out.println("failed to book appointment");
              }
          }catch (SQLException e){
              e.printStackTrace();
          }
       }else{
           System.out.println("doctor not available on this date ");
       }
        }else {
            System.out.println("either doc or patient not available on this date");
        }
    }
    public static boolean checkDoctorAvailability(int doctorID, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctors_id=? AND DATE(appointments_date)=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorID);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
