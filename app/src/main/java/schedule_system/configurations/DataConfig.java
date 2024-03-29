package schedule_system.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import schedule_system.fakeDB.ActivityData;
import schedule_system.fakeDB.CourseData;
import schedule_system.fakeDB.EventData;
import schedule_system.fakeDB.MapData;
import schedule_system.fakeDB.StudentData;
import schedule_system.fakeDB.UserData;

/**
 * DataConfig
 */
@Configuration
public class DataConfig {

    @Bean
    public UserData userData() {
        return new UserData();
    }

    @Bean
    public CourseData courseData() {
        return new CourseData();
    }

    @Bean
    public ActivityData activityData() {
        return new ActivityData();
    }

    @Bean
    public EventData eventData() {
        return new EventData();
    }

    @Bean
    public StudentData studentData() {
        return new StudentData();
    }

    @Bean
    public MapData mapData() {
        return new MapData();
    }
}
