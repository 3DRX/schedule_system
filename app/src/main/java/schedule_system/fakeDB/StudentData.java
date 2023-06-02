package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.Activity;
import schedule_system.utils.BitMap;
import schedule_system.utils.ClassTime;
import schedule_system.utils.Course;
import schedule_system.utils.KMap;
import schedule_system.utils.Student;

/**
 * 学生数据控制
 * 与 {@link UserData} 的区别是，这个类是用来控制学生的课程、课外活动、临时事务信息。
 * 操作一组 {@link Student} 对象 {@link #students}
 * 和一组 {@link BitMap} 对象 {@link #schedules}，存储学生的日程安排信息。
 */
public class StudentData {
    final private String path = "src/main/resources/studentThings.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Logger logger = LoggerFactory.getLogger(StudentData.class);

    private KMap<String, Student> students;
    private KMap<String, BitMap> schedules;

    @Autowired
    CourseData courseData;
    @Autowired
    ActivityData activityData;
    @Autowired
    EventData eventData;

    /**
     * 从文件读取学生的时间安排信息，并分别为每个学生建立时间安排的 {@link BitMap} 对象
     */
    public StudentData() {
        this.students = new KMap<>();
        this.schedules = new KMap<>();
        Arrays.stream(readStudentClasses())
                .forEach((e) -> {
                    this.schedules.put(e.getName(), generateSchedule(e));
                    this.students.put(e.getName(), e);
                });
    }

    /**
     * @return 所有学生
     */
    private Student[] allStudents() {
        return Arrays.stream(this.students.getKeyArray(String.class))
                .map(i -> this.students.get(i))
                .toArray(size -> new Student[size]);
    }

    /**
     * 删除学生
     * 在删除学生之前，需要先删除学生的全部课程、课外活动、临时事务信息
     * 
     * @param name 学生姓名
     * @return 是否成功删除
     */
    public boolean removeStudent(String name) {
        Student student = this.students.get(name);
        if (student == null) {
            logger.warn("删除学生 " + name + " 失败，学生不存在");
            return false;
        }
        for (String event : student.getEvents()) {
            deleteEventFromStudent(event, name);
        }
        logger.info("删除学生 " + student.getName() + " 的全部临时事物");
        for (String activity : student.getActivities()) {
            deleteActivityFromStudet(activity, name);
        }
        logger.info("删除学生 " + student.getName() + " 的全部课外活动");
        this.students.remove(name);
        this.schedules.remove(name);
        return writeStudentThings(allStudents());
    }

    /**
     * 添加学生，并将学生的时间安排信息写入文件
     * 
     * @param name 学生姓名
     * @return 添加学生是否成功
     */
    public boolean addStudent(String name) {
        this.students.put(name, new Student(name));
        this.schedules.put(name, new BitMap(ClassTime.getMaxIndex()));
        return writeStudentThings(allStudents());
    }

    /**
     * 获取学生的时间安排信息
     * 
     * @param studentName 学生姓名
     * @return 学生的时间安排 {@link BitMap} 对象
     */
    public BitMap getScheduleOf(String studentName) {
        return this.schedules.get(studentName);
    }

    /**
     * 获取学生在指定时间的课程
     * 
     * @param studentName 学生姓名
     * @param week        周数
     * @param day         星期几
     * @param time        时间
     * @return 学生在指定时间的课程
     */
    public Course courseAt(String studentName, int week, int day, int time) {
        int timeIndex = ClassTime.realTimeToIndex(week, day, time);
        return Arrays.stream(students.get(studentName).getCourses())
                .map(courseData::getCourseByName)
                .filter(course -> course != null)
                .filter(course -> course.getOccupiedTime().get(timeIndex))
                .findAny()
                .orElse(null);
    }

    /**
     * 获取学生在指定时间的课外活动
     * 
     * @param studentName 学生姓名
     * @param week        周数
     * @param day         星期几
     * @param time        时间
     * @return 学生在指定时间的课外活动
     */
    public Activity activityAt(String studentName, int week, int day, int time) {
        int timeIndex = ClassTime.realTimeToIndex(week, day, time);
        return Arrays.stream(students.get(studentName).getActivities())
                .map(activityData::getActivityByName)
                .filter(activity -> activity != null)
                .filter(activity -> activity.takesPlaceAt(timeIndex))
                .findAny()
                .orElse(null);
    }

