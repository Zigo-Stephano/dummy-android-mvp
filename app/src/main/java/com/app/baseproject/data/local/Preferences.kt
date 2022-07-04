package com.app.baseproject.data.local

import com.orhanobut.hawk.Hawk
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor() {


    /**
     * func for save token in Hawk session
     */
    fun saveToken(token: String?) {
        Hawk.put("token", token)
    }

    /**
     * func for get token in Hawk session
     */
    fun getToken(): String? {
        return Hawk.get("token") ?: ""
    }

    /**
     * func for save user in Hawk session
     */
    fun saveExpired(date: Date?) {
        Hawk.put("expired", date)
    }

    /**
     * func for get user in Hawk session
     */
    fun getExpired(): Date? {
        return Hawk.get("expired")
    }

    /**
     * func for save user in Hawk session
     */
    fun saveUser(user: String?) {
        Hawk.put("user", user)
    }

    /**
     * func for get user in Hawk session
     */
    fun getUser(): String? {
        return Hawk.get("user")
    }

    /**
     * func for save password in Hawk session
     */
    fun savePassword(password: String?) {
        Hawk.put("password", password)
    }

    /**
     * func for get password in Hawk session
     */
    fun getPassword(): String? {
        return Hawk.get("password")
    }

    /**
     * func for delete token in Hawk session
     */
    fun deleteToken() {
        Hawk.delete("token")
    }

    /**
     * func for save base url in Hawk session
     */
    fun saveBaseUrl(baseUrl: String) {
        Hawk.put("baseUrl", baseUrl)
    }

    /**
     * func for get base url in Hawk session
     */
    fun getBaseUrl(): String {
        return Hawk.get("baseUrl") ?: ""
    }

}
