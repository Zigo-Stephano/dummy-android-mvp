package com.app.baseproject.data.model.request

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class PostToken(

    //request for login

    @SerializedName("token")
    var token: String? = null,

    ) : Parcelable {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}