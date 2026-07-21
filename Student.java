import java.time.LocalDate;

public class Student {
    private String studentId;
    private String name;
    private String email;
    private LocalDate registrationDate;

    public Student(String studentId, String name, String email, LocalDate registrationDate) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toCsv() {
        return studentId + "," + name + "," + email + "," + registrationDate;
    }

    public static Student fromCsv(String line) {
        String[] parts = line.split(",", -1);

        if (parts.length != 4) {
            return null;
        }

        try {
            String studentId = parts[0];
            String name = parts[1];
            String email = parts[2];
            LocalDate registrationDate = LocalDate.parse(parts[3]);

            return new Student(studentId, name, email, registrationDate);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return studentId + " - " + name + " - " + email + " - Registered: " + registrationDate;
    }
}
