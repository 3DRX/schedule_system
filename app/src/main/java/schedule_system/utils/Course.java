package schedule_system.utils;

/**
 * Course
 */
public class Course {
    // 检查输入参数是否符合要求
    // 规则如下：
    // 1. 一学期共有20周（即课程或考试不能>20）
    // 2. 课程结束周必须大于等于课程开始周
    // 3. 课程考试周必须大于课程结束周
    // 4. 课程的上课时间和考试时间须合法

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

        if (endWeek > 20 || startWeek > 20 || testWeek > 20)
            throw new IllegalArgumentException("学期周数要小于等于20！！！！");
        if (startWeek > endWeek)
            throw new IllegalArgumentException("课程结束周必须大于等于课程开始周！！！！");
        if (testWeek <= endWeek)
            throw new IllegalArgumentException("课程考试周必须大于课程结束周！！！！");
        if (endWeek <= 0 || startWeek <= 0 || testWeek <= 0)
            throw new IllegalArgumentException("周数不能是负数！！！！");
        setStartWeek(startWeek);
        setEndWeek(endWeek);
        setTestWeek(testWeek);
        setClassTime(classTime);
        setExamTime(examTime);
        setName(name);
        setLocation(location);
    }

    // TODO for Ali:
    // 下面三个方法（也就是有TEST ME这个注释的三个方法，注意有序号，按序号进行测试）
    // 分别是用来检测不同种类的冲突的（如果不清楚这个逻辑微信里直接问就行～）
    //
    // 测试用例代码写在/app/src/test/java/schedule_system/ConflictCheckTest.java文件中
    // 写测试用例的语法可以看这个教程https://tonydeng.github.io/2017/10/09/junit-5-tutorial-introduction/
    // 这个教程中关于安装环境依赖的部分可以直接跳过
    // 此外，还可以参考/app/src/test/java/schedule_system/文件夹内其他的测试。
    // 测试用例尽量多而全，覆盖到所有可能情况。
    // 写完测试，运行./gradlew test跑一下看看效果。

    /**
     * 判断本课程是否与另一课程信息冲突
     * 
     * 1. 名称冲突
     * 2. 同一时间占用同一地点
     * （用于判断新建课程是否与其他课程冲突）
     * TODO: 现在先不管考试时间，只检查上课时间
     *
     * @param Course course
     * @return boolean
     */
    public boolean conflictsWith(Course course) {
        // TODO 3 : TEST ME
        boolean haveConflict = false;
        if (this.getName().equals(course.getName())) {
            // 若两门课程名称一样，则冲突
            haveConflict = true;
        }
        if (this.classTime.overlaps(course.getClassTime()) && weekOverlaps(course)
                && this.location.equals(course.location)) {
            // 若两门课程在同一时间占用同一地点，则冲突
            haveConflict = true;
        }
        return haveConflict;
    }

    /**
     * 判断是否与另一门课在时间上有冲突
     * （用于判断新课程与某一位学生的已有课程冲突与否）
     *
     * @param Course course
     * @return boolean
     */
    public boolean timeOverlapsWith(Course course) {
        // TODO 2 : TEST ME
        boolean res = this.classTime.overlaps(course.getClassTime()) && weekOverlaps(course);
        return res;
    }

    /**
     * 判断是否与另一门课有重叠的上课周
     *
     * @param b
     * @return
     */
    private boolean weekOverlaps(Course b) {
        // TODO 1 : TEST ME
        boolean res = false;
        if (this.startWeek < b.startWeek) {
            res = this.endWeek >= b.startWeek;
        } else if (b.startWeek < this.startWeek) {
            res = b.endWeek >= b.startWeek;
        } else {
            res = true;
        }
        return res;
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
