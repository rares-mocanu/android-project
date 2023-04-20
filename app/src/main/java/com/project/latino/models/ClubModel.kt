package com.project.latino.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class ClubModel(
    @PrimaryKey(autoGenerate = true)
    var clubID: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "events")
    var events : ArrayList<String>,
    @ColumnInfo(name = "logo")
    var logo : Int
    )