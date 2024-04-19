package com.task.testtask.model

data class DataListModel(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) {
    override fun toString(): String {
        return "PostModel(id=$id, userId=$userId, title='$title', body='$body')"
    }
}