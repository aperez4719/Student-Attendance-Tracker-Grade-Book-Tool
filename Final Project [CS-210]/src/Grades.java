import java.io.*;
import java.util.*;

public class Grades {
    private TreeMap<String, Double> gradeRecords = new TreeMap<>();
    private final String GRADES_FILE = "grades_data.txt";

    // Load existing grade data from a file, if available
    public void loadGrades() {
        try (BufferedReader reader = new BufferedReader(new FileReader(GRADES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    try {
                        double grade = Double.parseDouble(parts[1]);
                        gradeRecords.put(parts[0], grade);
                    } catch (NumberFormatException ex) {
                        // Ignore malformed lines, just print a warning
                        System.out.println("Skipping malformed grade line: " + line);
                    }
                }
            }
            System.out.println("Grades loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No existing grades file found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading grades: " + e.getMessage());
        }
    }

    // Save all current grade records to a file
    public void saveGrades() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GRADES_FILE))) {
            for (Map.Entry<String, Double> entry : gradeRecords.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
            System.out.println("Grades saved.");
        } catch (IOException e) {
            System.out.println("Error saving grades: " + e.getMessage());
        }
    }

    // Clear all grade records
    public void clearGrades() {
        gradeRecords.clear();
        System.out.println("All grade records have been cleared.");
    }

    // Set or update a student's grade percentage
    public void setGrade(String studentName, double gradePercent) {
        if (gradePercent < 0.0 || gradePercent > 100.0) {
            System.out.println("Invalid grade percentage. Must be between 0 and 100.");
            return;
        }
        gradeRecords.put(studentName, gradePercent);
        System.out.println("Grade for " + studentName + " set to " + gradePercent + "%");
    }

    // Display all grades in alphabetical order
    public void displayGrades() {
        if (gradeRecords.isEmpty()) {
            System.out.println("No grades to display.");
            return;
        }
        System.out.println("Current Grade Records:");
        for (Map.Entry<String, Double> entry : gradeRecords.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + "%");
        }
    }

    // Remove a student's grade record
    public void removeGrade(String studentName) {
        if (gradeRecords.remove(studentName) != null) {
            System.out.println("Removed grade record for " + studentName);
        } else {
            System.out.println("No grade record found for " + studentName);
        }
    }

    // Add points to a student's current grade
    // Example: If a student has 85%, adding 5 points brings them to 90%.
    // If adding points goes beyond 100%, it caps at 100%.
    public void addPoints(String studentName, double points) {
        // Check if student exists or not
        if (!gradeRecords.containsKey(studentName)) {
            System.out.println("No grade record found for " + studentName + ". Please set a grade first.");
            return;
        }
        
        double currentGrade = gradeRecords.get(studentName);
        double newGrade = currentGrade + points;

        if (newGrade > 100.0) {
            newGrade = 100.0;  // Cap at 100%
        }

        gradeRecords.put(studentName, newGrade);
        System.out.println("Added " + points + " points to " + studentName + "'s grade. New grade: " + newGrade + "%");
    }

    // Get all grades if needed elsewhere
    public TreeMap<String, Double> getGradeRecords() {
        return gradeRecords;
    }
}
