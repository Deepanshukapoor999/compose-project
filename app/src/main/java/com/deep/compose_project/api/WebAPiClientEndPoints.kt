package com.deep.compose_project.api

import com.deep.compose_project.model.AllTaskShowModel
import com.deep.compose_project.model.TaskIdByModel
import com.deep.compose_project.model.UserTotalTaskModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WebAPiClientEndPoints {
    @GET("getUserTotalTasks")
    suspend fun getUserTotalTasks(
        @Query("token") token: String,
        @Query("type") type: String,
        @Query("month") month: Int,
        @Query("user_id") userId: Int,
        @Query("year") year: Int
    ): UserTotalTaskModel


//    https://demo.krestsolutions.in/krestioc/index.php/webservice/getAllTasksToUserDateTimeWise?year=2026&date=2026-9-2&user_id=6&month=9&user_type=4&token=3411a09f060a63f51f2f172bff2fe61d&timezone=Asia%252FKolkata&isMyTask=2&searchByDeparment=&searchBySubCompany=
//
//    https://demo.krestsolutions.in/krestioc/index.php/webservice/getAllTasksToUserDateTimeWise?year=2026&date=2026-9-5&user_id=6&month=9&user_type=4&token=3411a09f060a63f51f2f172bff2fe61d&timezone=Asia%2FKolkata&isMyTask=2&searchByDeparment=&searchBySubCompany=
    @GET("getAllTasksToUserDateTimeWise")
    suspend fun getAllTasksToUserDateTimeWise(
        @Query("year") year: String,
        @Query("date") date: String,
        @Query("user_id") user_id: String,
        @Query("month") month: String,
        @Query("user_type") user_type: String,
        @Query("token") token: String,
        @Query("timezone") timezone: String,
        @Query("status") status: String,
        @Query("isMyTask") isMyTask: String,
        @Query("searchByDeparment") searchByDeparment: String,
        @Query("searchBySubCompany") searchBySubCompany: String
    ): AllTaskShowModel


//    https://demo.krestsolutions.in/krestioc/index.php/webservice/getTaskById?task_id=1264&timezone=Asia%2FKolkata&user_id=6&token=bc099af3a32213b23be7d79a182a4beb
    @GET("getTaskById")
    suspend fun getTaskById(
        @Query("task_id") task_id: String,
        @Query("timezone") timezone: String,
        @Query("user_id") userId: String,
        @Query("token") token: String,
    ): TaskIdByModel

}