package com.jae464.culinaryheaven

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CulinaryHeavenApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val keyHash = Utility.getKeyHash(this)
        Log.d("CulinaryHeavenApplication", keyHash)
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }
}