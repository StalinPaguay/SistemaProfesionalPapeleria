import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class FixDB {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Papeleria_PF", "postgres", "1751050913");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("ALTER TABLE usuarios DROP CONSTRAINT IF EXISTS usuarios_rol_check;");
            System.out.println("Constraint dropped successfully!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
