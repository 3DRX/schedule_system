package schedule_system.utils;

/**
 * 临时事物
 *
 * {@link #name} 事物名称
 * {@link #time} 事物时间
 * {@link #location} 事物地点
 * {@link #person} 事物人物
 */
public class Event {
    private String name;
    private EventTime time;
    private String location;
    private String person;

    /**
     * @param name     事物名称
     * @param time     事物时间
     * @param location 事物地点
     * @param person   事物人物
     */
    public Event(String name, EventTime time, String location, String person) {
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

    /**
     * @param event 事物
     * @return 两个事物是否相等
     */
    public boolean equals(Event event) {
        return this.name.equals(event.getName())
                && this.time.equals(event.getTime())
                && this.location.equals(event.getLocationName())
                && this.person.equals(event.getPerson());
    }

    /**
     * @return 事物名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param index 时间索引
     * @return 事物是否在该时间发生
     */
    public boolean takesPlaceAt(int index) {
        return this.time.toIndex() == index;
    }

    /**
     * @return 事物时间
     */
    public EventTime getTime() {
        return time;
    }

    /**
     * @return 事物地点
     */
    public String getLocationName() {
        return location;
    }

    /**
     * @return 事物人物
     */
    public String getPerson() {
        return person;
    }
}
