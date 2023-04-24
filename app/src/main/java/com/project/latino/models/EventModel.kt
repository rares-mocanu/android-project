package com.project.latino.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.util.Calendar

data class EventModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val eventDate: Calendar,
    val eventTime: Calendar,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "details")
    var details : String,
    @ColumnInfo(name = "pictures")
    var pictures : List<Int>,
    @ColumnInfo(name = "club")
    var club: String,
)
