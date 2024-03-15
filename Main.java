// import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    /**E-HealthCare-Management-System is a console based application which is built using java.
     * This application helps in management of Patients, doctors, admin in a easy and comfortable way.
     * using this Application patients can quickly Sign up, Login, view his/her profile, view doctors, book Appointment, 
     * view Report, choose doctor, view Appointments, give feedback, pay online and logout. Admin can add Doctors,view 
     * patients list, view Doctors list,
     * remove doctors, see feedback given by patients,view reports,logout.Doctor can login, view profile,
     *  viewAppointments, Attend Patients and logout. */
    Patients p =new Patients();
    Admin admin =new Admin();
    Doctor docttor=new Doctor();
  
    void Display( Scanner sc) throws Exception{
   

        System.out.println("|-------------------E -Healthcare Managment System ----------------------------|");
    System.out.println("|-----------------HOME PAGE--------------------|");
    System.out.println("1 : PATIENT                   2 : DOCTOR \n3 : ADMIN");
System.out.println("Choose your action ");
byte option =sc.nextByte();
switch (option) {
    case 1: 
    System.out.println("1 :Login \n2 :SignUp ");
    option =sc.nextByte();
    switch (option) {
        case 1:  //Login
        p.Login( sc);
          break;
     case 2:// SignUP
     p.SignUp( sc);
    // p.SignUp();
            break;
        default: System.out.println("Invalid entry ..");
            break;
    }
        
        break;
case 2: System.out.println("Login");
docttor.Login( sc);
        break;
        case 3: //Admin use only ...
        admin.Login( sc);
        
        break;
    default: System.out.println("invalid entry");
    // default: System.out.println("invalid entry");
        break;

}
     }
    public static void main(String[] args) throws Exception {
        Scanner sc =new Scanner(System.in);
        Main m=new Main();
        //managment of Patients , doctors , admin 
m.Display( sc);



    }
}
