import java.io.*;
import java.util.*;

public class Attendance {
    private HashMap<String, Integer> attendanceRecords = new HashMap<>();
    private final String ATTENDANCE_FILE = "attendance_data.txt";

    // Load previously stored attendance data if available
    public void loadAttendance() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    attendanceRecords.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No saved attendance found. Starting fresh.");
        } catch (IOException ex) {
            System.out.println("Problem loading attendance: " + ex.getMessage());
        }
    }

    // Save current attendance data to the file
    public void saveAttendance() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ATTENDANCE_FILE))) {
            for (Map.Entry<String, Integer> entry : attendanceRecords.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Problem saving attendance: " + ex.getMessage());
        }
    }

    // Clear out everything
    public void clearAttendance() {
        attendanceRecords.clear();
        System.out.println("All attendance records cleared.");
    }

    // Ask about each student and update their attendance
    public void manageAttendance(List<String> students) {
        Scanner scan = new Scanner(System.in);

        for (String student : students) {
            System.out.print("Is " + student + " here? (Y/N/E): ");
            char status = scan.next().toLowerCase().charAt(0);
            int curr = attendanceRecords.getOrDefault(student, 0);

            if (status == 'y') {
                attendanceRecords.put(student, curr + 1);
                System.out.println(student + " present. New total: " + attendanceRecords.get(student));
            } else if (status == 'n') {
                attendanceRecords.put(student, curr - 1);
                System.out.println(student + " absent. New total: " + attendanceRecords.get(student));
            } else if (status == 'e') {
                // No change for excused
                System.out.println(student + " excused (no change).");
            } else {
                System.out.println("Invalid input for " + student + ", skipping...");
            }
        }
    }

    // Just return what we have so far
    public HashMap<String, Integer> getAttendanceRecords() {
        return attendanceRecords;
    }
}

