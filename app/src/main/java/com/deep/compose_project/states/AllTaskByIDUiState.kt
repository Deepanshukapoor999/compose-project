package com.deep.compose_project.states

import com.deep.compose_project.model.TaskIdByModel

sealed class AllTaskByIDUiState {

    object Loading : AllTaskByIDUiState()

    data class Success(
        val data: TaskIdByModel
    ) : AllTaskByIDUiState()

    data class Error(
        val message: String
    ) : AllTaskByIDUiState()
}