    /**
     * 获取学生在指定时间是否空闲
     * 
     * @param studentName 学生姓名
     * @param week        周数
     * @param day         星期几
     * @param time        时间
     * @return 学生在指定时间是否空闲
     */
    public boolean isOccupied(String studentName, int week, int day, int time) {
        BitMap occupiedTime = this.schedules.get(studentName);
        if (occupiedTime == null) {
            return false;
        }
        return occupiedTime.get(ClassTime.realTimeToIndex(week, day, time));
    }

    /**
     * 获取学生在指定时间是否空闲
     * 
     * @param studentName 学生姓名
     * @param index       时间索引
     * @return 学生在指定时间是否空闲
     */
    public boolean isOccupied(String studentName, int index) {
        BitMap occupiedTime = this.schedules.get(studentName);
        if (occupiedTime == null) {
            return false;
        }
        return occupiedTime.get(index);
    }

    /**
     * 生成学生的时间安排
     * 
     * @param student 学生对象
     * @return 生成学生的时间安排 {@link BitMap} 对象
     */
    private BitMap generateSchedule(Student student) {
        BitMap res = new BitMap(ClassTime.getMaxIndex());
        // get all occupied time slot
        CourseData courseData = new CourseData();
        ActivityData activityData = new ActivityData();
        for (String courseName : student.getCourses()) {
            Course course = courseData.getCourseByName(courseName);
            if (course == null) {
                continue;
            }
            res = res.or(course.getOccupiedTime());
        }
        for (String activityName : student.getActivities()) {
            Activity activity = activityData.getActivityByName(activityName);
            if (activity == null) {
                continue;
            }
            res = res.or(activity.getOccupiedTime());
        }
        return res;
    }

    /**
     * @return 所有学生的数组
     */
    public Student[] getStudentsArray() {
        return Arrays.stream(this.students.getKeyArray(String.class))
                .map(i -> this.students.get(i))
                .toArray(size -> new Student[size]);
    }

    /**
     * 按照学生姓名获取学生对象
     * 
     * @param id 学生姓名
     * @return 学生对象
     */
    public Student getStudentById(String id) {
        return this.students.get(id);
    }

    /**
     * 在所有学生的课表中删除指定课程
     * 
     * @param courseName 课程名称
     * @return 更新删除结果是否成功
     */
    public boolean deleteCourseFromStudents(String courseName) {
        for (Student student : this.getStudentsArray()) {
            student.deleteCourseIfHave(courseName);
            // unset occupied time
            BitMap schedule = this.schedules.get(student.getName());
            schedule = schedule.and(courseData.getCourseByName(courseName).getOccupiedTime().not());
            this.schedules.put(student.getName(), schedule);
        }
        return writeStudentThings(this.getStudentsArray());
    }

    /**
     * 在指定学生的安排中删除指定活动
     * 
     * @param activityName 活动名称
     * @param studentName  学生姓名
     * @return 更新删除结果是否成功
     */
    public boolean deleteActivityFromStudet(String activityName, String studentName) {
        BitMap occupiedTime = activityData.getActivityByName(activityName).getOccupiedTime();
        this.activityData.removeParticipantOf(activityName, studentName);
        Student student = this.students.get(studentName);
        student.deleteActivityIfHave(activityName);
        // unset occupied time
        BitMap schedule = this.schedules.get(student.getName());
        schedule = schedule.and(occupiedTime.not());
        this.schedules.put(student.getName(), schedule);
        return writeStudentThings(this.getStudentsArray());
    }

