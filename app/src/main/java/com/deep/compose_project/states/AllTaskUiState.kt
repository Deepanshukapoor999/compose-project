package com.deep.compose_project.states

import com.deep.compose_project.model.AllTaskShowModel
import com.deep.compose_project.model.UserTotalTaskModel

sealed class AllTaskUiState {

    object Loading : AllTaskUiState()

    data class Success(
        val data: AllTaskShowModel
    ) : AllTaskUiState()

    data class Error(
        val message: String
    ) : AllTaskUiState()
}