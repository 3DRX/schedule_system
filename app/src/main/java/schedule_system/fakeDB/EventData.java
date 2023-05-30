package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.Event;
import schedule_system.utils.KMap;

/**
 * 临时事物控制
 * 操作一组 {@link Event} 对象 {@link #events}
 */
public class EventData {
    final private String path = "src/main/resources/events.json";
    final private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private KMap<String, Event> events;

    /**
     * 从文件读取临时事物信息
     */
    public EventData() {
        this.events = new KMap<>();
        Arrays.stream(readEvents())
                .filter(e -> e != null)
                .forEach(e -> this.events.put(
                        e.getName() + "," + e.getPerson(),
                        e));
    }

    /**
     * 按名称查找临时事务
     * 
     * @param eventName 临时事务名称，格式为"name,person"
     * @return 临时事务
     */
    public Event getEventByName(String eventName) {
        return this.events.get(eventName);
    }

    /**
     * 按周数和星期查找临时事务
     * 
     * @param week 周数
     * @param day  星期
     * @return 该周该天的所有临时事务
     */
    public Event[] getEventByDay(int week, int day) {
        return Arrays.stream(this.events.getKeyArray(String.class))
                .map(i -> this.events.get(i))
                .filter(i -> i.getTime().getWeek() == week
                        && i.getTime().getDay() == day)
                .toArray(size -> new Event[size]);
    }

    /**
     * @return 所有临时事务
     */
    public Event[] allEvents() {
        return Arrays
                .stream(this.events.getKeyArray(
                        String.class))
                .map(i -> this.events.get(i))
                .toArray(size -> new Event[size]);
    }

    /**
     * 删除临时事务，并将结果写入文件
     * 
     * @param eventName 临时事物名称，格式为"name,person"
     * @return 是否成功删除
     */
    public boolean deleteEvent(String eventName) {
        this.events.remove(eventName);
        return writeEvents(allEvents());
    }

    /**
     * 添加临时事务，并将结果写入文件
     * 
     * @param newEvent 新的临时事务
     * @return 是否成功添加
     */
    public boolean addEvent(Event newEvent) {
        String key = newEvent.getName()
                + "," + newEvent.getPerson();
        if (this.events.containKey(key)) {
            return false;
        }
        this.events.put(key, newEvent);
        return writeEvents(allEvents());
    }

    /**
     * @param eventName 临时事务名称，格式为"name,person"
     * @return 是否包含该临时事务
     */
    public boolean containsEvent(String eventName) {
        return this.events.containKey(eventName);
    }

    /**
     * 从文件中读取临时事物列表
     * 
     * @return 临时事物列表
     */
    private Event[] readEvents() {
        try {
            return gson.fromJson(
                    new JsonReader(new FileReader(path)),
                    Event[].class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将临时事物列表写入文件
     * 
     * @param events 临时事物列表
     * @return 是否成功写入文件
     */
    private boolean writeEvents(Event[] events) {
        try {
            FileWriter writer = new FileWriter(new File(path));
            writer.write(gson.toJson(events));
            writer.flush();
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
