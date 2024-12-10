import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();

        // Prompt the user at the start
        System.out.println("Welcome to the Student Management System!");
        System.out.print("How many students are in your class? ");
        int numStudents = scan.nextInt();

        // Create our Students manager
        Students myStudents = new Students();
        myStudents.addStudent(numStudents); 
        // We now have an initial roster of students

        char input = 0; // main menu option
        outerLoop:
        while (input != 'q') {
            System.out.println("\nMain Menu:");
            System.out.print("[S] Students | [A] Attendance | [G] Grades | [P] Projects | [q] Quit: ");
            input = scan.next().charAt(0);

            switch (input) {
                case 'S':
                    // Student options menu
                    System.out.print("\nStudent Options:\n"
                            + "[0] View Students\n"
                            + "[1] Add More Students\n"
                            + "[2] Remove a Student\n"
                            + "[3] Pick a Random Student\n"
                            + "[4] Sort Students\n"
                            + "[b] Back: ");
                    input = scan.next().charAt(0);

                    switch (input) {
                        case '0':
                            // Just list out who we have now
                            System.out.println("Students in your class: " + myStudents.getStudents());
                            continue outerLoop;

                        case '1':
                            // Add more students to the list
                            System.out.print("How many do you want to add? ");
                            numStudents = scan.nextInt();
                            myStudents.addStudent(numStudents);
                            continue outerLoop;

                        case '2':
                            // Remove someone by name
                            System.out.print("Enter the name of the student to remove: ");
                            scan.nextLine(); 
                            String removeName = scan.nextLine();
                            if (myStudents.getStudents().remove(removeName)) {
                                System.out.println(removeName + " has been removed.");
                            } else {
                                System.out.println(removeName + " not found in the list.");
                            }
                            continue outerLoop;

                        case '3':
                            // Pick a random student for something
                            myStudents.callRandom();
                            continue outerLoop;

                        case '4':
                            // Sort the students alphabetically
                            Collections.sort(myStudents.getStudents());
                            System.out.println("Students have been sorted: " + myStudents.getStudents());
                            continue outerLoop;

                        case 'b':
                            // Just go back without doing anything
                            continue outerLoop;

                        default:
                            System.out.println("Invalid option, please try again.");
                            continue outerLoop;
                    }

                case 'A':
                    // Attendance menu
                    Attendance att = new Attendance();
                    att.loadAttendance(); // Try loading previous records if any

                    while (true) {
                        System.out.print("\nAttendance Options:\n"
                                + "[0] Take Attendance\n"
                                + "[1] Display Attendance\n"
                                + "[2] Clear Records\n"
                                + "[b] Back: ");
                        input = scan.next().charAt(0);

                        switch (input) {
                            case '0':
                                // Mark who's here, absent, or excused
                                System.out.println("Taking attendance now...");
                                att.manageAttendance(myStudents.getStudents());
                                break;

                            case '1':
                                // Show current attendance tallies
                                System.out.println("Attendance Records:");
                                for (Map.Entry<String, Integer> entry : att.getAttendanceRecords().entrySet()) {
                                    System.out.println(entry.getKey() + ": " + entry.getValue());
                                }
                                break;

                            case '2':
                                // Wipe everything clean
                                System.out.println("Clearing all attendance records...");
                                att.clearAttendance();
                                att.saveAttendance();
                                break;

                            case 'b':
                                // Save before leaving and break out of this loop
                                att.saveAttendance();
                                continue outerLoop;

                            default:
                                System.out.println("Invalid choice. Try again.");
                        }
                    }

                case 'G':
                    // Grades menu
                    Grades grades = new Grades();
                    grades.loadGrades(); // Load any existing grade data

                    boolean handleGrades = true;
                    while (handleGrades) {
                        System.out.print("\nGrades Menu:\n"
                                + "[0] Set/Update a Student's Grade\n"
                                + "[1] View All Grades\n"
                                + "[2] Remove a Student's Grade\n"
                                + "[3] Clear All Grades\n"
                                + "[4] Add Points to a Student's Grade\n"
                                + "[b] Back: ");
                        input = scan.next().charAt(0);

                        switch (input) {
                            case '0':
                                // Assign or update a grade for someone
                                System.out.print("Student's name: ");
                                scan.nextLine(); 
                                String gradename = scan.nextLine();
                                System.out.print("Grade percentage (0-100): ");
                                double gVal = scan.nextDouble();
                                grades.setGrade(gradename, gVal);
                                break;

                            case '1':
                                // List out all current grades
                                grades.displayGrades();
                                break;

                            case '2':
                                // Remove a particular student's grade
                                System.out.print("Name to remove: ");
                                scan.nextLine();
                                String rmName = scan.nextLine();
                                grades.removeGrade(rmName);
                                break;

                            case '3':
                                // Clear all grading info
                                grades.clearGrades();
                                break;

                            case '4':
                                // Add some points to boost a student's grade
                                System.out.print("Student's name: ");
                                scan.nextLine();
                                String sName = scan.nextLine();
                                System.out.print("Points to add: ");
                                double addVal = scan.nextDouble();
                                grades.addPoints(sName, addVal);
                                break;

                            case 'b':
                                // Save grades and exit this menu
                                grades.saveGrades();
                                handleGrades = false;
                                break;

                            default:
                                System.out.println("Not a valid option, try again.");
                        }
                    }
                    break;

                case 'P':
                    // Projects menu (presentations and group connections)
                    Projects projects = new Projects();
                    boolean doingProjects = true;

                    while (doingProjects) {
                        System.out.print("\nProjects Menu:\n"
                                + "[0] Randomize Presentation Order\n"
                                + "[1] Show Next Presenter\n"
                                + "[2] Add Group Connection\n"
                                + "[3] Display Group Connections\n"
                                + "[b] Back: ");
                        input = scan.next().charAt(0);

                        switch (input) {
                            case '0':
                                // Random order for presentations
                                if (myStudents.getStudents().isEmpty()) {
                                    System.out.println("No students available to randomize.");
                                } else {
                                    projects.randomizePresentationOrder(myStudents.getStudents());
                                }
                                break;

                            case '1':
                                // Show whoever is up next to present
                                projects.showNextPresenter();
                                break;

                            case '2':
                                // Link two students in a group
                                if (myStudents.getStudents().isEmpty()) {
                                    System.out.println("No students to form connections with right now.");
                                } else {
                                    System.out.print("Name of the student starting the connection: ");
                                    scan.nextLine(); 
                                    String fromStudent = scan.nextLine();

                                    System.out.print("Name of the student to connect with: ");
                                    String toStudent = scan.nextLine();

                                    if (!myStudents.getStudents().contains(fromStudent)) {
                                        System.out.println("Can't find " + fromStudent + " in the class.");
                                        break;
                                    }
                                    if (!myStudents.getStudents().contains(toStudent)) {
                                        System.out.println("Can't find " + toStudent + " in the class.");
                                        break;
                                    }

                                    projects.addGroupConnection(fromStudent, toStudent);
                                }
                                break;

                            case '3':
                                // Show who a particular student is connected to
                                System.out.print("Enter the student's name: ");
                                scan.nextLine(); 
                                String stName = scan.nextLine();
                                projects.displayGroupConnections(stName);
                                break;

                            case 'b':
                                // Return to main menu
                                doingProjects = false;
                                break;

                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    }
                    break;

                case 'q':
                    // Quit the whole application
                    System.out.println("Exiting. Have a great day!");
                    break outerLoop;

                default:
                    System.out.println("That option doesn't exist. Try again.");
            }
        }

        scan.close();
    }
}
