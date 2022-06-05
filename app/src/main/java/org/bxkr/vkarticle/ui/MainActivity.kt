package org.bxkr.vkarticle.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.bxkr.vkarticle.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref =
            this.getSharedPreferences(getString(R.string.preference_auth), Context.MODE_PRIVATE)
        if (intent.getBooleanExtra(
                getString(R.string.extra_logged_in),
                false
            ) || sharedPref.getBoolean(getString(R.string.preference_auth_is_logged_in), false)
        ) {
            // todo open frags
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}