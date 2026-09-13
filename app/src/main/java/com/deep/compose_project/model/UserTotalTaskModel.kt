package com.deep.compose_project.model

data class UserTotalTaskModel(
    val `data`: UserTotalTaskData,
    val message: String,
    val status: String
)

data class UserTotalTaskData(
    val Completed: Int= 0,
    val In_progress: Int= 0,
    val Over_due: Int= 0,
    val Total_assigned: Int= 0,
    val new: Int= 0,
)