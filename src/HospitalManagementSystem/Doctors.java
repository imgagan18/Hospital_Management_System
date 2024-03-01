package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
    private Connection connection;


    public Doctors(Connection connection){//remove add patient as it is done via the DBA
        this.connection=connection;

    }
    // view patient
    public void viewDoctors(){
        String query="select * from Doctors";
        try {//pre-paid statement to enhance the speed of the code
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();//prints using next nam ke pointer
            //use format specifiers
            System.out.println("Doctors:");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");//integer type
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("|%-15s|%-22s|%-19s|\n",id,name,specialization);
                System.out.println("+------------+--------------------+------------------+");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //get patient
    public boolean getDoctorsByID(int id) {
        String query = "SELECT * FROM doctors WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
