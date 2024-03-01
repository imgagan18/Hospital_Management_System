package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }

    public void AddPatient(){
        System.out.print("enter the Patient name ");
        String name= scanner.next();
        System.out.print("enter the Patient age ");
        String age= scanner.next();
        System.out.print("enter the Patient Gender ");
        String gender= scanner.next();
//add patients
        try {
            String query = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Use preparedStatement (not PreparedStatement) to set values
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, Integer.parseInt(age));
            preparedStatement.setString(3, gender);

            //filled the three placeholders validated in the query and saved it in affected rows then checked afftected rows of it has the values

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Patient added successfully!!");
            } else {
                System.out.println("Failed to add the patient");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // view patient

    public void viewPatients(){
        String query="SELECT * FROM patients";
        try {//pre-paid statement to enhance the speed of the code
        PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();//prints using next nam ke pointer
            //use format specifiers
            System.out.println("patients");
            System.out.println("+--------------+--------------------+-----------+--------+");
            System.out.println("|  patient id  |name                |age        |gender  |");
            System.out.println("+--------------+--------------------+-----------+--------+");
            while (resultSet.next()){
                int id=resultSet.getInt("id");//integer type
                String name=resultSet.getString("name");
                int age=resultSet.getInt("age");
                String gender=resultSet.getString("gender");
                System.out.printf("|%-15s|%-22s|%-12s|%-10s|\n",id,name,age,gender);
                System.out.println("+--------------+--------------------+-----------+--------+");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //get patient
    public boolean getPatientByID(int id){
        String query="select * from patients WHERE id=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

//view patient,get patents,add patients are the three methods used to get this