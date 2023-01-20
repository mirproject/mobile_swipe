package ru.mobile.activities

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ru.mobile.R
import ru.mobile.usecases.RefreshAuthUsecase


class SplashActivity : AppCompatActivity() {

    private val refreshAuthUsecase: RefreshAuthUsecase = RefreshAuthUsecase()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setTheme(R.style.Theme_Mirmobile)
        super.onCreate(savedInstanceState)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        refreshAuthUsecase.execute("identityToken")
        startActivity(Intent(this@SplashActivity, LentaActivity::class.java))
        overridePendingTransition(0, 0)
    }

}