package com.project.latino.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(
    tableName="event_item",
    foreignKeys = [
        ForeignKey(entity = ClubModel::class, parentColumns = ["clubID"], childColumns = ["club"])
    ]
)
data class EventModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name="date")
    var eventDate: Calendar,
    @ColumnInfo(name="time")
    var eventTime: Calendar,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "details")
    var details : String,
    @ColumnInfo(name = "pictures")
    var pictures : ArrayList<Int>,
    @ColumnInfo(name = "club")
    var club: Int,
    )
