package com.project.latino.models

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName="school_item")
data class SchoolModel(
    @PrimaryKey(autoGenerate = true)
    var schoolID: Int?,
    @ColumnInfo(name="name")
    var name: String,
    @ColumnInfo(name="trainers")
    var trainers : ArrayList<String>,
    @ColumnInfo(name="logo")
    var logo : Int
    )
