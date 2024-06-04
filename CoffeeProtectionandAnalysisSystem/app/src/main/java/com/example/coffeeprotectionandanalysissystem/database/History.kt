package com.example.coffeeprotectionandanalysissystem.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "History_Scan")
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "image") val imageId: String,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "suggestion") val suggestion: String,
    @ColumnInfo(name = "date") val date: String
) : Parcelable
