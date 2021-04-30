package com.codingproject.nationalparktool.adapterdata;

public class TourGuideItem {

    private String name;
    private String description;
    private String duration;
    private String stops;

    // constructor
    public TourGuideItem(String name, String description, String duration, String stops) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.stops = stops;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public String getStops() {
        return stops;
    }
}
