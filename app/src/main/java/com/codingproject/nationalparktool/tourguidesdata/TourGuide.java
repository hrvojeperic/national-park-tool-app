package com.codingproject.nationalparktool.tourguidesdata;

import java.util.List;

public class TourGuide {

    String title;
    String description;
    String durationMin;
    String durationMax;
    String durationUnit;
    List<TourStops> stops;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDurationMin() {
        return durationMin;
    }

    public String getDurationMax() {
        return durationMax;
    }

    public String getDurationUnit() {
        return durationUnit;
    }
    public List<TourStops> getStops() {
        return stops;
    }

}
