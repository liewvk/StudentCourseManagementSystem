import java.time.LocalDate;

public class Enrollment {
    private String studentId;
    private String courseId;
    private LocalDate enrollmentDate;

    public Enrollment(String studentId, String courseId, LocalDate enrollmentDate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public String toCsv() {
        return studentId + "," + courseId + "," + enrollmentDate;
    }

    public static Enrollment fromCsv(String line) {
        String[] parts = line.split(",", -1);

        if (parts.length != 3) {
            return null;
        }

        try {
            String studentId = parts[0];
            String courseId = parts[1];
            LocalDate enrollmentDate = LocalDate.parse(parts[2]);

            return new Enrollment(studentId, courseId, enrollmentDate);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return studentId + " enrolled in " + courseId + " on " + enrollmentDate;
    }
}
