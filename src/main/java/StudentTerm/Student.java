package StudentTerm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student {
    private Integer id; // Change from int to Integer
    private String name;
    private String address;
    private String city;
    private String state;
    private int zip;
    private static final List<Student> students = new ArrayList<>();
    private static final Scanner reader = new Scanner(System.in);

    // Constructor
    public Student(Integer id, String name, String address, String city, String state, int zip) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    // Getters and setters (if not already present)
    public Integer getId() {
        if (this.id == null) {
            throw new IllegalStateException("Student ID is not initialized.");
        }

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZip() {
        return zip;
    }

    public static Student newStudent() {
        while (true) {
            System.out.println("- Add new student -");
            int id = readInt("ID");
            String name = readLine("Name");
            String address = readLine("Address");
            String city = readLine("City");
            String state = readLine("State");
            int zip = readInt("Zip");

            System.out.printf("ID - %d%nName - %s%nAddress - %s%nCity - %s%nState - %s%nZip - %d%n", id, name, address, city, state, zip);
            System.out.println("*Is this correct?*\n1.) Yes\n2.) No");
            if (reader.nextInt() == 1) {
                Student student = new Student(id, name, address, city, state, zip);
                students.add(student);
                return student;
            }
            reader.nextLine(); // Consume newline
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt + " - ");
        while (!reader.hasNextInt()) {
            System.out.print("Invalid input. " + prompt + " - ");
            reader.nextLine();
        }
        int value = reader.nextInt();
        reader.nextLine(); // Consume newline
        return value;
    }

    private static String readLine(String prompt) {
        System.out.print(prompt + " - ");
        return reader.nextLine().trim();
    }

    public static void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
            System.out.println(); // For spacing
        }
    }

    @Override
    public String toString() {
        return String.format("Student #%d:%nName: %s%nAddress: %s%nCity: %s%nState: %s%nZip: %d%n", id, name, address, city, state, zip);
    }
}