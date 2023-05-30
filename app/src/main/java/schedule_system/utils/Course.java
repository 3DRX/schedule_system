package schedule_system.utils;

/**
 * 课程
 *
 * {@link #startWeek} 课程开始周
 * {@link #endWeek} 课程结束周
 * {@link #testWeek} 课程考试周
 * {@link #classTime} 每周的上课时间
 * {@link #examTime} 考试时间
 * {@link #name} 课程名称（唯一id）
 * {@link #location} 课程地点
 */
public class Course {

    private int startWeek; // 课程开始周
    private int endWeek; // 课程结束周
    private int testWeek; // 课程考试周
    private ClassTime classTime; // 每周的上课时间
    private ClassTime examTime; // 考试时间
    private String name; // 课程名称（唯一id）
    private String location; // 课程地点

    /**
     * 检查输入规则如下：
     * 1. 一学期共有20周（即课程或考试不能>20）
     * 2. 课程结束周必须大于等于课程开始周
     * 3. 课程考试周必须大于课程结束周
     * 4. 课程的上课时间和考试时间须合法
     * 
     * @param startWeek 课程开始周
     * @param endWeek   课程结束周
     * @param testWeek  课程考试周
     * @param classTime 每周的上课时间
     * @param examTime  考试时间
     * @param name      课程名称（唯一id）
     * @param location  课程地点
     */
    public Course(
            final int startWeek,
            final int endWeek,
            final int testWeek,
            final ClassTime classTime,
            final ClassTime examTime,
            final String name,
            final String location) {
        int weekInSemester = SystemTime.getWeekInSemester();
        if (endWeek > weekInSemester || startWeek > weekInSemester || testWeek > weekInSemester)
            throw new IllegalArgumentException("学期周数要小于等于20");
        if (startWeek > endWeek)
            throw new IllegalArgumentException("课程结束周必须大于等于课程开始周");
        if (testWeek <= endWeek)
            throw new IllegalArgumentException("课程考试周必须大于课程结束周");
        if (endWeek <= 0 || startWeek <= 0 || testWeek <= 0)
            throw new IllegalArgumentException("周数不能是负数");
        setStartWeek(startWeek);
        setEndWeek(endWeek);
        setTestWeek(testWeek);
        setClassTime(classTime);
        setExamTime(examTime);
        setName(name);
        setLocation(location);
    }

    /**
     * 获得该课程所占用所有时间的BitMap
     * 
     * @return occupiedTime
     */
    public BitMap getOccupiedTime() {
        BitMap occupiedTime = new BitMap(ClassTime.getMaxIndex());
        for (int week = startWeek; week <= endWeek; week++) {
            int day = classTime.getDay();
            int time = classTime.getTime();
            for (int j = 0; j < classTime.getDuration(); j++) {
                occupiedTime.set(ClassTime.realTimeToIndex(week, day, time + j));
            }
        }
        return occupiedTime;
    }

    /**
     * 检查是否占用某个时间
     * 
     * @param index
     * @return
     */
    public boolean takesPlaceAt(int index) {
        return getOccupiedTime().get(index);
    }

    /**
     * 检查是否占用某个时间
     * 
     * @param week
     * @param day
     * @param time
     * @return
     */
    public boolean takesPlaceAt(int week, int day, int time) {
        return takesPlaceAt(ClassTime.realTimeToIndex(week, day, time));
    }

    /**
     * 用于模拟时判断 index 对应时间是否有本课程
     * 与 {@link #takesPlaceAt(int)} 不同的是，本方法只在 index 正好对应本课程开始的那一个小时时返回 true
     * 
     * @param index
     * @return
     */
    public boolean atIndex(int index) {
        // parse index into week, day, hour
        int week = (index / ClassTime.getHourInWeek()) + 1;
        int day = ((index % ClassTime.getHourInWeek()) / ClassTime.getHourInDay()) + 1;
        int hour = index % ClassTime.getHourInDay() + ClassTime.firstIndexOfHour();
        boolean res = false;
        // at the correct week
        if (week >= this.startWeek && week <= this.endWeek) {
            if (this.classTime.getDay() == day &&
                    this.classTime.getTime() == hour) {
                // index 正好对应本课程开始的那一个小时
                res = true;
            }
        }
        return res;
    }

    /**
     * 判断本课程是否与另一课程信息冲突
     * 
     * 1. 名称冲突
     * 2. 同一时间占用同一地点
     * （用于判断新建课程是否与其他课程冲突）
     * 忽略考试时间，只检查上课时间
     *
     * @param Course course
     * @return boolean
     */
    public boolean conflictsWith(Course course) {
        boolean haveConflict = false;
        if (this.getName().equals(course.getName())) {
            // 若两门课程名称一样，则冲突
            haveConflict = true;
        }
        if (this.timeOverlapsWith(course) && this.location.equals(course.location)) {
            // 若两门课程在同一时间占用同一地点，则冲突
            haveConflict = true;
        }
        return haveConflict;
    }

    /**
     * 判断是否与另一门课在时间上有冲突
     * 1. 开始周、结束周有重叠
     * 2. 上课时间有重叠
     *
     * @param Course course
     * @return boolean
     */
    public boolean timeOverlapsWith(Course course) {
        return this.getOccupiedTime().overlaps(course.getOccupiedTime());
    }

    /**
     * @return 课程名称
     */
    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = name;
    }

    /**
     * @return 课程地点名称
     */
    public String getLocationName() {
        return location;
    }

    private void setLocation(final String location) {
        this.location = location;
    }

    /**
     * @return 课程开始周
     */
    public int getStartWeek() {
        return startWeek;
    }

    private void setStartWeek(final int startWeek) {
        this.startWeek = startWeek;
    }

    /**
     * @return 课程结束周
     */
    public int getEndWeek() {
        return endWeek;
    }

    private void setEndWeek(final int endWeek) {
        this.endWeek = endWeek;
    }

    /**
     * @return 课程考试周
     */
    public int getTestWeek() {
        return testWeek;
    }

    private void setTestWeek(final int testWeek) {
        this.testWeek = testWeek;
    }

    /**
     * @return 课程上课时间
     */
    public ClassTime getClassTime() {
        return classTime;
    }

    private void setClassTime(final ClassTime classTime) {
        this.classTime = classTime;
    }

    /**
     * @return 课程考试时间
     */
    public ClassTime getExamTime() {
        return examTime;
    }

    private void setExamTime(final ClassTime examTime) {
        this.examTime = examTime;
    }
}
