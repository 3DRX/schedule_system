package schedule_system.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import schedule_system.CourseData;
import schedule_system.StudentData;
import schedule_system.UserData;

/**
 * 初始化三个文件读写器
 * DataConfig
 */
@Configuration
public class DataConfig {

    @Bean
    public UserData userData() {
        return new UserData();
    }

    @Bean
    public StudentData studentData() {
        return new StudentData();
    }

    @Bean
    public CourseData courseData() {
        return new CourseData();
    }
}
