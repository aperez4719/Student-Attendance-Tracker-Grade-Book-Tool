import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

public class Students {

    private ArrayList<String> student = new ArrayList<>();
    private Hashtable<Integer, String> studentIDs = new Hashtable<>();
    private Random rand = new Random();

    // Add the specified number of students directly into 'student'
    public ArrayList<String> addStudent(int num) {
        Scanner scan = new Scanner(System.in);

        for (int i = 0; i < num; i++) {
            System.out.print("Insert Name: ");
            String name = scan.next();
            student.add(name);
        }

        // Print updated student list
        System.out.println("Here are your students: " + student);

        // Update their IDs
        studentIDs = studentIDs(student);
        System.out.println("Here are their IDs: " + studentIDs);

        return student;
    }

    // Generate a hashtable of IDs for the students currently in 'student'
    public Hashtable<Integer, String> studentIDs(ArrayList<String> student) {
        Hashtable<Integer, String> ids = new Hashtable<>(student.size());
        for (String s : student) {
            int key;
            // Ensure unique IDs for each student by checking for existing keys
            do {
                key = rand.nextInt(900) + 100; // IDs between 100 and 999
            } while (ids.containsKey(key));
            ids.put(key, s);
        }
        return ids;
    }

    // Return the main student list
    public ArrayList<String> getStudents() {
        return student;
    }

    // Print the current student list
    public void printStudents() {
        System.out.println(student);
    }

    // Call a random student from the list
    public void callRandom() {
        if (student.isEmpty()) {
            System.out.println("No students to pick from.");
            return;
        }
        System.out.println("\n" + student.get(rand.nextInt(student.size())));
    }
}
