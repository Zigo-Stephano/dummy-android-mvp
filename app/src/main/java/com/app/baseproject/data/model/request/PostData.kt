package com.app.baseproject.data.model.request

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonArray
import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class PostData(

        //request for login

        @SerializedName("user")
        var user: String? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("email")
        var email: String? = null,

        @SerializedName("password")
        var password: String? = null,

) : Parcelable {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}