package schedule_system.utils;

/**
 * Course
 */
public class Course {
    // TODO: 检查输入参数是否符合要求，将函数返回值改为boolean
    // 若符合，return true，否则return false

    private int startWeek; // 课程开始周
    private int endWeek; // 课程结束周
    private int testWeek; // 课程考试周
    private ClassTime classTime; // 每周的上课时间
    private ClassTime examTime; // 考试时间
    private String name; // 课程名称（唯一id）
    private Location location; // 课程地点

    public Course(
            final int startWeek,
            final int endWeek,
            final int testWeek,
            final ClassTime classTime,
            final ClassTime examTime,
            final String name,
            final Location location) {
        setStartWeek(startWeek);
        setEndWeek(endWeek);
        setTestWeek(testWeek);
        setClassTime(classTime);
        setExamTime(examTime);
        setName(name);
        setLocation(location);
    }

    public boolean covers(int week) {
        return this.startWeek <= week && this.endWeek >= week;
    }

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    private void setLocation(final Location location) {
        this.location = location;
    }

    public boolean isPeriodic() {
        return this.startWeek == this.endWeek;
    }

    public int getStartWeek() {
        return startWeek;
    }

    private void setStartWeek(final int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    private void setEndWeek(final int endWeek) {
        this.endWeek = endWeek;
    }

    public int getTestWeek() {
        return testWeek;
    }

    private void setTestWeek(final int testWeek) {
        this.testWeek = testWeek;
    }

    public ClassTime getClassTime() {
        return classTime;
    }

    private void setClassTime(final ClassTime classTime) {
        this.classTime = classTime;
    }

    public ClassTime getExamTime() {
        return examTime;
    }

    private void setExamTime(final ClassTime examTime) {
        this.examTime = examTime;
    }
}
