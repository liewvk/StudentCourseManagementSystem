import java.io.*;
import java.util.ArrayList;

public class FileService {
    private static final String STUDENT_FILE = "students.csv";
    private static final String COURSE_FILE = "courses.csv";
    private static final String ENROLLMENT_FILE = "enrollments.csv";

    public ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Student student = Student.fromCsv(line);

                if (student != null) {
                    students.add(student);
                }
            }

        } catch (IOException e) {
            // File may not exist yet. Start with an empty list.
        }

        return students;
    }

    public ArrayList<Course> loadCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(COURSE_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Course course = Course.fromCsv(line);

                if (course != null) {
                    courses.add(course);
                }
            }

        } catch (IOException e) {
            // File may not exist yet. Start with an empty list.
        }

        return courses;
    }

    public ArrayList<Enrollment> loadEnrollments() {
        ArrayList<Enrollment> enrollments = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ENROLLMENT_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Enrollment enrollment = Enrollment.fromCsv(line);

                if (enrollment != null) {
                    enrollments.add(enrollment);
                }
            }

        } catch (IOException e) {
            // File may not exist yet. Start with an empty list.
        }

        return enrollments;
    }

    public void saveStudents(ArrayList<Student> students) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_FILE))) {
            for (Student student : students) {
                writer.println(student.toCsv());
            }
        }
    }

    public void saveCourses(ArrayList<Course> courses) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COURSE_FILE))) {
            for (Course course : courses) {
                writer.println(course.toCsv());
            }
        }
    }

    public void saveEnrollments(ArrayList<Enrollment> enrollments) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ENROLLMENT_FILE))) {
            for (Enrollment enrollment : enrollments) {
                writer.println(enrollment.toCsv());
            }
        }
    }
}
