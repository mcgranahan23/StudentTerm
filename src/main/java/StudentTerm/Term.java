package StudentTerm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Term {
    private static final List<Term> terms = new ArrayList<>();
    private static final Scanner reader = new Scanner(System.in);

    private int id; // Unique ID for each term
    private String start;
    private String name;
    private String dates;
    private String description;
    private double chargeAmount;
    private double payment;

    public Term(int id, String start, String name, String dates, String description, double chargeAmount, double payment) {
        this.id = id; // Assign the unique term ID
        this.start = start;
        this.name = name;
        this.dates = dates;
        this.description = description;
        this.chargeAmount = chargeAmount;
        this.payment = payment;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Getter for start
    public String getStart() {
        return start;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for dates
    public String getDates() {
        return dates;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Getter for chargeAmount
    public double getChargeAmount() {
        return chargeAmount;
    }

    // Getter for payment
    public double getPayment() {
        return payment;
    }

    public static Term newTerm() {
        while (true) {
            System.out.println("- Add new term -");
            String start = readLine("Start Date");
            String name = readLine("Term Name");
            String dates = readLine("Term Dates");
            String description = readLine("Term Description");
            double chargeAmount = readDouble("Charge Amount");
            double payment = readDouble("Payment");

            System.out.printf("Start Date - %s%nName - %s%nDates - %s%nDescription - %s%nCharge Amount - %.2f%nPayment - %.2f%n", start, name, dates, description, chargeAmount, payment);
            System.out.println("*Is this correct?*\n1.) Yes\n2.) No");
            if (reader.nextInt() == 1) {
                int id = terms.size() + 1; // Generate a unique ID based on list size (or specify manually)
                Term term = new Term(id, start, name, dates, description, chargeAmount, payment);
                terms.add(term);
                return term;
            }
            reader.nextLine(); // Consume newline
        }
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt + " - ");
        while (!reader.hasNextDouble()) {
            System.out.print("Invalid input. " + prompt + " - ");
            reader.nextLine();
        }
        double value = reader.nextDouble();
        reader.nextLine(); // Consume newline
        return value;
    }

    private static String readLine(String prompt) {
        System.out.print(prompt + " - ");
        return reader.nextLine().trim();
    }

    public static void printAllTerms() {
        if (terms.isEmpty()) {
            System.out.println("No terms available.");
            return;
        }
        for (Term term : terms) {
            System.out.println(term);
            System.out.println(); // For spacing
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %d%nTerm - %s%nStart: %s%nDates: %s%nDescription: %s%nCharge Amount: %.2f%nPayment: %.2f%n",
                id, name, start, dates, description, chargeAmount, payment);
    }
}