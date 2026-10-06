package com.deep.compose_project.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deep.compose_project.states.AllTaskByIDUiState
import com.deep.compose_project.states.AllTaskUiState
import com.deep.compose_project.states.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class TaskViewModel : ViewModel() {

    private val repository = TaskRepository()

    private val _uiState = MutableStateFlow<TaskUiState>(TaskUiState.Loading)
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()


    private val _uiAllTaskState = MutableStateFlow<AllTaskUiState>(AllTaskUiState.Loading)
    val uiAllTaskState: StateFlow<AllTaskUiState> = _uiAllTaskState.asStateFlow()


    val _uiTaskByIdState =  MutableStateFlow<AllTaskByIDUiState>(AllTaskByIDUiState.Loading)
    val uiTaskByIdState: StateFlow<AllTaskByIDUiState> = _uiTaskByIdState.asStateFlow()


    fun getUserTotalTasks(
        token: String,
        type: String,
        month: Int,
        userId: Int,
        year: Int
    ) {

        viewModelScope.launch {

            _uiState.value = TaskUiState.Loading

            try {

                val response = repository.getUserTotalTasks(
                    token = token,
                    type = type,
                    month = month,
                    userId = userId,
                    year = year
                )

                if (response.status.equals("true")) {

                    _uiState.value =
                        TaskUiState.Success(response)

                } else {

                    _uiState.value =
                        TaskUiState.Error(response.message)
                }

            } catch (e: Exception) {

                _uiState.value =
                    TaskUiState.Error(
                        e.message ?: "Something went wrong"
                    )
            }
        }
    }




    fun getAllTasksToUserDateTimeWise(
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
    ) {

        viewModelScope.launch {

            _uiAllTaskState.value = AllTaskUiState.Loading

            try {

                val response = repository.getAllTasksToUserDateTimeWise(
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

                if (response.status.equals("true")) {

                    _uiAllTaskState.value =
                        AllTaskUiState.Success(response)

                } else {

                    _uiAllTaskState.value =
                        AllTaskUiState.Error(response.message)
                }

            } catch (e: Exception) {

                _uiAllTaskState.value =
                    AllTaskUiState.Error(
                        e.message ?: "Something went wrong"
                    )
            }
        }
    }




    fun getTaskById(
        task_id: String,
        timezone: String,
        user_id: String,
        token: String
    ) {
        viewModelScope.launch {

            _uiTaskByIdState.value = AllTaskByIDUiState.Loading

            try {

                val response = repository.getTaskById(
                    task_id = task_id,
                    timezone = timezone,
                    user_id = user_id,
                    token = token
                )

                if (response.status == "true") {

                    _uiTaskByIdState.value =
                        AllTaskByIDUiState.Success(response)

                } else {

                    _uiTaskByIdState.value =
                        AllTaskByIDUiState.Error(
                            response.message ?: "Something went wrong"
                        )
                }

            } catch (e: Exception) {

                _uiTaskByIdState.value =
                    AllTaskByIDUiState.Error(
                        e.message ?: "Something went wrong"
                    )
            }
        }
    }

}