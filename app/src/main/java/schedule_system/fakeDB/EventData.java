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

public class EventData {
    final private String path = "src/main/resources/events.json";
    final private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private KMap<String, Event> events;

    public EventData() {
        this.events = new KMap<>();
        Arrays.stream(readEvents())
                .filter(e -> e != null)
                .forEach(e -> this.events.put(
                        e.getName() + "," + e.getPerson(),
                        e));
    }

    /**
     * @param eventName <name>,<person>
     * @return
     */
    public Event getEventByName(String eventName) {
        return this.events.get(eventName);
    }

    public Event[] getEventByDay(int week, int day) {
        return Arrays.stream(this.events.getKeyArray(String.class))
                .map(i -> this.events.get(i))
                .filter(i -> i.getTime().getWeek() == week
                        && i.getTime().getDay() == day)
                .toArray(size -> new Event[size]);
    }

    public Event[] allEvents() {
        return Arrays
                .stream(this.events.getKeyArray(
                        String.class))
                .map(i -> this.events.get(i))
                .toArray(size -> new Event[size]);
    }

    public boolean deleteEvent(String eventName) {
        this.events.remove(eventName);
        return writeEvents(allEvents());
    }

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
     * @param eventName <name>,<person>
     * @return
     */
    public boolean containsEvent(String eventName) {
        return this.events.containKey(eventName);
    }

    /**
     * 从文件中读取临时事物列表
     * 
     * @return
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
