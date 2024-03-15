import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Doctor {

    // Doctors Data members
    private int DoctorsId;
    private String Specialization;
    private String Dr_name;
    private String Contact_number;
    private String City;
    private String State;
    private String Password;
    private String Address;
    String appointment_date;
    // Basic patients data member
    private int PatientId;
    private String Patient_name;
    private String PatientContactNumber;
    private int Appointment_charge;
    Connection connection = Database.getInstance();

    void Login(Scanner sc) {
        int ch = 4;
        while (ch >= 1) {

            try {
                System.out.println("Enter User Id ");
                DoctorsId = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter password ");
                Password = sc.nextLine();

                String SqlQuary = "SELECT * FROM doctors WHERE DoctorId =? AND DPassword =?";

                PreparedStatement pstmt = connection.prepareStatement(SqlQuary);
                pstmt.setInt(1, DoctorsId);
                pstmt.setString(2, Password);
                ResultSet result = pstmt.executeQuery();

                if (result.next()) {
                    ch = -1;
                    Dr_name = result.getString("Doctorsname");
                    Specialization = result.getString("Specialist");
                    // Address = result.getString("Address");
                    City = result.getString("City");
                    State = result.getString("State");
                    Contact_number = result.getString("Contact_number");
                    Appointment_charge = result.getInt("Appointment_charge");
                    System.out.println("congratulations logged in successfully");
                    // System.out.println("Appointment_charge :" + Appointment_charge);
                    Dashboard(sc);

                } else if (ch == 1) {
                    System.out.println("attempt limit exit try after some time ....");
                    System.exit(0);

                } else {
                    ch--;
                    System.out.println("Wrong User Id or passward ");
                    System.out.println("Do you wanna try again ?? left attempt's : " + ch);
                    char attemp = sc.next().charAt(0);
                    if (attemp == 'Y' || attemp == 'y') {
                        continue;
                    } else {
                        System.out.println("Try after some time Thank You....");
                        System.exit(0);
                    }
                }

                result.close();
                pstmt.close();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid User Id.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                Login(sc);
            } catch (SQLException e) {
                System.out.println("Exception aa gya bhai :");
                e.printStackTrace();
            }
        }
    }

    void Dashboard(Scanner sc) {
        System.out.println("-------------------DOCTOR DASHBOARD---------------------");
        System.out.println("1 :ViewProfile                     2 :ViewAppointments  \n3 :AttendPatents  4 :Logout");
        byte cnt = sc.nextByte();

        switch (cnt) {
            case 1:
                ViewProfile(sc);

                break;
            case 2:
                ViewAppointments(sc);

                break;
            case 3:
                AddtendPatents(sc);

                break;
            case 4:
                Logout();

                break;

            default:
                System.out.println("Invalid Entry ...");
                break;
        }
    }

    void ViewProfile(Scanner sc) {
        System.out.println(
                "|---------+------------------------+---------------------+---------------------+-------------+--------------------+---------------------|");

        System.out.println(
                "| USER ID | NAME                   | SPECIALIZATION      | CONTACT DETAILS     | CITY        |        STATE       | Appointment charges |");

        System.out.println(
                "|---------+------------------------+---------------------+---------------------+-------------+--------------------+---------------------|");

        System.out.printf("| %-7s | %-22s | %-19s | %-19s | %-11s | %-18s | %-20s|\n", DoctorsId, Dr_name,
                Specialization, Contact_number, City, State, Appointment_charge);

        System.out.println(
                "|---------+------------------------+---------------------+---------------------+-------------+--------------------+---------------------|");

        exit(sc);
    }

    void ViewAppointments(Scanner sc) {
        System.out.println("Your appointments .....");
        String appointment = "SELECT *FROM Appointments WHERE DoctorId=?";
        try {
            boolean cnt = false;
            PreparedStatement pstmt = connection.prepareStatement(appointment);
            pstmt.setInt(1, DoctorsId);
            // pstmt.setInt(2, DoctorsId);
            ResultSet result = pstmt.executeQuery();
            System.out.println(
                    "|-------+-------+-------------------------------+---------------------------+-------------------+--------------------+-------------------------|");

            System.out.println(
                    "| Dr.ID | Pt.ID |  Dr.Name                      |    Patient Name           | appointment_date  | Dr. Contact number | Patient Contact number  |");

            System.out.println(
                    "|-------+-------+-------------------------------+---------------------------+-------------------+--------------------+-------------------------|");
            while (result.next()) {
                cnt = true;
                DoctorsId = result.getInt("DoctorId");
                PatientId = result.getInt("PatientId");
                Dr_name = result.getString("Doctor_Name");
                Patient_name = result.getString("Patient_Name");
                appointment_date = result.getString("Appointment_Date");
                PatientContactNumber = result.getString("PatientContact_number");
                Contact_number = result.getString("DoctorsContact_number");

                System.out.printf("| %-5s | %-5s | %-29s | %-25s | %-17s | %-18s | %-23s |\n", DoctorsId, PatientId,
                        Dr_name, Patient_name, appointment_date, Contact_number, PatientContactNumber);

            }
            System.out.println(
                    "|-------+-----------+---------------------------+---------------------------+-------------------+--------------------+-------------------------|");
            if (cnt) {
                System.out.println(" want to Attend the patients \n Enter Y to continue");
                char ch = sc.next().charAt(0);
                if (ch == 'y' || ch == 'Y') {
                    AddtendPatents(sc);
                } else {
                    Dashboard(sc);
                }

            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void AddtendPatents(Scanner sc) {
        try {
            System.out.println("Enter Patients Id ");
            PatientId = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter Appointment date in format yyyy-mm-dd ");
            appointment_date = sc.nextLine();

            String appointment = "SELECT *FROM Appointments WHERE PatientId=? AND DoctorId =? AND Appointment_Date =?";
            PreparedStatement pstmt = connection.prepareStatement(appointment);
            pstmt.setInt(1, PatientId);
            pstmt.setInt(2, DoctorsId);
            pstmt.setDate(3, java.sql.Date.valueOf(appointment_date));
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                PatientId = result.getInt("PatientId");
                Patient_name = result.getString("Patient_Name");
                PatientContactNumber = result.getString("PatientContact_number");
                System.out.println("|-----------+-----------------------+------------------+-----------------------|");
                System.out.println("| PatientId | Patient Name          | appointment date | Patient Contact Number|");
                System.out.println("|-----------+-----------------------+------------------+-----------------------|");
                System.out.printf("| %-9s | %-21s | %-16s | %-25s |\n", PatientId, Patient_name, appointment_date,
                        PatientContactNumber);
                System.out.println("|-----------+-----------------------+------------------+-----------------------|");
                createPatientsReport(sc);
            } else {
                try {
                    System.out.println("No patient's record found \n wanna try again ? enter Y/N");
                    char option = sc.next().charAt(0);
                    sc.nextLine();
                    if (option == 'y' || option == 'Y') {
                        sc.nextLine();
                        AddtendPatents(sc);
                    } else {
                        System.out.println("redirecting to dashbord ....");
                        Dashboard(sc);
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input ...sending you to Dashbord ");
                    sc.nextLine();
                    Dashboard(sc);
                }

            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input .. please enter correct patient id ");
            sc.nextLine();
            AddtendPatents(sc);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid format ..enter date in given format only");
            AddtendPatents(sc);
        } catch (SQLException e) {
            e.getStackTrace();
        }

    }

    void Logout() {
        System.out.println("Logged out Succusefully...");
        System.exit(0);
    }

    void createPatientsReport(Scanner sc) {
        System.out.println("Having discussions with patient's ...inquir below data with patient's only  ");
        System.out.println("Ask Patients age ");
        int age = sc.nextInt();
        System.out.println("patient's gender");
        String gender = sc.next();
        System.out.println("Patients blood type");
        String blood = sc.next();
        sc.nextLine();
        System.out.println("Patients Clinical History ?");
        String clinicalHistory = sc.nextLine();
        System.out.println("Examination Finding :");
        String Examination = sc.nextLine();
        System.out.println("Dignosis :");
        String Dignosis = sc.nextLine();
        System.out.println("Treatment");
        String Treatment = sc.nextLine();
        System.out.println("Follow Up Advise");
        String FolloUp = sc.nextLine();
        System.out.println("Enter treatmen fees ");
        int fees = sc.nextInt() + Appointment_charge;

        String PReport = "INSERT INTO PatientsReport(PatientId,DoctorId,Doctorsname,Patient_Name,Age,gender,Blood_Type,"
                +
                "Contact_Details,Clinical_History,Examination_Finding," +
                "Dignosis,Treatment,FollowUp_Advise ,Doctorfees,Appointment_Date)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement pstmt = connection.prepareStatement(PReport);
            pstmt.setInt(1, PatientId);
            pstmt.setInt(2, DoctorsId);
            pstmt.setString(3, Dr_name);
            pstmt.setString(4, Patient_name);
            pstmt.setInt(5, age);
            pstmt.setString(6, gender);
            pstmt.setString(7, blood);
            pstmt.setString(8, PatientContactNumber);
            pstmt.setString(9, clinicalHistory);
            pstmt.setString(10, Examination);
            pstmt.setString(11, Dignosis);
            pstmt.setString(12, Treatment);
            pstmt.setString(13, FolloUp);
            pstmt.setInt(14, fees);
            pstmt.setDate(15, java.sql.Date.valueOf(appointment_date));

            int result = pstmt.executeUpdate();
            if (result > 0) {

                System.out.println("Patient's report created sucussesfully....");
                exit(sc);
            } else
                System.out.println("Issue while entering data");

        } catch (SQLException e) {
            // System.out.println();
            e.printStackTrace();
        }

    }

    void exit(Scanner sc) {
        System.out.println("For main menu \nEnter Y to Continue or other key for exit");
        char appoint = sc.next().charAt(0);
        if (appoint == 'Y' || appoint == 'y') {
            Dashboard(sc);
        } else {
            System.out.println("Thanks For using ....");
            System.exit(2);
        }
    }

}
