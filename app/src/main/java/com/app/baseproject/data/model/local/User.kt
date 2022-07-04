package com.app.baseproject.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated


@Generated("com.robohorse.robopojogenerator")
@Entity(tableName = "users")
data class User(

    @field:SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @field:SerializedName("name")
    @ColumnInfo(name = "name") var name: String = "",

    @field:SerializedName("email")
    @ColumnInfo(name = "email") var email: String = "",
)