public class Course {
    private String courseId;
    private String courseName;
    private double fee;

    public Course(String courseId, String courseName, double fee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.fee = fee;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getFee() {
        return fee;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String toCsv() {
        return courseId + "," + courseName + "," + fee;
    }

    public static Course fromCsv(String line) {
        String[] parts = line.split(",", -1);

        if (parts.length != 3) {
            return null;
        }

        try {
            String courseId = parts[0];
            String courseName = parts[1];
            double fee = Double.parseDouble(parts[2]);

            return new Course(courseId, courseName, fee);

        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return courseId + " - " + courseName + " - Fee: $ " + String.format("%.2f", fee);
    }
}
