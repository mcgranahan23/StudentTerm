package StudentTerm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DataOperations {

    public void addStudent(Student student) {
        String sql = "INSERT INTO Students (id, name, address, city, state, zip) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getAddress());
            pstmt.setString(4, student.getCity());
            pstmt.setString(5, student.getState());
            pstmt.setInt(6, student.getZip());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTerm(Term term) {
        String sql = "INSERT INTO Terms (start, name, dates, description, chargeAmount, payment) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, term.getStart());
            pstmt.setString(2, term.getName());
            pstmt.setString(3, term.getDates());
            pstmt.setString(4, term.getDescription());
            pstmt.setDouble(5, term.getChargeAmount());
            pstmt.setDouble(6, term.getPayment());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void linkStudentToTerm(int studentId, int termId) {
        String sql = "INSERT INTO StudentTerms (studentId, termId) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, termId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addTermAndGetId(Term term) {
        String sql = "INSERT INTO Terms (start, name, dates, description, chargeAmount, payment) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, term.getStart());
            pstmt.setString(2, term.getName());
            pstmt.setString(3, term.getDates());
            pstmt.setString(4, term.getDescription());
            pstmt.setDouble(5, term.getChargeAmount());
            pstmt.setDouble(6, term.getPayment());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indicates failure
    }

    public void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Students ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "address TEXT, "
                + "city TEXT, "
                + "state TEXT, "
                + "zip INTEGER)";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateInitialData() {
        addStudent(new Student(null, "John Doe", "123 Elm Street", "Springfield", "IL", 62704));
        addStudent(new Student(null, "Jane Smith", "456 Oak Avenue", "Monroeville", "PA", 15146));
        addStudent(new Student(null, "Emily Johnson", "789 Maple Drive", "San Francisco", "CA", 94103));
    }
}