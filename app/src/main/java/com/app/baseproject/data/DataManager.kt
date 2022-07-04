package com.app.baseproject.data

import com.app.baseproject.data.local.Preferences
import com.app.baseproject.data.model.request.PostData
import com.app.baseproject.data.model.request.PostToken
import com.app.baseproject.data.model.response.ResponRegister
import com.app.baseproject.data.model.response.ResponseLogin
import com.app.baseproject.data.model.response.ResponseSetupData
import com.app.baseproject.data.remote.RestService
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(val mRestService: RestService,
) {

    @Inject
    lateinit var preferences: Preferences

//    Default
//    fun login(data: PostData): Observable<ResponseLogin> {
//        return mRestService.login(preferences.getBaseUrl(), data)
//    }

    fun login(user: PostData): Observable<ResponseLogin> {
        return mRestService.login(preferences.getBaseUrl() + "login.php", user)
    }

    fun register(user: PostData): Observable<ResponRegister>{
        return mRestService.register(preferences.getBaseUrl() + "regisuser.php", user)
    }

    fun importDb(tokenHeader: String, token: PostToken): Observable<ResponseSetupData> {
        return mRestService.importDb(preferences.getBaseUrl() + "import-db.php", "Bearer $tokenHeader", token)
    }

}