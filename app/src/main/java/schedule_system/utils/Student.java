package schedule_system.utils;

public class Student {
    private String[] courses;
    private String[] events;
    private String[] activities;
    private String name;

    public Student(String name) {
        this.courses = null;
        this.events = null;
        this.activities = null;
        this.name = name;
    }

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

    public String[] getCourses() {
        return courses;
    }

    public String[] getActivities() {
        return activities;
    }

    public String getName() {
        return name;
    }

    public String[] getEvents() {
        return events;
    }

    public void addEvent(String eventName) {
        String[] newEvents = new String[this.events.length + 1];
        for (int i = 0; i < this.events.length; i++) {
            newEvents[i] = this.events[i];
        }
        newEvents[this.events.length] = eventName;
        this.events = newEvents.clone();
    }

    public void deleteEvent(String eventName) {
        KList<String> newEvents = new KList<>(String.class);
        for (String event : this.events) {
            if (!event.equals(eventName)) {
                newEvents.add(event);
            }
        }
        this.events = newEvents.toArray();
    }

    public void addCourse(String courseName) {
        String[] newCourses = new String[this.courses.length + 1];
        for (int i = 0; i < this.courses.length; i++) {
            newCourses[i] = this.courses[i];
        }
        newCourses[this.courses.length] = courseName;
        this.courses = newCourses.clone();
    }
}
