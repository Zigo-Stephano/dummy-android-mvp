package com.app.baseproject.data.model.response

import com.app.baseproject.data.model.local.User
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class ResponseSetupData(

    @SerializedName("code")
    var code: String? = null,

    @SerializedName("data")
    var data: List<User>? = null,

    @SerializedName("message")
    var message: String? = null,
)