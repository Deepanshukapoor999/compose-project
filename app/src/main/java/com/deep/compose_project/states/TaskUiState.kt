package com.deep.compose_project.states

import com.deep.compose_project.model.UserTotalTaskModel

sealed class TaskUiState {

    object Loading : TaskUiState()

    data class Success(
        val data: UserTotalTaskModel
    ) : TaskUiState()

    data class Error(
        val message: String
    ) : TaskUiState()
}