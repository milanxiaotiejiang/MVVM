package com.huaqing.property.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huaqing.property.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager.apply {
            findFragmentByTag(TAG) ?: beginTransaction()
                .add(R.id.flContainer, LoginFragment(), TAG)
                .commitAllowingStateLoss()
        }
    }

    companion object {
        private const val TAG = "LoginFragment"
    }

}