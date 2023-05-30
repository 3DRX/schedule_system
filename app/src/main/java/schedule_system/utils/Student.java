package schedule_system.utils;

/**
 * 学生类
 *
 * {@link #courses} 课程列表
 * {@link #events} 事件列表
 * {@link #activities} 活动列表
 * {@link #name} 学生姓名
 */
public class Student {
    private String[] courses;
    private String[] events;
    private String[] activities;
    private String name;

    /**
     * 创建一个学生，其课程、事件、活动列表为空
     * 
     * @param name 学生姓名
     */
    public Student(String name) {
        this.courses = new String[0];
        this.events = new String[0];
        this.activities = new String[0];
        this.name = name;
    }

    /**
     * 如果学生已经有了这门课程，就将其从学生课程列表删除
     * 
     * @param courseName 课程名称
     */
    public void deleteCourseIfHave(String courseName) {
        KList<String> newCourses = new KList<>(String.class);
        for (String course : this.courses) {
            if (course.equals(courseName)) {
            } else {
                newCourses.add(course);
            }
        }
        this.courses = newCourses.toArray();
    }

    /**
     * 如果学生已经有了这个活动，就将其从学生活动列表删除
     * 
     * @param activityName 活动名称
     */
    public void deleteActivityIfHave(String activityName) {
        KList<String> newActivities = new KList<>(String.class);
        for (String activity : this.activities) {
            if (activity.equals(activityName)) {
            } else {
                newActivities.add(activity);
            }
        }
        this.activities = newActivities.toArray();
    }

    /**
     * @return 学生课程列表
     */
    public String[] getCourses() {
        return courses;
    }

    /**
     * @return 学生活动列表
     */
    public String[] getActivities() {
        return activities;
    }

    /**
     * @return 学生姓名
     */
    public String getName() {
        return name;
    }

    /**
     * @return 学生事件列表
     */
    public String[] getEvents() {
        return events;
    }

    /**
     * 向学生的临时事务列表中添加一个事件
     * 
     * @param eventName 事件名称
     */
    public void addEvent(String eventName) {
        String[] newEvents = new String[this.events.length + 1];
        for (int i = 0; i < this.events.length; i++) {
            newEvents[i] = this.events[i];
        }
        newEvents[this.events.length] = eventName;
        this.events = newEvents.clone();
    }

    /**
     * 从学生的临时事务列表中删除一个事件
     * 
     * @param eventName 事件名称
     */
    public void deleteEvent(String eventName) {
        KList<String> newEvents = new KList<>(String.class);
        for (String event : this.events) {
            if (!event.equals(eventName)) {
                newEvents.add(event);
            }
        }
        this.events = newEvents.toArray();
    }

    /**
     * 向学生的课程列表中添加一个课程
     * 
     * @param courseName 课程名称
     */
    public void addCourse(String courseName) {
        String[] newCourses = new String[this.courses.length + 1];
        for (int i = 0; i < this.courses.length; i++) {
            newCourses[i] = this.courses[i];
        }
        newCourses[this.courses.length] = courseName;
        this.courses = newCourses.clone();
    }

    /**
     * 向学生的活动列表中添加一个活动
     * 
     * @param activityName 活动名称
     */
    public void addActivity(String activityName) {
        String[] newActivities = new String[this.activities.length + 1];
        for (int i = 0; i < this.activities.length; i++) {
            newActivities[i] = this.activities[i];
        }
        newActivities[this.activities.length] = activityName;
        this.activities = newActivities.clone();
    }
}
