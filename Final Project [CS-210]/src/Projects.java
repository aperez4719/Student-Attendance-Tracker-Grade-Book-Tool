
import java.util.*;

public class Projects {
    // Queue for presentations
    // We'll just use a LinkedList as a queue
    private Queue<String> presentationQueue = new LinkedList<>();

    // Graph for group projects (Adjacency List)
    // Each student points to a list of other students they're grouped with
    private Map<String, List<String>> groupGraph = new HashMap<>();

    // Load students into a queue in a random order for presentations
    public void randomizePresentationOrder(List<String> students) {
        List<String> shuffled = new ArrayList<>(students);
        Collections.shuffle(shuffled); // Randomize the order
        presentationQueue.clear();
        presentationQueue.addAll(shuffled);

        System.out.println("Presentation order has been randomized. Ready to present:");
        System.out.println(presentationQueue);
    }

    // Show the next presenter (dequeue from the queue)
    public void showNextPresenter() {
        if (presentationQueue.isEmpty()) {
            System.out.println("No more presenters in the queue.");
            return;
        }
        String next = presentationQueue.poll();
        System.out.println("Next presenter: " + next);
    }

    // Add a group connection between two students
    // For example, student A can form a group link with student B
    public void addGroupConnection(String fromStudent, String toStudent) {
        // Create an empty list if none exists
        groupGraph.putIfAbsent(fromStudent, new ArrayList<>());

        // Add the connection if not already present
        if (!groupGraph.get(fromStudent).contains(toStudent)) {
            groupGraph.get(fromStudent).add(toStudent);
            System.out.println(fromStudent + " is now connected to " + toStudent + " in the group graph.");
        } else {
            System.out.println(fromStudent + " is already connected to " + toStudent + ".");
        }
    }

    // Display the group connections for a particular student
    public void displayGroupConnections(String student) {
        if (!groupGraph.containsKey(student) || groupGraph.get(student).isEmpty()) {
            System.out.println(student + " has no group connections.");
            return;
        }
        System.out.println(student + " is connected to: " + groupGraph.get(student));
    }
}
