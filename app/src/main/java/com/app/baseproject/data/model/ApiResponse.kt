package com.app.baseproject.data.model

/**
 * Created by test(test@gmail.com) on 11/21/16.
 */

class ApiResponse<T> {
    var response_code: Int? = null
    var status: String? = null
    var message: String? = null
    var data: T? = null
}
