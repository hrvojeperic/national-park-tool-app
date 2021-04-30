package com.codingproject.nationalparktool.api;

import com.codingproject.nationalparktool.activitiesdata.Activities;
import com.codingproject.nationalparktool.historydata.History;
import com.codingproject.nationalparktool.longlatdata.LongLatData;
import com.codingproject.nationalparktool.moreinfodata.MoreInfo;
import com.codingproject.nationalparktool.parklistdata.ParkArray;
import com.codingproject.nationalparktool.servicesdata.ServicesData;
import com.codingproject.nationalparktool.tourguidesdata.TourGuidesData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NationalParkServiceApi {

    @GET("parks")
    Call<ParkArray> getParks(@Query("limit") String limit, @Query("api_key") String api_key);

    @GET("parks")
    Call<MoreInfo> getHours(@Query("parkCode") String parkCode, @Query("api_key") String api_key);

    @GET("parks")
    Call<History> getHistory(@Query("parkCode") String parkCode, @Query("api_key") String api_key);

    @GET("parks")
    Call<Activities> getActivities(@Query("parkCode") String parkCode, @Query("api_key") String api_key);

    @GET("amenities/parksplaces")
    Call<ServicesData> getServices(@Query("parkCode") String parkCode, @Query("api_key") String api_key);

    @GET("tours")
    Call<TourGuidesData> getTourGuides(@Query("parkCode") String parkCode, @Query("api_key") String api_key);

    @GET("parks")
    Call<LongLatData> getLongLat(@Query("parkCode") String parkCode, @Query("api_key") String api_key);

}
