package com.project.latino.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HPItemModel(val title: String, val image: Int) : Parcelable