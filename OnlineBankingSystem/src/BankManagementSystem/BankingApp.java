package BankManagementSystem;

import java.sql.*;
import java.util.Scanner;

import java.util.Scanner;
import static java.lang.Class.forName;

public class BankingApp {

    private static  final String url= "jdbc:mysql://localhost:3306/banking_system";
    private static  final String username="root";
    private static  final String password= "your_password";

    public static void main(String[] args) throws ClassNotFoundException,SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner sc = new Scanner(System.in);
            User user = new User(connection, sc);
            Accounts accounts = new Accounts(connection, sc);
            AccountManager accountManager = new AccountManager(connection, sc);

            String email;
            long account_number;

            while (true) {
                System.out.println("*******************WELCOME TO ONLINE BANKING SYSTEM*******************");
                System.out.println();
                System.out.println("              1. REGISTER");
                System.out.println("              2. LOGIN");
                System.out.println("              3. EXIT");
                System.out.println();
                System.out.print("Enter Your Choice: ");
                int choice1 = sc.nextInt();
                switch (choice1) {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        email = user.login();
                        if (email != null) {
                            System.out.println();
                            System.out.println("User Logged In!");
                            if (!accounts.account_exist(email)) {
                                System.out.println();
                                System.out.println("           1. Open a new Bank Account");
                                System.out.println("           2. Exit");
                                System.out.println();
                                System.out.print("Enter your Choice: ");
                                choice1 = sc.nextInt();
                                if (choice1 == 1) {
                                    account_number = accounts.open_account(email);
                                    System.out.println();
                                    System.out.println("Account Created Successfully");
                                    System.out.println("Your Account Number is: " + account_number);
                                } else {
                                    break;
                                }
                            }

                                account_number = accounts.getAccount_number(email);
                                int choice2 = 0;
                                while (choice2 != 5) {
                                    System.out.println();
                                    System.out.println("            1. DEBIT MONEY");
                                    System.out.println("            2. CREDIT MONEY");
                                    System.out.println("            3. TRANSFER MONEY");
                                    System.out.println("            4. CHECK BALANCE");
                                    System.out.println("            5. LOG OUT");
                                    System.out.println();
                                    System.out.print("Enter your Choice: ");
                                    choice2 = sc.nextInt();
                                    switch (choice2) {
                                        case 1:
                                            accountManager.debit_money(account_number);
                                            break;
                                        case 2:
                                            accountManager.credit_money(account_number);
                                            break;
                                        case 3:
                                            accountManager.transfer_money(account_number);
                                            break;
                                        case 4:
                                            accountManager.getBalance(account_number);
                                            break;
                                        case 5:
                                            break;
                                        default:
                                            System.out.println("Enter the Valid Choice!!");
                                            break;

                                    }

                                }

                            } else {
                                System.out.println("Incorrect Email or Password!");
                            }
                            case 3:
                                System.out.println();
                                System.out.println("THANK YOU FOR USING ONLINE BANKING!!!");
                                System.out.println("Exiting System!");
                                System.out.println("***************************THANK YOU********************************");
                                return;
                            default:
                                System.out.println("Enter Valid Choice!!");
                                break;
                        }
                }
            }catch(SQLException e){
                e.printStackTrace();
            }

        }
    }