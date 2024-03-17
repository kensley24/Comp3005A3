import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static String url = "jdbc:postgresql://localhost:5432/Students";
    static String user = "postgres";
    static String password = "passwrd";

    public static String getAllStudents(){

        StringBuilder str = new StringBuilder();

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            statement.executeQuery("SELECT * FROM students");
            ResultSet set = statement.getResultSet();
            while (set.next()) {
                int id = set.getInt("student_id");
                String first_name = set.getString("first_name");
                String last_name = set.getString("last_name");
                String email = set.getString("email");
                Date enrollment_date = set.getDate("enrollment_date");
                str.append(id + " " + first_name + " " + last_name + " " + email + " " + enrollment_date + "\n");
            }

        }catch (Exception e){
            System.out.println(e);
        }


        return str.toString();
    }

    public static String addStudent(String first, String last, String em, Date enrollment){

        int rows = -1;

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            String insert = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
            PreparedStatement prepared = conn.prepareStatement(insert);

            prepared.setString(1, first);
            prepared.setString(2, last);
            prepared.setString(3, em);
            prepared.setDate(4, enrollment);

            rows = prepared.executeUpdate();

        }catch (Exception e){
            System.out.println(e);
        }
        if(rows > 0){
            return("A new student was inserted successfully");

        }
        else{
            return("There was a problem with inserting the student");
        }
    }

    public static String updateStudentEmail(Integer s_id, String new_email){

        int rows = -1;

        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            String updateEmail = "UPDATE students SET email = ? WHERE student_id = ?";
            PreparedStatement prepared = conn.prepareStatement(updateEmail);
            prepared.setString(1, new_email);
            prepared.setInt(2, s_id);

            rows = prepared.executeUpdate();

        }catch (Exception e){
            System.out.println(e);
        }
        if(rows > 0){
            return(String.format("Student with id %d has updated email", s_id));

        }
        else{
            return(String.format("There was a problem with updating student with id %d", s_id));
        }
    }

    public static String deleteStudent(Integer s_id){

        int rows = -1;

        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            String updateEmail = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement prepared = conn.prepareStatement(updateEmail);
            prepared.setInt(1, s_id);

            rows = prepared.executeUpdate();

        }catch (Exception e){
            System.out.println(e);
        }
        if(rows > 0){
            return(String.format("Student with id %d has been deleted", s_id));

        }
        else{
            return(String.format("There was a problem with deleting student with id %d", s_id));
        }
    }

    public static void main(String[] args) {

        /*

        String url = "jdbc:postgresql://localhost:5432/Students";
        String user = "postgres";
        String password = "passwrd";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            statement.executeQuery("SELECT * FROM students");
            ResultSet set = statement.getResultSet();
            while(set.next()){
                System.out.println(set.getString("first_name"));
            }

            if(conn != null){
                System.out.println("Connected to Database!");
            }
            else{
                System.out.println("did not connect :(");
            }

        }
        catch (Exception e){
            System.out.println(e);
        }

         */
        long millis=System.currentTimeMillis();
        Date d =new Date(millis);

        System.out.println(getAllStudents());
        //System.out.println(deleteStudent(2));
        //System.out.println(addStudent("Squid", "Ward", "GrumpySquid420@gmail.com", d));

    }
}