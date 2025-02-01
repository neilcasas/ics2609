package com.mycompany.databasehandlerlab;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    public static class Student {

        String number;
        String fname;
        String mname;
        String lname;
        String sex;
        String birth;
        int start;
        String department;
        int units;
        String address;

        public Student(String number, String fname, String mname, String lname, String sex, String birth,
                int start, String department, int units, String address) {
            this.number = number;
            this.fname = fname;
            this.mname = mname;
            this.lname = lname;
            this.sex = sex;
            this.birth = birth;
            this.start = start;
            this.department = department;
            this.units = units;
            this.address = address;
        }

        public Student() {
        }

        @Override
        public String toString() {
            return this.number + "-" + this.fname + "-" + this.lname + " Units: " + this.units + "\n";
        }
    }

    private Connection conn;

    public DatabaseHandler(String database) {
        String connStr = "jdbc:sqlite:" + database;
        try {
            conn = DriverManager.getConnection(connStr);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            System.err.println("Failed to create connection: " + e.getMessage());
        }
    }

    public void initializeStudents() {
        String dropStr = "DROP TABLE IF EXISTS Students;";
        String sqlStr
                = "CREATE TABLE Students ("
                + "student_number TEXT NOT NULL PRIMARY KEY, "
                + "student_fname TEXT NOT NULL, "
                + "student_mname TEXT, "
                + "student_lname TEXT NOT NULL, "
                + "student_sex TEXT NOT NULL, "
                + "student_birth TEXT NOT NULL, "
                + "student_start INTEGER NOT NULL, "
                + "student_department TEXT NOT NULL, "
                + "student_units INTEGER NOT NULL, "
                + "student_address TEXT);";

        try {
            PreparedStatement drop = conn.prepareStatement(dropStr);
            drop.execute();

            PreparedStatement init = conn.prepareStatement(sqlStr);
            init.execute();

            System.out.println("Students table initialized.");
        } catch (SQLException e) {
            System.err.println("Error initializing Students table: " + e.getMessage());
        }
    }

    public Student getStudent(String studentNumber) throws SQLException {
        String sqlStr = "SELECT * FROM Students WHERE student_number = ?";
        Student s = null;
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, studentNumber);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                s = new Student(
                        rs.getString("student_number"),
                        rs.getString("student_fname"),
                        rs.getString("student_mname"),
                        rs.getString("student_lname"),
                        rs.getString("student_sex"),
                        rs.getString("student_birth"),
                        rs.getInt("student_start"),
                        rs.getString("student_department"),
                        rs.getInt("student_units"),
                        rs.getString("student_address")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
        }
        return s;
    }

    public Student getStudent(String studentFname, String studentMname, String studentLname) throws SQLException {
        String sqlStr = "SELECT * FROM Students WHERE student_fname = ? AND student_mname = ? AND student_lname = ?";
        Student s = null;
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, studentFname);
        pstmt.setString(2, studentMname);
        pstmt.setString(3, studentLname);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                s = new Student(
                        rs.getString("student_number"),
                        rs.getString("student_fname"),
                        rs.getString("student_mname"),
                        rs.getString("student_lname"),
                        rs.getString("student_sex"),
                        rs.getString("student_birth"),
                        rs.getInt("student_start"),
                        rs.getString("student_department"),
                        rs.getInt("student_units"),
                        rs.getString("student_address")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
        }
        return s;
    }

    public ArrayList<Student> getStudents() throws SQLException {
        String sqlStr = "SELECT * FROM Students";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);

        ArrayList<Student> students = new ArrayList<>();

        try (ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getString("student_number"),
                        rs.getString("student_fname"),
                        rs.getString("student_mname"),
                        rs.getString("student_lname"),
                        rs.getString("student_sex"),
                        rs.getString("student_birth"),
                        rs.getInt("student_start"),
                        rs.getString("student_department"),
                        rs.getInt("student_units"),
                        rs.getString("student_address")
                );
                students.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }

        return students;
    }

    public Boolean removeStudent(String studentNumber) throws SQLException {
        String sqlStr = "DELETE FROM Students WHERE student_number = ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, studentNumber);

        try {
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Successfully deleted " + studentNumber);
                return true;
            } else {
                System.err.println("Delete unsuccesful: No students matched the query.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Delete unsuccessful: " + e.getMessage());
        }
        return false;
    }

    public ArrayList<Student> getStudentsByYear(int year) throws SQLException {
        String sqlStr = "SELECT * FROM Students s WHERE s.student_number LIKE ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, year + "%");

        ArrayList<Student> students = new ArrayList<>();

        try (ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getString("student_number"),
                        rs.getString("student_fname"),
                        rs.getString("student_mname"),
                        rs.getString("student_lname"),
                        rs.getString("student_sex"),
                        rs.getString("student_birth"),
                        rs.getInt("student_start"),
                        rs.getString("student_department"),
                        rs.getInt("student_units"),
                        rs.getString("student_address")
                );
                students.add(s);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }

        return students;
    }

    public Boolean updateStudentInfo(String studentNumber, Student studentInfo) throws SQLException {
        String sqlStr = "UPDATE Students\n"
                + "SET student_fname = ?\n"
                + ", student_mname = ?\n"
                + ", student_lname = ?\n"
                + ", student_department = ?\n"
                + ", student_address = ?\n"
                + "WHERE student_number = ?";

        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, studentInfo.fname);
        pstmt.setString(2, studentInfo.mname);
        pstmt.setString(3, studentInfo.lname);
        pstmt.setString(4, studentInfo.department);
        pstmt.setString(5, studentInfo.address);
        pstmt.setString(6, studentNumber);

        try {
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return true;
            } else {
                System.err.println("Update unsuccesful: No students matched the query.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Update unsuccessful: " + e.getMessage());
        }
        return false;
    }

    public Boolean updateStudentUnits(String studentNumber, int subtractedUnits) throws SQLException {
        String sqlStr = "UPDATE Students \n"
                + "SET student_units = ?\n"
                + "WHERE student_number = ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setInt(1, subtractedUnits);
        pstmt.setString(2, studentNumber);

        try {
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return true;
            } else {
                System.err.println("Update unsuccesful: No students matched the query.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Update unsuccessful: " + e.getMessage());
        }
        return false;
    }

    public Boolean insertStudent(Student newStudent) throws SQLException {
        String sqlStr = "INSERT INTO Students (\n"
                + "student_number\n"
                + ", student_fname\n"
                + ", student_mname\n"
                + ", student_lname\n"
                + ", student_sex\n"
                + ", student_birth\n"
                + ", student_start\n"
                + ", student_department\n"
                + ", student_units\n"
                + ", student_address\n"
                + ") values (\n"
                + "?\n"
                + ", ?\n"
                + ", ?\n"
                + ", ?\n"
                + ", ?\n"
                + ", ?\n"
                + ", ?\n"
                + ", ?\n"
                + ", ?\n"
                + ", ?\n"
                + ")";

        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, newStudent.number);
        pstmt.setString(2, newStudent.fname);
        pstmt.setString(3, newStudent.mname);
        pstmt.setString(4, newStudent.lname);
        pstmt.setString(5, newStudent.sex);
        pstmt.setString(6, newStudent.birth);
        pstmt.setInt(7, newStudent.start);
        pstmt.setString(8, newStudent.department);
        pstmt.setInt(9, newStudent.units);
        pstmt.setString(10, newStudent.address);

        try {
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return true;
            } else {
                System.err.println("Update unsuccesful: No students matched the query.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Update unsuccessful: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) throws SQLException {
        String dbPath = "C:\\Users\\neila\\Documents\\dev\\ics2609\\DatabaseHandlerLab\\students";
        DatabaseHandler dbh = new DatabaseHandler(dbPath);

        // Initialize the Students table
        dbh.initializeStudents();

        // Insert a new student
        Student s = new Student(
                "20250102023",
                "John",
                "Doe",
                "Smith",
                "M",
                "2003-01-01",
                2025,
                "College of Information and Computing Sciences",
                15,
                "Lacson St."
        );
        dbh.insertStudent(s);

        Student s1 = new Student(
                "20240121248",
                "Michael",
                "Ego",
                "Kaiser",
                "M",
                "2002-01-01",
                2024,
                "Faculty of Engineering",
                15,
                "P. Noval St."
        );
        dbh.insertStudent(s1);
        
        System.out.println("");

        // Retrieve a student by student number
        System.out.println("Retrieve a student by student number:");
        Student retrievedBySn = dbh.getStudent("20250102023");
        System.out.println(retrievedBySn.toString());

        // Retrieve a student by name
        System.out.println("Retrieve a student by name:");
        Student retrievedByName = dbh.getStudent("Michael", "Ego", "Kaiser");
        System.out.println(retrievedByName.toString());

        // Retrieve all students
        ArrayList<Student> allStudents = dbh.getStudents();
        System.out.println("Retrieving all students: ");
        for (Student student : allStudents) {
            System.out.print(student.toString());
        }

        System.out.println("");
        
        // Retrieve all students by year
        System.out.println("Retrieving students by year 2024:");
        ArrayList<Student> studentsByYear = dbh.getStudentsByYear(2024);
        for (Student student : studentsByYear) {
            System.out.print(student.toString());
        }

        // Update student information
        Student updatedStudentInfo = new Student(
                "20250102023",
                "Seishiro",
                "Hassle",
                "Nagi",
                "M",
                "2003-11-01",
                2025,
                "CICS",
                15,
                "456 Elm St"
        );
        dbh.updateStudentInfo("20250102023", updatedStudentInfo);
        Student updatedStudent = dbh.getStudent("20250102023");
        
        System.out.println("Updated student: ");
        System.out.println(updatedStudent.toString());

        dbh.updateStudentUnits("20250102023", 12);
        Student updatedUnitsStudent = dbh.getStudent("20250102023");
        System.out.println("Lesser units: " + updatedUnitsStudent.toString());

//        // Delete a student
//        System.out.println("");
//        dbh.removeStudent("20250102023");
    }
}
