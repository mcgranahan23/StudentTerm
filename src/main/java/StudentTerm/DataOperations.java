package StudentTerm;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataOperations {

    public DataOperations() {
        initializeTables();
    }

    private void initializeTables() {
        executeUpdate("""
       CREATE TABLE IF NOT EXISTS Students (
           id INTEGER PRIMARY KEY AUTOINCREMENT,
           name TEXT NOT NULL,
           address TEXT,
           city TEXT,
           state TEXT,
           zip INT
       )
   """, pstmt -> {});
        executeUpdate("""
       CREATE TABLE IF NOT EXISTS Terms (
           id INTEGER PRIMARY KEY AUTOINCREMENT,
           start TEXT,
           name TEXT NOT NULL,
           dates TEXT,
           description TEXT,
           chargeAmount REAL,
           payment REAL
       )
   """, pstmt -> {});
        executeUpdate("""
       CREATE TABLE IF NOT EXISTS StudentTerms (
           studentId INTEGER,
           termId INTEGER,
           PRIMARY KEY (studentId, termId),
           FOREIGN KEY (studentId) REFERENCES Students(id),
           FOREIGN KEY (termId) REFERENCES Terms(id)
       )
   """, pstmt -> {});
    }

    public void addOrUpdateStudent(Student student) {
        if (doesStudentExist(student.getName())) {
            updateStudent(student);
        } else {
            insertStudent(student);
        }
    }

    private boolean doesStudentExist(String name) {
        String sql = "SELECT 1 FROM Students WHERE name = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void insertStudent(Student student) {
        executeUpdate("INSERT INTO Students (name, address, city, state, zip) VALUES (?, ?, ?, ?, ?)",
                pstmt -> {
                    pstmt.setString(1, student.getName());
                    pstmt.setString(2, student.getAddress());
                    pstmt.setString(3, student.getCity());
                    pstmt.setString(4, student.getState());
                    pstmt.setInt(5, student.getZip());
                });
    }

    private void updateStudent(Student student) {
        executeUpdate("UPDATE Students SET address = ?, city = ?, state = ?, zip = ? WHERE name = ?",
                pstmt -> {
                    pstmt.setString(1, student.getAddress());
                    pstmt.setString(2, student.getCity());
                    pstmt.setString(3, student.getState());
                    pstmt.setInt(4, student.getZip());
                    pstmt.setString(5, student.getName());
                });
    }

    public void addTermIfNotExist(Term term) {
        if (!doesTermExist(term)) {
            addTerm(term);
        }
    }

    private boolean doesTermExist(Term term) {
        String sql = "SELECT 1 FROM Terms WHERE start = ? AND name = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, term.getStart());
            pstmt.setString(2, term.getName());
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addTerm(Term term) {
        executeUpdate("INSERT INTO Terms (start, name, dates, description, chargeAmount, payment) VALUES (?, ?, ?, ?, ?, ?)",
                pstmt -> {
                    pstmt.setString(1, term.getStart());
                    pstmt.setString(2, term.getName());
                    pstmt.setString(3, term.getDates());
                    pstmt.setString(4, term.getDescription());
                    pstmt.setDouble(5, term.getChargeAmount());
                    pstmt.setDouble(6, term.getPayment());
                });
    }

    public void linkStudentToTerm(int studentId, int termId) {
        executeUpdate("INSERT INTO StudentTerms (studentId, termId) VALUES (?, ?)",
                pstmt -> {
                    pstmt.setInt(1, studentId);
                    pstmt.setInt(2, termId);
                });
    }

    public Term getTermByStudentId(int studentId) {
        String sql = "SELECT Terms.* FROM Terms JOIN StudentTerms ON Terms.id = StudentTerms.termId WHERE StudentTerms.studentId = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Include the `id` obtained from the database in the constructor
                    return new Term(
                            rs.getInt("id"), // id
                            rs.getString("start"), // start
                            rs.getString("name"), // name
                            rs.getString("dates"), // dates
                            rs.getString("description"), // description
                            rs.getDouble("chargeAmount"), // chargeAmount
                            rs.getDouble("payment") // payment
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no term is found.
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT id, name, address, city, state, zip FROM Students";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getInt("zip")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static void resetDatabase() {
        DataOperations dataOperations = new DataOperations(); // Create an instance
        String sql = "DELETE FROM Students"; // Example query to clear the table
        dataOperations.executeUpdate(sql, pstmt -> {
            // No parameters needed for this query
        });
        System.out.println("Database reset successfully.");
    }

    private void clearTable(String tableName) {
        String sql = "DELETE FROM " + tableName;
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println(tableName + " table cleared.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateInitialData() {
        addOrUpdateStudent(new Student(null, "John Doe", "123 Elm St", "Springfield", "IL", 62704));
        addOrUpdateStudent(new Student(null, "Jane Smith", "456 Oak Ave", "Monroeville", "PA", 15146));
        addOrUpdateStudent(new Student(null, "Emily Johnson", "789 Maple Dr", "San Francisco", "CA", 94103));
    }

    public List<Term> populateGenericTerms() {
        List<Term> terms = Arrays.asList(
                new Term(1, "2024-01-10", "Spring 2024", "2024-01-10 to 2024-05-15", "Spring semester of 2024", 5000.0, 0.0),
                new Term(2, "2024-06-10", "Summer 2024", "2024-06-10 to 2024-08-15", "Summer for accelerating credits", 3000.0, 0.0),
                new Term(3, "2024-09-05", "Fall 2024", "2024-09-05 to 2024-12-20", "Fall semester of 2024", 5000.0, 0.0)
        );
        for (Term term : terms) {
            addTermIfNotExist(term);
        }
        return terms;
    }

    // Utility method to execute updates with prepared statements
    public void executeUpdate(String sql, SQLConsumer<PreparedStatement> consumer) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            consumer.accept(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing update: " + sql);
            e.printStackTrace();
        }
    }

    // Functional interface for SQLConsumer
    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
}