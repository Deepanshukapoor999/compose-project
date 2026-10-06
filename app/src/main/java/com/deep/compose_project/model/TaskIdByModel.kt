package com.deep.compose_project.model

data class TaskIdByModel(
    val `data`: Data,
    val message: String,
    val status: String
)

data class Data(
    val AssignedDepartMentCode: String,
    val AssignedDepartMentName: String,
    val AssignedStoreCode: String,
    val AssignedStoreName: String,
    val AssignedTaskBy: String,
    val AssignedTaskById: String,
    val AssignedTaskCode: String,
    val AssignedTaskTo: String,
    val AssignedTaskToUserCode: String,
    val AssignedTaskUpdatedBy: String,
    val IsReminder: String,
    val RecurringParamter: String,
    val RecurringValue: String,
    val TaskAttachment: List<String>,
    val TaskCreatedTime: String,
    val TaskDescription: String,
    val TaskDuration: String,
    val TaskEndDateTime: String,
    val TaskPriorityCode: String,
    val TaskRemarks: String,
    val TaskStartDateTime: String,
    val TaskStatusCode: String,
    val TaskTitle: String,
    val TaskUpdatedTime: String,
    val TaskWebLinks: String,
    val TriggerFrequency: String,
    val users_not_updated_task: String,
    val users_not_updated_task_code: String
)