package com.deep.compose_project.model

data class TaskSummaryModel(
    var name: String,
    var totalTask: String,
    var progress: Float,
    var progressTxt: String,
    var color:Int
)