package com.project.latino.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class EventModel(
    @PrimaryKey(autoGenerate = true)
    var clubID: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "details")
    var details : String
)