    /**
     * 为指定学生添加一个临时事务
     * 
     * @param newEventName 新的临时事务名称
     * @param studentName  学生姓名
     * @return 更新添加结果是否成功
     */
    public boolean addEventToStudent(String newEventName, String studentName) {
        // check if student already have this event
        for (String eventName : this.students.get(studentName).getEvents()) {
            if (eventName.equals(newEventName)) {
                logger.warn("为学生 " + studentName + " 添加事件 " + newEventName + " 失败：学生已有该事件");
                return false;
            }
        }
        this.students.get(studentName).addEvent(newEventName);
        return writeStudentThings(this.getStudentsArray());
    }

    /**
     * 为指定学生删除一个临时事务
     * 
     * @param eventName   临时事务名称
     * @param studentName 学生姓名
     * @return 更新删除结果是否成功
     */
    public boolean deleteEventFromStudent(String eventName, String studentName) {
        this.students.get(studentName).deleteEvent(eventName);
        eventData.deleteEvent(eventName + "," + studentName);
        return writeStudentThings(this.getStudentsArray());
    }

    /**
     * 为一组学生添加一个课外活动
     * 
     * @param activityName 活动名称
     * @param students     学生姓名数组
     * @return 更新添加结果是否成功
     */
    public boolean addActivityToStudents(String activityName, String[] students) {
        for (String studentName : students) {
            Student student = this.students.get(studentName);
            if (students == null) {
                continue;
            }
            String[] studentActivities = student.getActivities();
            Activity newActivity = activityData.getActivityByName(activityName);
            if (newActivity == null) {
                logger.warn("为学生 " + studentName + " 添加课外活动" + activityName + "失败：该课外活动不存在");
            }
            for (int i = 0; i < studentActivities.length; i++) {
                Activity loopActivity = activityData.getActivityByName(studentActivities[i]);
                if (loopActivity.timeOverlapsWith(newActivity)) {
                    logger.warn("为学生" + student.getName() + "添加课外活动"
                            + activityName + "失败：与学生已有课外活动："
                            + studentActivities[i] + "冲突");
                    return false;
                }
            }
            student.addActivity(activityName);
        }
        return writeStudentThings(this.getStudentsArray());
    }

    /**
     * 为一组学生添加一个课程
     * 
     * @param newCourseName 新的课程名称
     * @param students      学生姓名数组
     * @return 更新添加结果是否成功
     */
    public boolean addCourseToStudents(String newCourseName, String[] students) {
        for (String studentName : students) {
            Student student = this.students.get(studentName);
            if (student == null) {
                continue;
            }
            Course newCourse = courseData.getCourseByName(newCourseName);
            if (newCourse == null) {
                logger.warn("为学生 " + studentName + " 添加课程" + newCourseName + "失败：课程不存在");
                return false;
            }
            if (this.getScheduleOf(studentName).overlaps(newCourse.getOccupiedTime())) {
                logger.warn("为学生" + student.getName() + "添加课程"
                        + newCourseName + "失败：与学生已有课程冲突");
                return false;
            }
            student.addCourse(newCourseName);
            this.schedules.get(studentName).or(newCourse.getOccupiedTime());
        }
        return writeStudentThings(this.getStudentsArray());
    }

    /**
     * @param userName 学生姓名
     * @return 是否存在该学生
     */
    public boolean isStudent(String userName) {
        return this.students.containKey(userName);
    }

    /**
     * @return 所有学生的数组
     */
    private Student[] readStudentClasses() {
        Student[] readStudent = {};
        try {
            JsonReader reader = new JsonReader(new FileReader(path));
            readStudent = new Gson().fromJson(reader, Student[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readStudent;
    }

    /**
     * 将学生数组写入文件，更新学生时间占用 {@link BitMap}
     * 
     * @param students 学生数组
     * @return 是否成功写入文件
     */
    public boolean writeStudentThings(Student[] students) {
        File file = new File(path);
        String res = gson.toJson(students);
        boolean successFlag = true;
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(res);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            successFlag = false;
            e.printStackTrace();
        }
        // 重新读取一遍学生信息，更新学生时间占用 BitMap
        Arrays.stream(readStudentClasses())
                .forEach((e) -> {
                    this.schedules.put(e.getName(), generateSchedule(e));
                    this.students.put(e.getName(), e);
                });
        return successFlag;
    }
}
