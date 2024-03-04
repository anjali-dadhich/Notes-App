package com.example.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes (
    @PrimaryKey(autoGenerate = true)
    var id : Int = 1,
    var title : String,
    var description : String,
    var date : Long
)