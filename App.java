import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class App {

    // private static final String Url = "jdbc:mysql://localhost:3306/mydb";
    // private static final String Username = "root";
    // private static final String Pswd = "Space@2611";

   public static void main(String[] args)throws SQLException {
    
    // Database t=Database.getobj();
    Connection con =Database.getInstance();
  
     System.out.println("main 1 ....");
// t.input();
System.out.println("main end program ");
      




        // jdbc:mysql://localhost:3306/?user=root
//   Connection conn = DriverManager.getConnection(Url, Username, Pswd);
//   Statement stmt = conn.createStatement();

// switch (option) {
//     case 1:
        
//         break;

//     default:
//         break;
// }

//         String quary = "INSERT INTO student(S_no ,name,age,marks )VALUES(101 ,'Abhishek yadav', 23, 89);";

//         int result = stmt.executeUpdate(quary);
//         if (result > 0) {
//             System.out.println("Updated ");
//         } else
//             System.out.println("Not updated");

//         System.out.println("done");

    }
}
