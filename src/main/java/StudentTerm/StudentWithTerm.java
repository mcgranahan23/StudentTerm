package StudentTerm;

import java.util.ArrayList;
import java.util.List;

public class StudentWithTerm {
    private Student student;
    private Term term;

    private static final List<StudentWithTerm> studentWithTermList = new ArrayList<>();

    public StudentWithTerm(Student student, Term term) {
        this.student = student;
        this.term = term;
        studentWithTermList.add(this);
    }

    public static void addStudentTermPair(Student student, Term term) {
        new StudentWithTerm(student, term);
    }

    public static void printAll() {
        if (studentWithTermList.isEmpty()) {
            System.out.println("No student-term associations available.");
            return;
        }
        for (StudentWithTerm studentWithTerm : studentWithTermList) {
            System.out.println("Student and Term:");
            System.out.println(studentWithTerm.student);
            System.out.println(studentWithTerm.term);
            System.out.println(); // For spacing
        }
    }
}