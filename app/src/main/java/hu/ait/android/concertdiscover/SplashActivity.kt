package hu.ait.android.concertdiscover

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    val splashDuration : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getSupportActionBar()?.hide()

        Handler().postDelayed( {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, splashDuration)

    }
}