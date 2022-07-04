package com.app.baseproject.data.model.response

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ResponseLogin(

	@SerializedName("code")
	var code: String? = null,

	@SerializedName("token")
	var token: String? = null,

	@SerializedName("message")
	var message: String? = null,
)