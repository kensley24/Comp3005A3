import java.sql.*;
import java.util.Scanner;

public class Main {
    static String url = "jdbc:postgresql://localhost:5432/Students";
    static String user;
    static String password;

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

    public static int prompt(){
        Scanner in = new Scanner(System.in);
        System.out.println("------------------------------------------------");
        System.out.println("input 1: to see the database \ninput 2: to add a student to the database \ninput 3: to update a students email \ninput 4: to delete a student \ninput 0: to exit");
        System.out.println("------------------------------------------------");
        int c = in.nextInt();
        if(c < 0 || c > 4){
            System.out.println("Error: Not a valid input");
            return -1;
        }
        return c;

    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("enter your username and password for your postgres account with localhost:5432 and Database Students");
        System.out.println("username: ");
        user = scan.next();
        System.out.println("password: ");
        password = scan.next();
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String startQuery = "drop table if exists students;\n" +
                    "\n" +
                    "CREATE TABLE students (\n" +
                    "\tstudent_id Serial Primary Key,\n" +
                    "\tfirst_name Text Not Null,\n" +
                    "\tlast_name Text Not Null,\n" +
                    "\temail Text Not Null Unique,\n" +
                    "\tenrollment_date Date\n" +
                    ");\n" +
                    "\n" +
                    "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES\n" +
                    "('John', 'Doe', 'john.doe@example.com', '2023-09-01'),\n" +
                    "('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),\n" +
                    "('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');";
            stmt.executeUpdate(startQuery);


        }catch (Exception e){
            System.out.println(e);
            return;
        }


        int c = prompt();
        while(c != 0){
            //see database
            if(c == 1){
                System.out.println("Here is the database: \n" + getAllStudents());
            }
            //add student
            else if(c == 2){
                System.out.println("First name: ");
                String first = scan.next();
                System.out.println("Last name: ");
                String last = scan.next();
                System.out.println("Student Email: ");
                String email = scan.next();
                long millis=System.currentTimeMillis();
                Date d =new Date(millis);
                System.out.println(addStudent(first, last, email, d));
            }
            //update email
            else if(c == 3){
                System.out.println("Student ID: ");
                int id = scan.nextInt();
                System.out.println("Updated Email: ");
                String email = scan.next();
                System.out.println(updateStudentEmail(id, email));
            }
            //delete student
            else if(c == 4){
                System.out.println("Sudent ID: ");
                int id = scan.nextInt();
                System.out.println(deleteStudent(id));
            }
            c = prompt();
        }

    }
}