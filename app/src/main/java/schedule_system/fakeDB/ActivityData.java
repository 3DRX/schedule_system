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

public class ActivityData {
    final private String path = "src/main/resources/activities.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private KMap<String, Activity> activities;

    public ActivityData() {
        this.activities = new KMap<>();
        Arrays.stream(readActivities())
                .forEach(e -> this.activities.put(e.getName(), e));
    }

    public Activity[] allActivities() {
        return Arrays.stream(this.activities.getKeyArray(String.class))
                .map(i -> this.activities.get(i))
                .toArray(Activity[]::new);
    }

    public Activity getActivityByName(String name) {
        return this.activities.get(name);
    }

    public boolean isActivityExist(String name) {
        return this.activities.containKey(name);
    }

    public boolean deleteActivity(String activityName) {
        this.activities.remove(activityName);
        return writeActivities(this.allActivities());
    }

    public Activity[] getActivityByDay(int week, int day) {
        return Arrays.stream(this.activities.getKeyArray(String.class))
                .map(i -> this.activities.get(i))
                .filter(i -> i.getStartWeek() <= week
                        && i.getEndWeek() >= week
                        && i.getTime().getDay() == day)
                .toArray(Activity[]::new);
    }

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

    public boolean addActivity(Activity activity) {
        if (this.activities.containKey(activity.getName())) {
            return false;
        }
        this.activities.put(activity.getName(), activity);
        return writeActivities(this.allActivities());
    }

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
