import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MainApp {
    private static ArrayList<Student> students;
    private static ArrayList<Course> courses;
    private static ArrayList<Enrollment> enrollments;

    private static FileService fileService = new FileService();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        students = fileService.loadStudents();
        courses = fileService.loadCourses();
        enrollments = fileService.loadEnrollments();

        int choice;

        do {
            displayMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    addCourse();
                    break;
                case 4:
                    viewCourses();
                    break;
                case 5:
                    enrollStudent();
                    break;
                case 6:
                    viewEnrollments();
                    break;
                case 7:
                    searchStudent();
                    break;
                case 8:
                    saveAllData();
                    System.out.println("Program ended.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 8);

        input.close();
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("Student Course Management System");
        System.out.println("--------------------------------");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Add Course");
        System.out.println("4. View Courses");
        System.out.println("5. Enroll Student in Course");
        System.out.println("6. View Enrollments");
        System.out.println("7. Search Student");
        System.out.println("8. Exit");
    }

    private static void addStudent() {
        System.out.println();
        System.out.println("Add Student");
        System.out.println("-----------");

        String studentId = readText("Enter student ID: ");
        String name = readText("Enter name: ");
        String email = readText("Enter email: ");

        if (studentId.isEmpty() || name.isEmpty() || email.isEmpty()) {
            System.out.println("Student ID, name, and email cannot be empty.");
            return;
        }

        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        if (findStudentById(studentId) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        Student student = new Student(studentId, name, email, LocalDate.now());
        students.add(student);

        saveStudents();

        System.out.println("Student added successfully.");
    }

    private static void viewStudents() {
        System.out.println();
        System.out.println("Student Records");
        System.out.println("---------------");

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void addCourse() {
        System.out.println();
        System.out.println("Add Course");
        System.out.println("----------");

        String courseId = readText("Enter course ID: ");
        String courseName = readText("Enter course name: ");
        double fee = readDouble("Enter course fee: $ ");

        if (courseId.isEmpty() || courseName.isEmpty()) {
            System.out.println("Course ID and course name cannot be empty.");
            return;
        }

        if (fee < 0) {
            System.out.println("Course fee cannot be negative.");
            return;
        }

        if (findCourseById(courseId) != null) {
            System.out.println("Course ID already exists.");
            return;
        }

        Course course = new Course(courseId, courseName, fee);
        courses.add(course);

        saveCourses();

        System.out.println("Course added successfully.");
    }

    private static void viewCourses() {
        System.out.println();
        System.out.println("Course Records");
        System.out.println("--------------");

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private static void enrollStudent() {
        System.out.println();
        System.out.println("Enroll Student in Course");
        System.out.println("------------------------");

        String studentId = readText("Enter student ID: ");
        String courseId = readText("Enter course ID: ");

        Student student = findStudentById(studentId);
        Course course = findCourseById(courseId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (isAlreadyEnrolled(studentId, courseId)) {
            System.out.println("This student is already enrolled in this course.");
            return;
        }

        Enrollment enrollment = new Enrollment(studentId, courseId, LocalDate.now());
        enrollments.add(enrollment);

        saveEnrollments();

        System.out.println("Enrollment successful.");
    }

    private static void viewEnrollments() {
        System.out.println();
        System.out.println("Enrollment Records");
        System.out.println("------------------");

        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }

        for (Enrollment enrollment : enrollments) {
            Student student = findStudentById(enrollment.getStudentId());
            Course course = findCourseById(enrollment.getCourseId());

            String studentName = student != null ? student.getName() : "Unknown Student";
            String courseName = course != null ? course.getCourseName() : "Unknown Course";

            System.out.println("Student : " + enrollment.getStudentId() + " - " + studentName);
            System.out.println("Course  : " + enrollment.getCourseId() + " - " + courseName);
            System.out.println("Date    : " + enrollment.getEnrollmentDate());
            System.out.println();
        }
    }

    private static void searchStudent() {
        System.out.println();
        System.out.println("Search Student");
        System.out.println("--------------");

        String keyword = readText("Enter student ID or name: ").toLowerCase();

        if (keyword.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return;
        }

        boolean found = false;

        for (Student student : students) {
            if (student.getStudentId().toLowerCase().contains(keyword)
                    || student.getName().toLowerCase().contains(keyword)) {
                System.out.println(student);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching student found.");
        }
    }

    private static Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(studentId)) {
                return student;
            }
        }

        return null;
    }

    private static Course findCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equalsIgnoreCase(courseId)) {
                return course;
            }
        }

        return null;
    }

    private static boolean isAlreadyEnrolled(String studentId, String courseId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId().equalsIgnoreCase(studentId)
                    && enrollment.getCourseId().equalsIgnoreCase(courseId)) {
                return true;
            }
        }

        return false;
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(input.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(input.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && !email.contains(" ");
    }

    private static void saveStudents() {
        try {
            fileService.saveStudents(students);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    private static void saveCourses() {
        try {
            fileService.saveCourses(courses);
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }

    private static void saveEnrollments() {
        try {
            fileService.saveEnrollments(enrollments);
        } catch (IOException e) {
            System.out.println("Error saving enrollments: " + e.getMessage());
        }
    }

    private static void saveAllData() {
        saveStudents();
        saveCourses();
        saveEnrollments();
    }
}
