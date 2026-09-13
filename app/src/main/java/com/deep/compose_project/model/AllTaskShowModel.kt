package com.deep.compose_project.model

data class AllTaskShowModel(
    val `data`: List<AllTaskData> = emptyList(),
    val day: List<String>,
    val message: String,
    val month: List<String>,
    val status: String,
    val year: List<String>
)

data class AllTaskData(
    val AssignedTaskBy: String,
    val AssignedTaskByCode: String,
    val AssignedTaskByMe: Int,
    val AssignedTaskByUserTypeCode: String,
    val AssignedTaskCode: String,
    val AssignedTaskStatus: String,
    val AssignedTaskStatusname: String,
    val LinkedDepatmentNames: String,
    val TaskAttachment: List<String>,
    val TaskCode: String,
    val TaskCreatedTime: String,
    val TaskDescription: String,
    val TaskEndDateTime: String,
    val TaskRemarks: String,
    val TaskStartDateTime: String,
    val TaskTitle: String,
    val TaskUpdatedTime: String
)