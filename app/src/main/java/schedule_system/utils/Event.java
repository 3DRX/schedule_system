package schedule_system.utils;

import org.springframework.beans.factory.annotation.Autowired;

import schedule_system.fakeDB.MapData;

public class Event {
    private String name;
    private EventTime time;
    private String location;
    private String person;

    public Event(String name, EventTime time, String location, String person) {
        // check input
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("输入不合法");
        }
        if (time == null) {
            throw new IllegalArgumentException("输入不合法");
        }
        if (location == null || location.length() == 0) {
            throw new IllegalArgumentException("输入不合法");
        }
        if (person == null || person.length() == 0) {
            throw new IllegalArgumentException("输入不合法");
        }
        this.name = name;
        this.time = time;
        this.location = location;
        this.person = person;
    }

    public boolean equals(Event event) {
        return this.name.equals(event.getName())
                && this.time.equals(event.getTime())
                && this.location.equals(event.getLocationName())
                && this.person.equals(event.getPerson());
    }

    public String getName() {
        return name;
    }

    public boolean takesPlaceAt(int index) {
        return this.time.toIndex() == index;
    }

    public EventTime getTime() {
        return time;
    }

    public String getLocationName() {
        return location;
    }

    public String getPerson() {
        return person;
    }
}
