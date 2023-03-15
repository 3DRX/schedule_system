package schedule_system.records;

import schedule_system.utils.Course;

/**
 * AddCourseRecord
 */
public record CourseInfoRecord(Course course, String[] students) {
}
