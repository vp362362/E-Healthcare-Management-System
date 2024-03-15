import java.util.InputMismatchException;
import java.util.Scanner;
import java.security.spec.PSSParameterSpec;
import java.sql.*;

final class Patients {
    // private static final String
    // Url="jdbc:mysql://localhost:3306/healthcare_managmentsystem";
    // private static final String Username ="root";
    // private static final String Pswd="Space@2611";
    // Scanner sc = new Scanner(System.in);
    // Patient dataembers
    private int PatientId;
    private String name, Address;
    private int age;
    private String PatientContactNumber;
    private String password;
    // private int UserId;
    private String Password;

    // Doctors datamembers
    private int DoctorsId;
    private String Specialization;
    private String Dr_name;
    private String Contact_number;
    private String City;
    String appointment_date;
    String Date;

    Connection conn = Database.getInstance();

    void SignUp(Scanner sc) {
        sc.nextLine();
        System.out.println("Enter Customers name :");
        name = sc.nextLine();
        try {
            System.out.println("Age :");
            age = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer age.");
            sc.nextLine(); // Consume the invalid input to prevent an infinite loop
            SignUp(sc);
        }

        System.out.println("Address :");
        Address = sc.nextLine();
        System.out.println("Enter Phone number :");
        PatientContactNumber = sc.nextLine();
        // sc.nextLine();
        System.out.println("Create Password :");
        password = sc.nextLine();

        try {
            String SqlQuary = "INSERT INTO patient(Name, Age ,Address, Contact_Details ,pswd) VALUES ( ?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(SqlQuary);
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, Address);
            pstmt.setString(4, PatientContactNumber);
            pstmt.setString(5, password);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("Account created succssesfully ...");
                String createuser = "SELECT * FROM patient WHERE Contact_Details =? AND Name =? AND pswd =?";
                pstmt = conn.prepareStatement(createuser);
                pstmt.setString(1, PatientContactNumber);
                pstmt.setString(2, name);
                pstmt.setString(3, password);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int userid = rs.getInt("PatientId");
                    System.out.println("Your User id is : " + userid);
                }
            } else
                System.out.println("No changes ");
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // System.out.println("Account created succussefully ....");
        boolean lg = true;
        while (lg) {
            System.out.println("Want to continue login ??");
            System.out.println("1 :Login \n2 :Logout ");
            byte option = sc.nextByte();
            sc.nextLine();
            lg = false;
            switch (option) {
                case 1:
                    Login(sc);
                    break;
                case 2:
                    Logout();
                    break;
                default:
                    System.out.println("Invalid entry ..");
                    break;
            }
        }

    }

    void Login(Scanner sc) {

        int ch = 3;
        while (ch >= 1) {
            try {
                System.out.println("Enter User Id  ");
                PatientId = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter password ");
                Password = sc.nextLine();

                String SqlQuary = "SELECT * FROM patient WHERE PatientId =? AND pswd =?";

                PreparedStatement pstmt = conn.prepareStatement(SqlQuary);
                pstmt.setInt(1, PatientId);
                pstmt.setString(2, Password);
                ResultSet result = pstmt.executeQuery();

                if (result.next()) {
                    ch = -1;
                    name = result.getString("Name");
                    Address = result.getString("Address");
                    age = result.getInt("Age");
                    PatientContactNumber = result.getString("Contact_Details");
                    System.out.println("congratulations logged in successfully");

                    Dashboard(sc);
                } else {
                    System.out.println("Wrong User Id or passward ");
                    System.out.println("left attempt's :" + (ch - 1));
                }
                ch--;
                if (ch == 0) {
                    System.out.println("attempt limit exit try after some time ....");
                }
                result.close();
                pstmt.close();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for User Id.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
            } catch (SQLException e) {
                System.out.println("Exception aa gya bhai :");
                e.printStackTrace();
            }
        }

    }

    void Dashboard(Scanner sc) throws SQLException {

        System.out.println("DASHBORD------------------------------------------------------Welcome :" + name);
        System.out.println(
                "1 : View Profile                                                2 :View Doctor \n3 :Book Appointment" +
                        "                                                4 :View Appointments \n5 :viewReports\n6:Give Feedback                                                 7 :Pay Online \n8 : Logout ");
        try {
            byte option = sc.nextByte();

            switch (option) {
                case 1:
                    ViewProfile(sc);

                    break;
                case 2:
                    ViewDoctor(sc);

                    break;

                case 3:
                    BookAppointment(sc);

                    break;
                case 4:
                    ViewAppointments(sc);

                    break;
                case 5:
                    viewReports(sc);

                    break;
                case 6:
                    GiveFeedback(sc);

                    break;
                case 7:
                    PaymentPortal(sc);

                    break;
                case 8:
                    Logout();

                    break;

                default:
                    System.out.println("Invalid entry ");
                    break;
            }
            System.out.println(
                    "|-------------------------------------------------------------------------------------------|");

        } catch (InputMismatchException e) {
            System.out.println("invalid input");
            sc.nextLine();
            Dashboard(sc);
        }

    }

    void ViewProfile(Scanner sc) {

        try {
            String SqlQuary = "SELECT * FROM patient WHERE PatientId =? AND pswd =?";

            PreparedStatement pstmt = conn.prepareStatement(SqlQuary);
            pstmt.setInt(1, PatientId);
            pstmt.setString(2, Password);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {

                PatientId = result.getInt("PatientId");

                name = result.getString("Name");

                age = result.getInt("Age");

                PatientContactNumber = result.getString("Contact_Details");

                Address = result.getString("Address");

                System.out.println("|------Profie-------------------------------------|");
                System.out.printf("|Patient Id : %-10s  Patients name : %-5s \n", PatientId, name);
                System.out.printf("|Age : %-13s Contact number : %-5s \nAddress : %-5s\n\n", age, PatientContactNumber,
                        Address);
                System.out.println("Want to update your profile ...\n enter Y or N ");

                boolean vp = true;
                while (vp) {
                    try {

                        char ch = sc.next().charAt(0);
                        vp = false;
                        if (ch == 'y' || ch == 'Y') {
                            System.out.println("1 :Change Password \n2 :Address \n3 :Contact number \n4 : Edit Name");
                            byte option = sc.nextByte();
                            sc.nextLine();
                            switch (option) {
                                case 1:
                                    String pswd = "UPDATE patient SET pswd =? WHERE PatientId = ?";
                                    boolean condetion = true;
                                    while (condetion) {

                                        System.out.println("Enter your current password ");
                                        String currentpassward = sc.nextLine();

                                        if (currentpassward.equals(Password)) {
                                            condetion = false;

                                            int cnt2 = 1;
                                            while (cnt2 <= 3) {
                                                System.out.println("Enter your new password...");
                                                currentpassward = sc.nextLine();

                                                System.out.println("Confirm password");
                                                String newpassward = sc.nextLine();

                                                if (currentpassward.equals(newpassward)) {
                                                    cnt2 = 3;
                                                    pstmt = conn.prepareStatement(pswd);
                                                    pstmt.setString(1, newpassward);
                                                    pstmt.setInt(2, PatientId);

                                                    int rs = pstmt.executeUpdate();
                                                    if (rs > 0) {
                                                        System.out.println("Passward updated sucussesfully");
                                                        Dashboard(sc);
                                                    }

                                                } else if (cnt2 == 3) {
                                                    System.out.println("Attept limit exist ...");
                                                    Dashboard(sc);
                                                }

                                                else {
                                                    System.out.println("Wrong password try again " + cnt2);
                                                    cnt2++;
                                                }
                                            }

                                        } else
                                            System.out.println("Wrong passsword ....");

                                    }
                                    exit(sc);

                                    break;
                                case 2:
                                    String add = "UPDATE patient SET Address =? WHERE PatientId = ?";

                                    System.out.println("Enter your current or new Address ");
                                    Address = sc.nextLine();

                                    pstmt = conn.prepareStatement(add);
                                    pstmt.setString(1, Address);
                                    pstmt.setInt(2, PatientId);

                                    int rs = pstmt.executeUpdate();
                                    if (rs > 0) {
                                        System.out.println("Address updated sucussesfully");
                                        exit(sc);
                                    }

                                    break;
                                case 3:
                                    String contact = "UPDATE patient SET Contact_Details =? WHERE PatientId = ?";

                                    System.out.println("Enter your current Phone number to change ");
                                    PatientContactNumber = sc.nextLine();

                                    pstmt = conn.prepareStatement(contact);
                                    pstmt.setString(1, PatientContactNumber);
                                    pstmt.setInt(2, PatientId);

                                    rs = pstmt.executeUpdate();
                                    if (rs > 0) {
                                        System.out.println("Contact details updated sucussesfully");
                                        exit(sc);
                                    }

                                    break;
                                case 4:
                                    String changename = "UPDATE patient SET Name =? WHERE PatientId = ?";

                                    System.out.println("Enter your current password ");
                                    String currentpassward = sc.nextLine();
                                    condetion = true;
                                    while (condetion) {

                                        if (currentpassward.equals(Password)) {
                                            condetion = false;
                                            System.out.println("Enter your name ...");
                                            name = sc.nextLine();
                                            pstmt = conn.prepareStatement(changename);
                                            pstmt.setString(1, name);
                                            pstmt.setInt(2, PatientId);
                                            rs = pstmt.executeUpdate();
                                            if (rs > 0) {
                                                System.out.println("Name updated sucussesfully");
                                                exit(sc);
                                            }

                                        } else {
                                            System.out.println("Wrong passsword ....");
                                        }

                                    }
                                    break;
                                default:
                                    System.out.println("Invalid Entry");
                                    break;
                            }

                        } else {
                            Dashboard(sc);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input.");
                        sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                    }
                }
            }

            result.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Exception aa gya bhai :");
            e.printStackTrace();
        }
    }

    void ViewDoctor(Scanner sc) throws SQLException {
        try {
            // ----------------+------------

            String view_DR = "SELECT * FROM doctors";
            PreparedStatement pstmt = conn.prepareStatement(view_DR);

            ResultSet result = pstmt.executeQuery();
            System.out.println(
                    "|-----------+-----------------------------+-------------------+-------------+------------------------+--------------------+---------------------|");

            System.out.println(
                    "| Doctor Id |  Dr. Name                   |   Specialist      |    City     |     State              |  Contact number    | Appointment charges |");

            System.out.println(
                    "|-----------+-----------------------------+-------------------+-------------+------------------------+--------------------+---------------------|");
            boolean vd = false;
            while (result.next()) {
                vd = true;

                DoctorsId = result.getInt("DoctorId");
                Specialization = result.getString("Specialist");
                Dr_name = result.getString("Doctorsname");
                // Address = result.getString("Address");
                City = result.getString("City");
                String State = result.getString("State");
                String Contact_number = result.getString("Contact_number");
                int Appointment_charge = result.getInt("Appointment_charge");

                System.out.printf("| %-9s | %-27s | %-17s | %-11s | %-22s | %-18s | %-20s|\n", DoctorsId,
                        Dr_name,
                        Specialization, City, State, Contact_number, Appointment_charge);

            }

            if (vd == false) {
                System.out.println("Sorry no doctor's available this time ...");
            }
            System.out.println(
                    "|-----------+-----------------------------+-------------------+-------------+------------------------+--------------------+---------------------|");
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Want to book an appointment ?\n enter Y for yes N for Main manu");
        char appoint = sc.next().charAt(0);
        sc.nextLine();
        if (appoint == 'Y' || appoint == 'y') {
            BookAppointment(sc);
        } else {
            Dashboard(sc);
        }

    }

    void BookAppointment(Scanner sc) throws SQLException {
        int ch = 4;
        PreparedStatement pstmt;
        boolean record = false;
        while (ch >= 1) {

            try {
                System.out.println("Enter Doctors Id ");
                DoctorsId = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("invalid input ...please enter valid Doctor id ..");
                sc.nextLine();
                BookAppointment(sc);
            }

            // boolean attempt = true;
            String view_DR = "SELECT * FROM doctors WHERE DoctorId =?";
            pstmt = conn.prepareStatement(view_DR);

            pstmt.setInt(1, DoctorsId);
            // pstmt.setString(2, Dr_name);
            // pstmt.setString(3, City);
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                ch = -1; // while condition update here
                record = true;
                DoctorsId = result.getInt("DoctorId");
                Specialization = result.getString("Specialist");
                Dr_name = result.getString("Doctorsname");
                // Address = result.getString("Address");
                String City = result.getString("City");
                String State = result.getString("State");
                Contact_number = result.getString("Contact_number");
                int Appointment_charge = result.getInt("Appointment_charge");

                System.out.println(
                        "|-----------+-----------------------------+-------------------+-------------+------------------------+--------------------|---------------------|");

                System.out.println(
                        "| Doctor Id |  Dr. Name                   |   Specialist      |    City     |     State              |  Contact number    | Appointment charges |");
                System.out.printf("| %-9s | %-26s | %-18s | %-12s | %-15s | %-19s | %-10s\n", DoctorsId,
                        Dr_name,
                        Specialization, City, State, Contact_number, Appointment_charge);

                System.out.println(
                        "|-----------+-----------------------------+-------------------+-------------+------------------------+--------------------|---------------------|");

            } else if (ch == 1) {
                System.out.println("attempt limit exit try after some time ....");
                System.exit(5);
            } else {
                ch--;
                System.out.println("No record found ...");
                System.out.println("Wanna try again ?....left attemps : " + ch);

                char at = sc.next().charAt(0);
                sc.nextLine();

                if (at == 'y' || at == 'Y') {
                    continue;
                } else {
                    System.out.println("We are redirecting you on home page....");
                    Dashboard(sc);
                }

            }

            // ch--;

            if (ch == 0) {
                System.out.println("attempt limit exit try after some time ....");
                System.exit(5);
            }

        }

        if (record) {
            boolean rd = true;
            while (rd) {
                try {
                    System.out.println("For Booking your slot enter Date in format YYYY-MM-DD ");

                    // java.lang.IllegalArgumentException
                    Date = sc.nextLine();

                    // DATE - format YYYY-MM-DD
                    String BookSlot = "INSERT INTO Appointments(DoctorId,PatientId,Doctor_Name,Patient_Name,Appointment_Date,DoctorsContact_number,PatientContact_number) VALUES ( ?,?,?,?,?,?,?)";
                    pstmt = conn.prepareStatement(BookSlot);

                    pstmt.setInt(1, DoctorsId);
                    pstmt.setInt(2, PatientId);
                    pstmt.setString(3, Dr_name);
                    pstmt.setString(4, name);

                    pstmt.setDate(5, java.sql.Date.valueOf(Date));

                    pstmt.setString(6, Contact_number);
                    pstmt.setString(7, PatientContactNumber);

                    int rs = pstmt.executeUpdate();

                    if (rs > 0) {
                        System.out.println("Appointment booked ");

                        System.out.println("want to view appoiintment \nEnter Y for YES or n for main menu");
                        char appoint = sc.next().charAt(0);
                        if (appoint == 'Y' || appoint == 'y') {
                            ViewAppointments(sc);
                        } else {
                            Dashboard(sc);
                        }

                    } else {
                        System.out.println("Opps Something went wrong ");
                    }

                    rd = false;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid format ..enter date in given format only");
                }
            }

        }

    }

    void viewReports(Scanner sc) throws SQLException {
        System.out.println("View  Your Doctor's report here ");
        System.out.println("Please enter your doctor Id and Appointment Date");
        DoctorsId = sc.nextInt();
        sc.nextLine();
        System.out.println("Appointment date in Formate of yyyy-mm-dd");
        appointment_date = sc.nextLine();
        String ps = "SELECT * FROM PatientsReport WHERE DoctorId =? AND Appointment_Date=?";
        try {

            PreparedStatement pstmt = conn.prepareStatement(ps);
            pstmt.setInt(1, DoctorsId);
            pstmt.setDate(2, java.sql.Date.valueOf(appointment_date));
            ResultSet result = pstmt.executeQuery();

            System.out.println();
            if (result.next()) {
                DoctorsId = result.getInt("DoctorId");
                PatientId = result.getInt("PatientId");
                int Age = result.getInt("Age");
                Dr_name = result.getString("Doctorsname");
                name = result.getString("Patient_Name");
                String Attended_date = result.getString("Date_Time");
                PatientContactNumber = result.getString("Contact_Details");
                String gender = result.getString("gender");
                String clinicalhistor = result.getString("Clinical_History");
                String Examination = result.getString("Examination_Finding");
                String Dignosis = result.getString("Dignosis");
                String Treatment = result.getString("Treatment");
                String FolloUp = result.getString("FollowUp_Advise");
                String Blood_Type = result.getString("Blood_Type");
                int Doctorfees = result.getInt("Doctorfees");
                String PaymentStatus = result.getString("PaymentStatus");

                if (PaymentStatus.equals("Pendding")) {
                    System.out.printf("Doctor Id : %-10s Doctor Name : %-5s\n", DoctorsId, Dr_name);
                    System.out.printf("Date : %-10s Doctor Total bill : %-5s\n", appointment_date, Doctorfees);
                    System.out.println("Payment Status :" + PaymentStatus);

                    System.out.println("|PAY NOW|: y for Yes");
                    String pay = "UPDATE PatientsReport SET PaymentStatus ='DONE' WHERE DoctorId = ? AND Appointment_Date =?";
                    char appoint = sc.next().charAt(0);
                    if (appoint == 'Y' || appoint == 'y') {

                        try {

                            pstmt = conn.prepareStatement(pay);

                            pstmt.setInt(1, DoctorsId);

                            pstmt.setDate(2, java.sql.Date.valueOf(appointment_date));

                            int rs = pstmt.executeUpdate();

                            if (rs > 0) {
                                System.out.println("Payment Done Sucussesfully ....");
                                System.out.println(
                                        "Want to redirected to View Patients Report ... enter Y  anything else  to Main manu");
                                char p = sc.next().charAt(0);
                                if (p == 'Y' || p == 'y') {
                                    viewReports(sc);
                                } else {
                                    Dashboard(sc);
                                }
                            } else
                                System.out.println("issue while paying .....");

                        } catch (SQLException e) {
                            System.out.println("error :" + e.getMessage());
                        }
                    } else {
                        System.out.println(
                                "Redirecting to dasboard .....");
                        Dashboard(sc);

                    }

                } else {

                    // System.out.printf("| %-10s | %-12s | %-10s | %-10s | %-15s | %-5s | %-15s
                    // |\n", DoctorsId, PatientId,
                    // Dr_name, name, appointment_date, Contact_number, PatientContactNumber);
                    System.out.println(
                            "|--------------------------*--------------------+----------------------*------------------------------|");

                    System.out.println(
                            "|---------------------------------PATIENTS REPORTS---------------------------------------------|");

                    System.out.printf("Patient's Name : %-40s Patients Id  : %-5s \nGender : %-40s Date :%-2s\n", name,
                            PatientId, gender, Attended_date);
                    System.out.printf(
                            "Blood_Type : %-30s Doctor's Name : %-2s \nAge : %-30s Patient's Contact number : %-2s\n",
                            Blood_Type, Dr_name, Age, PatientContactNumber);
                    System.out.println("Clinical History :" + clinicalhistor);
                    System.out.println("\nExamination Finding :" + Examination);
                    System.out.println("\nDignosis :" + Dignosis);
                    System.out.println("\nTreatment :" + Treatment);
                    System.out.println("FollowUp_Advise :" + FolloUp);

                    System.out.println(
                            "\n\n|--------------------------*--------------------+----------------------*------------------------------|");
                }

            } else {
                System.out.println("No record found ...please contact your Doctor's to update report ...");
                System.out.println(
                        "Wanna try again with correct credentials ?? enter Y to contine other key to main menu ");
                char vr = sc.next().charAt(0);
                if (vr == 'y' || vr == 'Y') {
                    viewReports(sc);
                } else {
                    Dashboard(sc);
                }
            }
            pstmt.close();
        } catch (IllegalArgumentException e) {
            System.out.println("Enter date in correct format ..");
            viewReports(sc);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        exit(sc);
    }

    void exit(Scanner sc) throws SQLException {
        System.out.println("For main manu \nEnter Y to Continue or other key for exit");
        char appoint = sc.next().charAt(0);
        if (appoint == 'Y' || appoint == 'y') {
            Dashboard(sc);
        } else {
            System.out.println("Thanks For using ....");
            System.exit(2);
        }
    }

    void ViewAppointments(Scanner sc) throws SQLException {

        System.out.println("Your appointments .....");
        String booking = "SELECT *FROM Appointments WHERE PatientId=?";
        try {

            PreparedStatement pstmt = conn.prepareStatement(booking);
            pstmt.setInt(1, PatientId);
            // pstmt.setInt(2, DoctorsId);
            ResultSet result = pstmt.executeQuery();
            System.out.println(
                    "|-----------+-----------+-----------------------+-----------------------+------------------+--------------------+------------------------|");

            System.out.println(
                    "| Doctor Id | PatientId | Dr.Name               | Patient Name          | Appointment_date | Dr. Contact number | Patient Contact number |");
            System.out.println(
                    "|-----------+-----------+-----------------------+-----------------------+------------------+--------------------+------------------------|");
            while (result.next()) {
                DoctorsId = result.getInt("DoctorId");
                PatientId = result.getInt("PatientId");
                Dr_name = result.getString("Doctor_Name");
                name = result.getString("Patient_Name");
                String appointment_date = result.getString("Appointment_Date");
                PatientContactNumber = result.getString("PatientContact_number");
                Contact_number = result.getString("DoctorsContact_number");

                System.out.printf("| %-9s | %-9s | %-21s | %-21s | %-16s | %-18s | %-22s |\n", DoctorsId, PatientId,
                        Dr_name, name, appointment_date, Contact_number, PatientContactNumber);

            }
            System.out.println(
                    "|-----------+-----------+-----------------------+-----------------------+------------------+--------------------+------------------------|");
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        exit(sc);
    }

    void GiveFeedback(Scanner sc) throws SQLException {

        try {
            System.out.println("Help us to improve ...please give your valuable feedback");

            System.out.println("Enter Your Doctor Id :");
            DoctorsId = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter Your appointment date with doctor in format yyyy-mm-dd");
            appointment_date = sc.nextLine();
            System.out.println("Share Your exprience with our Doctor :");
            String Feedback = sc.nextLine();

            String fb = "UPDATE PatientsReport SET Patient_Feedback =? WHERE PatientId =? AND DoctorId = ? AND Appointment_Date =? ";
            PreparedStatement pstmt = conn.prepareStatement(fb);
            pstmt.setString(1, Feedback);
            pstmt.setInt(2, PatientId);
            pstmt.setInt(3, DoctorsId);
            pstmt.setDate(4, java.sql.Date.valueOf(appointment_date));

            int rs = pstmt.executeUpdate();
            if (rs > 0) {
                System.out.println("Feedback submited successfully");
                exit(sc);
            } else {
                System.out.println("Sorry No record found with your doctor ...");
                System.out.println("wanna try again with correct details ?? \nenter Y/N for switch to main menu");
                char f = sc.next().charAt(0);
                if (f == 'y' || f == 'Y') {
                    GiveFeedback(sc);
                } else {
                    Dashboard(sc);
                }

            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid Doctor id ....Please enter correct doctor id ");
            sc.nextLine();
            GiveFeedback(sc);

        }

    }

    void PaymentPortal(Scanner sc) throws SQLException {

        try {
            System.out.println("|====================PAYMENT PORTAL====================|");
            System.out.println("|1 : View Bill                 2 :Check Payment Status |");
            System.out.println("|3 : PAY BILL");
            int pp = sc.nextInt();
            sc.nextLine();

            switch (pp) {
                case 1:
                    System.out.println("|====================View Bill=========================|");
                    PayOnline(sc);
                    break;
                case 2:
                    System.out.println("|====================Payment Status====================|");
                    PayOnline(sc);
                    break;
                case 3:
                    System.out.println("|====================PAY BILL==========================|");
                    PayOnline(sc);
                    break;

                default:
                    System.out.println("Invalid entry");
                    System.out.println("Wanna try again ?? \n enter Y / n");
                    char t = sc.next().charAt(0);
                    if (t == 'Y' || t == 'y') {
                        PaymentPortal(sc);
                    } else {
                        Dashboard(sc);
                    }

                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input .. please select given options ");
            sc.nextLine();
            PaymentPortal(sc);
        }

    }

    void PayOnline(Scanner sc) throws SQLException {

        System.out.println("Please enter your doctor Id");
        try {
            DoctorsId = sc.nextInt();
            sc.nextLine();

        } catch (InputMismatchException e) {
            System.out.println("Invaid input ...enter correct Doctor id ");
            sc.nextLine();
            PayOnline(sc);
        }
        boolean po = true;
        while (po) {

            System.out.println("Appointment date in Formate of yyyy-mm-dd");
            appointment_date = sc.nextLine();
            String ps = "SELECT * FROM 
            PatientsReport WHERE PatientId =? AND DoctorId =? AND Appointment_Date=?";
            try {

                PreparedStatement pstmt = conn.prepareStatement(ps);
                pstmt.setInt(1, PatientId);
                pstmt.setInt(2, DoctorsId);
                pstmt.setDate(3, java.sql.Date.valueOf(appointment_date));
                ResultSet result = pstmt.executeQuery();

                po = false;
                System.out.println();
                if (result.next()) {

                    DoctorsId = result.getInt("DoctorId");
                    PatientId = result.getInt("PatientId");
                    int Age = result.getInt("Age");
                    Dr_name = result.getString("Doctorsname");
                    name = result.getString("Patient_Name");
                    appointment_date = result.getString("Date_Time");
                    PatientContactNumber = result.getString("Contact_Details");

                    int Doctorfees = result.getInt("Doctorfees");
                    String PaymentStatus = result.getString("PaymentStatus");

                    if (PaymentStatus.equals("Pendding")) {
                        System.out.println(
                                "|--------------------------*--------------------+----------------------*------------------------------|");
                        System.out.printf("Doctor Id : %-10s Doctor Name : %-5s\n", DoctorsId, Dr_name);
                        System.out.printf("Date : %-10s Doctor Total bill : %-5s\n", appointment_date, Doctorfees);
                        System.out.println("Payment Status :" + PaymentStatus);
                        char appoint = ' ';
                        boolean condition = true;
                        while (condition) {
                            try {
                                System.out.println("|PAY NOW|: y for Yes");
                                appoint = sc.next().charAt(0);
                                sc.nextLine();
                                condition = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input please enter correct input ..");
                                sc.nextLine();
                            }
                        }
                        if (appoint == 'Y' || appoint == 'y') {
                            String pay = "UPDATE PatientsReport SET Doctorfees =?, PaymentStatus =? WHERE DoctorId = ? AND Appointment_Date =?";

                            try {
                                pstmt = conn.prepareStatement(pay);
                                pstmt.setInt(1, Doctorfees);
                                pstmt.setString(2, "Done");
                                pstmt.setInt(3, DoctorsId);
                                pstmt.setDate(4, java.sql.Date.valueOf(appointment_date));

                                int rs = pstmt.executeUpdate();
                                if (rs > 0) {
                                    System.out.println("Payment Done Sucussesfully ....");
                                    System.out.println(
                                            "Want to redirected to View Patients Report ... enter Y  anything else  to Main manu");
                                    char p = sc.next().charAt(0);
                                    if (p == 'Y' || p == 'y') {
                                        viewReports(sc);
                                    } else {
                                        Dashboard(sc);
                                    }
                                } else
                                    System.out.println("issue while paying .....");

                            } catch (SQLException e) {
                                System.out.println("error :" + e.getMessage());
                            }
                        } else {
                            System.out.println(
                                    "Redirecting to dasboard .....");
                            Dashboard(sc);

                        }
                    } else {
                        System.out.println("Payment Is Already Complete. You can now see Patient's Reports");
                        System.out.println("\nEnter Y for YES  patient's report or anything else for main menu");
                        char appoint = sc.next().charAt(0);
                        if (appoint == 'Y' || appoint == 'y') {
                            viewReports(sc);
                        } else {
                            Dashboard(sc);
                        }
                    }
                } else {
                    System.out.println("No record Found this time ....contact your doctor's ");
                    System.out.println("Wanna try again with correct entries ?? enter Y anything else for main menu");
                    char c = sc.next().charAt(0);
                    if (c == 'y' || c == 'Y') {
                        PayOnline(sc);
                    } else {
                        Dashboard(sc);
                    }
                }
                pstmt.close();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid format ..enter date in given format only");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void Logout() {

        System.out.println("thanks! see you soon ...");
        System.exit(0);
        // m1.Display();
    }
}
