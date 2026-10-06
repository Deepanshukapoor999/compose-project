package com.deep.compose_project.api

import com.deep.compose_project.model.AllTaskShowModel
import com.deep.compose_project.model.TaskIdByModel
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
        status: String,
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
            status = status,
            isMyTask = isMyTask,
            searchByDeparment = searchByDeparment,
            searchBySubCompany = searchBySubCompany
        )
    }



    suspend fun getTaskById(
        task_id: String,
        timezone: String,
        user_id: String,
        token: String
    ): TaskIdByModel {

        return api.getTaskById(
            task_id = task_id,
            timezone = timezone,
            userId = user_id,
            token = token
        )
    }
}