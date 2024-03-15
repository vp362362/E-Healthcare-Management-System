import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Admin {

    private String AdminName = "Vishal Patel";
    private long adminUserid = 362362;
    private String adminPassword = "vishal123";
    private String Email;
    // Scanner sc = new Scanner(System.in);

    private String Dr_name;
    private String Specialization;
    private String Address;
    private String City;
    private String State;
    private String Contact_number;
    private String PassWord;
    private int DoctorsId;
    int Appointment_charge;
    Connection conn = Database.getInstance();

    void Login(Scanner sc) {
        int l = 4;
        while (l >= 1) {

            try {
                System.out.println("Administration uses only ");
                System.out.println("Enter User Id ");
                long Userid = sc.nextLong();
                sc.nextLine();
                System.out.println("Enter Password");
                String temppassword = sc.nextLine();

                // String Lg = "select * from Administration where AdminId =? AND Password =?";
                // PreparedStatement pstmt = conn.prepareStatement(Lg);
                // pstmt.setLong(1, Userid);
                // pstmt.setString(2, temppassword);

                // ResultSet rs = pstmt.executeQuery();
                // if (rs.next()) {
                if (Userid == adminUserid && temppassword.equals(adminPassword)) {
                    // l = -1; // break while loop
                    // AdminName = rs.getString("Admin_Name");
                    // Email = rs.getString("EmailId");

                    System.out.println("Logged in successful");
                    Dashboard(sc);
                } else if (l == 1) {
                    System.out.println("Attempt limit exist!!  try after some time");
                    System.exit(0);
                } else {
                    l--;
                    System.out.println("Wrong user id or password ....attempt left : " + l);
                    System.out.println("wanna try again ? enter y to continue other key to exit ");
                    char c = sc.next().charAt(0);
                    if (c == 'y' || c == 'Y') {
                        continue;
                    } else {
                        System.out.println("Thank you for choosing us ..see you soon ");
                        System.exit(3);
                    }
                }

            } catch (InputMismatchException e) {
                System.out.println("invalid input ...please enter valid Admin Id ");
                sc.nextLine();
                Login(sc);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    void Dashboard(Scanner sc) {
        try {

            System.out.println("||==============Admin Dashboard===============||");
            System.out.println("                          Welcome  : " + AdminName);

            System.out.println(
                    "1 :Add Doctor           2 :View Pateints List \n3 :View Doctors List            \n4 :Remove Doctors "
                            +
                            "5 :Pateints Feedback            \n6 : View Reports            7 :Logout");

            byte options = sc.nextByte();
            sc.nextLine();

            switch (options) {
                case 1:
                    AddDoctors(sc);

                    break;
                case 2:
                    viewPateintslist(sc);

                    break;
                case 3:
                    ViewDoctorsList(sc);

                    break;
                case 4:
                    RemoveDoctors(sc);

                    break;
                case 5:
                    PateintsFeedback(sc);

                    break;
                case 6:
                    viewReports(sc);

                    break;
                case 7:
                    Logout();

                    break;

                default:
                    System.out.println("Incorrect inputs");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input enter correct input i.e : 1,2");
            sc.nextLine();
            Dashboard(sc);
        }

    }

    void exit(Scanner sc) {
        try {
            System.out.println("Switch to Main menu enter y or other for exit ");
            char ch = sc.next().charAt(0);
            if (ch == 'y' || ch == 'Y') {
                Dashboard(sc);
            } else {
                System.out.println("Thank's for using us ...");
                System.exit(0);
            }

        } catch (InputMismatchException e) {
            System.out.println("invalid input ...please enter valid input  ");
            sc.nextLine();
            exit(sc);

        }
    }

    void AddDoctors(Scanner sc) {
        System.out.println("Enter Doctor name :");
        Dr_name = sc.nextLine();
        System.out.println("Enter Dr. Specialization");
        Specialization = sc.nextLine();
        // System.out.println("Enter complete address Address :");
        // Address = sc.nextLine();
        System.out.println("enter City ");
        City = sc.nextLine();
        System.out.println("State");
        State = sc.nextLine();
        System.out.println("Enter Contact number :");
        Contact_number = sc.nextLine();

        boolean ky = true;
        while (ky) {
            try {
                System.out.println("Doctor appointment charges ");
                Appointment_charge = sc.nextInt();
                sc.nextLine();
                ky = false;
            } catch (InputMismatchException e) {
                System.out.println("enter valid amount i.e : 500,1000 only");
                sc.nextLine();
            }
        }

        System.out.println("Create Password:");
        PassWord = sc.nextLine();

        try {
            String SqlQuary = "INSERT INTO doctors(Doctorsname, Specialist ,City,State,Contact_number ,Appointment_charge,DPassword) VALUES ( ?,?,?,?,?,?,?)";
            // Address,

            PreparedStatement pstmt = conn.prepareStatement(SqlQuary);
            pstmt.setString(1, Dr_name);
            pstmt.setString(2, Specialization);
            // pstmt.setString(3, Address);
            pstmt.setString(3, City);
            pstmt.setString(4, State);
            pstmt.setString(5, Contact_number);
            pstmt.setInt(6, Appointment_charge);
            pstmt.setString(7, PassWord);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("Doctor added succussesfully...");

                String createuser = "SELECT * FROM doctors WHERE Doctorsname =? AND DPassword =? AND Contact_number =? AND Specialist =?";
                pstmt = conn.prepareStatement(createuser);
                pstmt.setString(1, Dr_name);
                pstmt.setString(2, PassWord);
                pstmt.setString(3, Contact_number);
                pstmt.setString(4, Specialization);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int userid = rs.getInt("DoctorId");
                    System.out.println("Doctor's User Id  : " + userid);
                    System.out.println("Password        : " + PassWord);
                    System.out.println("Share this credential with doctor ..");

                    System.out.println(
                            "Want to add more doctor's ??\nenter y for continue adding other key for dashbord ");
                    char key = sc.next().charAt(0);
                    sc.nextLine();
                    if (key == 'y' || key == 'Y') {
                        AddDoctors(sc);
                    } else {
                        Dashboard(sc);
                    }
                }

            } else
                System.out.println("No changes ");
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        exit(sc);

    }

    void viewPateintslist(Scanner sc) {
        try {
            boolean check = false;
            String Viewpatients = "SELECT * FROM patient";
            PreparedStatement pstmt = conn.prepareStatement(Viewpatients);

            ResultSet result = pstmt.executeQuery();
            System.out.println(
                    "|------------+---------------------+------+--------------+--------------------+----------------|");

            System.out.println(
                    "|Patients Id | Patients Name       | Age  | Address      | Contact number     | Password       |");
            System.out.println(
                    "|------------+---------------------+------+--------------+--------------------+----------------|");
            while (result.next()) {
                check = true;
                int PatientsId = result.getInt("PatientId");
                Dr_name = result.getString("Name");
                int age = result.getInt("Age");
                Address = result.getString("Address");
                Contact_number = result.getString("Contact_Details");
                PassWord = result.getString("pswd");

                System.out.printf("| %-10s | %-19s | %-4s | %-12s | %-18s | %-14s |\n", PatientsId, Dr_name, age,
                        Address,
                        Contact_number, PassWord);

            }

            System.out.println(
                    "|------------+---------------------+------+--------------+--------------------+----------------|");

            if (check == false) {
                System.out.println();
                System.out.println("No record Found here ..");
                System.out.println("Update your record first ...redirecting you to dashborad .....");
                Dashboard(sc);

            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        exit(sc);
    }

    void ViewDoctorsList(Scanner sc) {
        try {
            boolean check = false;
            String view_DR = "SELECT * FROM doctors";
            PreparedStatement pstmt = conn.prepareStatement(view_DR);

            ResultSet result = pstmt.executeQuery();
            System.out.println(
                    "|-----------+-----------------------------+--------------------+---------------+-------------------|-------------------|-------------|");

            System.out.println(
                    "| Doctor Id |  Dr. Name                   | Specialist         |    City       | State             | Contact number    | Password    |");

            System.out.println(
                    "|-----------+-----------------------------+--------------------+---------------+-------------------|-------------------|-------------|");

            while (result.next()) {
                check = true;
                DoctorsId = result.getInt("DoctorId");
                Specialization = result.getString("Specialist");
                Dr_name = result.getString("Doctorsname");
                // Address = result.getString("Address");
                City = result.getString("City");
                State = result.getString("State");
                Contact_number = result.getString("Contact_number");
                PassWord = result.getString("DPassword");

                System.out.printf("| %-9s | %-27s | %-18s | %-13s | %-17s | %-17s | %-12s|\n", DoctorsId, Dr_name,
                        Specialization, City, State, Contact_number, PassWord);

            }

            System.out.println(
                    "|-----------+-----------------------------+--------------------+---------------+-------------------|-------------------|-------------|");

            if (check == false) {
                System.out.println();
                System.out.println("No record Found here ..");
                System.out.println("Update your record first ...redirecting you to dashborad .....");
                Dashboard(sc);

            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        exit(sc);
    }

    void RemoveDoctors(Scanner sc) {

        System.out.println("Delete Doctor's data here ....");
        try {
            System.out.println("Enter Doctor User id :");
            DoctorsId = sc.nextInt();
            sc.nextLine();
            // System.out.println("enter Password:");
            // PassWord = sc.nextLine();
            String view_DR = "SELECT * FROM doctors WHERE DoctorId =?";
            PreparedStatement pstmt = conn.prepareStatement(view_DR);
            pstmt.setInt(1, DoctorsId);

            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                DoctorsId = result.getInt("DoctorId");
                Specialization = result.getString("Specialist");
                Dr_name = result.getString("Doctorsname");
                // Address = result.getString("Address");
                City = result.getString("City");
                State = result.getString("State");
                Contact_number = result.getString("Contact_number");
                PassWord = result.getString("DPassword");

                System.out.println(
                        "|-----------+-----------------------------+--------------------+---------------+-------------------|-------------------|-------------|");

                System.out.println(
                        "| Doctor Id |  Dr. Name                   | Specialist         |    City       | State             | Contact number    | Password    |");

                System.out.println(
                        "|-----------+-----------------------------+--------------------+---------------+-------------------|-------------------|-------------|");
                System.out.printf("| %-9s | %-27s | %-18s | %-13s | %-17s | %-17s | %-12s|\n", DoctorsId, Dr_name,
                        Specialization, City, State, Contact_number, PassWord);

                System.out.println(
                        "|-----------+-----------------------------+--------------------+---------------+-------------------|-------------------|-------------|");

                System.out.println(
                        "Are you sure you want to delete doctor's data ??\n You won't be able to undo the action ... enter Y to continue ..\nOther key to main menu");
                char delet = sc.next().charAt(0);
                sc.nextLine();
                if (delet == 'Y' || delet == 'y') {
                    try {
                        String remmovedoctor = "DELETE FROM doctors WHERE DoctorId =?;";
                        // String remmovedoctor ="SELECT * FROM doctors WHERE DoctorId =? AND
                        // Password=?";
                        pstmt = conn.prepareStatement(remmovedoctor);
                        pstmt.setInt(1, DoctorsId);
                        // pstmt.setString(2, PassWord);
                        int resul = pstmt.executeUpdate();
                        if (resul > 0) {
                            System.out.println("Doctor removed succussesfully...");
                            exit(sc);

                        } else
                            System.out.println("wrong user id or password ");
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    Dashboard(sc);
                }

            } else {
                System.out.println("No record Found here ...\nWanna try again ?? enter y /n");
                char delet = sc.next().charAt(0);
                sc.nextLine();
                if (delet == 'Y' || delet == 'y') {
                    RemoveDoctors(sc);
                } else {
                    Dashboard(sc);
                }

            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input enter correct input ..");
            sc.nextLine();
            RemoveDoctors(sc);
        } catch (SQLException e) {
            System.out.println("");
        }

    }

    void PateintsFeedback(Scanner sc) {
        System.out.println("To checks Doctor's feedback given by patient's ");
        try {
            System.out.println("Enter Doctor's Id :");
            DoctorsId = sc.nextInt();
            sc.nextLine();
            String FB = "SELECT * FROM PatientsReport WHERE DoctorId =? ";

            PreparedStatement pstmt = conn.prepareStatement(FB);
            pstmt.setInt(1, DoctorsId);
            // pstmt.setInt(2, DoctorsId);
            ResultSet result = pstmt.executeQuery();
            boolean check = false;
            System.out.println(
                    "|-----------+--------------------------+--------------------------+------------+---------------------------------|");
            System.out.println(
                    "| Doctor Id | Doctor's Name            | Patient's Name           |   Date     |  Patient's Feedback             |");

            System.out.println(
                    "|-----------+--------------------------+--------------------------+------------+---------------------------------|");

            while (result.next()) {
                check = true;
                Dr_name = result.getString("Doctorsname");
                String Patient_Name = result.getString("Patient_Name");
                String date = result.getString("Appointment_Date");
                String Fb = result.getString("Patient_Feedback");

                System.out.printf("| %-9s | %-24s | %-24s | %-11s| %-32s|\n", DoctorsId, Dr_name, Patient_Name, date,
                        Fb);

            }

            System.out.println(
                    "|-----------+--------------------------+--------------------------+------------+---------------------------------|");

            if (check == false) {
                System.out.println();
                System.out.println("No record Found here ...\nWanna try again ?? enter y /n");
                char delet = sc.next().charAt(0);
                sc.nextLine();
                if (delet == 'Y' || delet == 'y') {
                    RemoveDoctors(sc);
                } else {
                    Dashboard(sc);
                }

            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input ...try again ");
            sc.nextLine();
            PateintsFeedback(sc);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        exit(sc);
    }

    void viewReports(Scanner sc) {
        System.out.println("Patient's Reports record's");
        String ps = "SELECT * FROM PatientsReport";
        try {
            boolean check = false; // for record updation
            PreparedStatement pstmt = conn.prepareStatement(ps);
            ResultSet result = pstmt.executeQuery();
            System.out.println(
                    "|-------+--------+-------------------------------+-------------------------------+---------------------+-------------------+--------+-----+------------------------------------+--------------+-------------------|");

            System.out.println(
                    "| Dr.Id | Pt. Id | Dr.name                       | Patient's Name                | Attended Date       | Patient's Contact | Gender | Age | Treatment                          |Payment Status| Patient's Feedback|");

            System.out.println(
                    "|-------+--------+-------------------------------+-------------------------------+---------------------+-------------------+--------+-----+------------------------------------+--------------+-------------------|");
            System.out.println();
            while (result.next()) {
                check = true;
                DoctorsId = result.getInt("DoctorId");
                int PatientId = result.getInt("PatientId");
                int Age = result.getInt("Age");
                Dr_name = result.getString("Doctorsname");
                String name = result.getString("Patient_Name");
                String Attended_date = result.getString("Date_Time");
                String PatientContactNumber = result.getString("Contact_Details");
                String gender = result.getString("gender");
                // String clinicalhistor = result.getString("Clinical_History");
                // String Examination = result.getString("Examination_Finding");
                // String Dignosis = result.getString("Dignosis");
                String Treatment = result.getString("Treatment");
                // String FolloUp = result.getString("FollowUp_Advise");
                // String Blood_Type = result.getString("Blood_Type");
                // int Doctorfees = result.getInt("Doctorfees");
                String PaymentStatus = result.getString("PaymentStatus");
                String pfb = result.getString("Patient_Feedback");

                System.out.printf(
                        "| %-5s | %-6s | %-29s | %-29s | %-19s | %-17s | %-6s | %-3s | %-34s | %-12s | %-21s |\n",
                        DoctorsId, PatientId, Dr_name, name, Attended_date, PatientContactNumber, gender, Age,
                        Treatment, PaymentStatus, pfb);

            }

            System.out.println(
                    "|-------+--------+-------------------------------+-------------------------------+---------------------+-------------------+--------+-----+------------------------------------+--------------+-------------------|");

            if (check == false) {

                System.out.println("No record Found here ");
                System.out.println("Update your record first! redirecting you to Dashbord....");
                Dashboard(sc);

            }

            exit(sc);

        } catch (InputMismatchException e) {
            Dashboard(sc);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    void Logout() {
        System.out.println("See you letter");
        System.exit(0);
    }

}
