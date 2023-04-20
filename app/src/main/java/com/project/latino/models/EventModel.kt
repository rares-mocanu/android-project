package com.project.latino.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class EventModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "details")
    var details : String,
    @ColumnInfo(name = "pictures")
    var pictures : List<Int>,
    @ColumnInfo(name = "club")
    var club: String,
)
