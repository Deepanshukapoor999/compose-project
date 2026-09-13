package com.deep.compose_project.api

import com.deep.compose_project.model.AllTaskShowModel
import com.deep.compose_project.model.UserTotalTaskModel

class TaskRepository {

    private val api = RetrofitClient.api

    suspend fun getUserTotalTasks(
        token: String,
        type: String,
        month: Int,
        userId: Int,
        year: Int
    ): UserTotalTaskModel {

        return api.getUserTotalTasks(
            token = token,
            type = type,
            month = month,
            userId = userId,
            year = year
        )
    }

    suspend fun getAllTasksToUserDateTimeWise(
        year: String,
        date: String,
        user_id: String,
        month: String,
        user_type: String,
        token: String,
        timezone: String,
        isMyTask: String,
        searchByDeparment: String,
        searchBySubCompany: String
    ): AllTaskShowModel {

        return api.getAllTasksToUserDateTimeWise(
            year = year,
            date = date,
            user_id = user_id,
            month = month,
            user_type = user_type,
            token = token,
            timezone = timezone,
            isMyTask = isMyTask,
            searchByDeparment = searchByDeparment,
            searchBySubCompany = searchBySubCompany
        )
    }
}