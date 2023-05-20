package com.project.latino.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="location_item")
data class LocationModel(
    @PrimaryKey(autoGenerate = true)
    var waypointID: Int?,
    @ColumnInfo(name="name")
    var name: String,
    @ColumnInfo(name="lat")
    var lat: Double,
    @ColumnInfo(name="lon")
    var lng: Double,
    @ColumnInfo(name="description")
    var desc: String

    )
