package br.unifor.financialmanagement.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import br.unifor.financialmanagement.R
import br.unifor.financialmanagement.activity.activity.LoginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val it = Intent(applicationContext, LoginActivity::class.java)
            startActivity(it)
            finish()
        },3000)
    }
}