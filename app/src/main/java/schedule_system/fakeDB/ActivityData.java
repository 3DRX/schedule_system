package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.Activity;
import schedule_system.utils.KMap;

/**
 * 课外活动控制
 * 操作一组 {@link Activity} 对象
 */
public class ActivityData {
    final private String path = "src/main/resources/activities.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private KMap<String, Activity> activities;

    /**
     * 从文件读取课外活动信息
     */
    public ActivityData() {
        this.activities = new KMap<>();
        Arrays.stream(readActivities())
                .forEach(e -> this.activities.put(e.getName(), e));
    }

    /**
     * @return 所有课外活动
     */
    public Activity[] allActivities() {
        return Arrays.stream(this.activities.getKeyArray(String.class))
                .map(i -> this.activities.get(i))
                .toArray(Activity[]::new);
    }

    /**
     * 删除课外活动的一个参与者
     * 
     * @param activityName 课外活动名称
     * @param studentName  学生名称
     */
    public void removeParticipantOf(String activityName, String studentName) {
        Activity activity = this.activities.get(activityName);
        activity.removeParticipant(studentName);
        if (activity.getParticipants().length == 0) {
            this.activities.remove(activityName);
        } else {
            this.activities.put(activityName, activity);
        }
        writeActivities(this.allActivities());
    }

    /**
     * @param name 课外活动名称
     * @return 课外活动
     */
    public Activity getActivityByName(String name) {
        return this.activities.get(name);
    }

    /**
     * @param name 课外活动名称
     * @return 课外活动是否存在
     */
    public boolean isActivityExist(String name) {
        return this.activities.containKey(name);
    }

    /**
     * 删除课外活动，结束后将结果写入文件
     * 
     * @param activityName 课外活动名称
     * @return 删除课外活动是否成功
     */
    public boolean deleteActivity(String activityName) {
        this.activities.remove(activityName);
        return writeActivities(this.allActivities());
    }

    /**
     * @param week 周数
     * @param day  星期
     * @return 指定周数和星期的所有课外活动
     */
    public Activity[] getActivityByDay(int week, int day) {
        return Arrays.stream(this.activities.getKeyArray(String.class))
                .map(i -> this.activities.get(i))
                .filter(i -> i.getStartWeek() <= week
                        && i.getEndWeek() >= week
                        && i.getTime().getDay() == day)
                .toArray(Activity[]::new);
    }

    /**
     * @return 从文件读取的课外活动信息
     */
    private Activity[] readActivities() {
        Activity[] read_activities = {};
        try {
            JsonReader reader = new JsonReader(new FileReader(path));
            read_activities = new Gson().fromJson(reader, Activity[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return read_activities;
    }

    /**
     * 添加课外活动，添加后将结果写入文件
     * 
     * @param activity 课外活动
     * @return 添加课外活动是否成功
     */
    public boolean addActivity(Activity activity) {
        if (this.activities.containKey(activity.getName())) {
            return false;
        }
        this.activities.put(activity.getName(), activity);
        return writeActivities(this.allActivities());
    }

    /**
     * 将课外活动写入文件
     * 
     * @param activities 课外活动列表
     * @return 将课外活动写入文件是否成功
     */
    private boolean writeActivities(Activity[] activities) {
        File file = new File(path);
        String res = gson.toJson(activities);
        boolean successFlag = true;
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(res);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            successFlag = false;
        }
        return successFlag;
    }
}
