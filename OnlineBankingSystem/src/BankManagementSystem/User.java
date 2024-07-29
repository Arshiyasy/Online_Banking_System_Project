package BankManagementSystem;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {

    private Connection connection;
    private Scanner sc;

    public User(Connection connection,Scanner sc){
        this.connection=connection;
        this.sc=sc;
    }

    // New Registration=================================================================================================
    public void register(){
        sc.nextLine();
        System.out.print("Full Name: ");
        String full_name=sc.nextLine();
        System.out.print("Email: ");
        String email=sc.nextLine();
        System.out.print("Password: ");
        String password=sc.nextLine();
        if(user_exist(email)){
            System.out.println("User already exists fot this Email Address!!");
            return;
        }
        String register_query="INSERT INTO USER(full_name,email,password) VALUES(?,?,?)";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(register_query);
            preparedStatement.setString(1,full_name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);
            int affected_rows=preparedStatement.executeUpdate();
            if(affected_rows>0){
                System.out.println();
                System.out.println("REGISTRATION SUCCESSFUL!");
            }else{
                System.out.println();
                System.out.println("REGISTRATION FAILED!!");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    //LOGIN=============================================================================================================
    public String login(){
        sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        String login_query="SELECT * FROM USER WHERE email= ?AND password= ?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(login_query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return email;
            }else{
                return null;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //EXISTENCE OF USER=================================================================================================
    public boolean user_exist(String email){
        String query="SELECT * FROM user WHERE email= ?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;

            }
            else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
