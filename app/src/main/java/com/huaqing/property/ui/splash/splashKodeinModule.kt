package com.huaqing.property.ui.splash

import android.app.Activity
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.huaqing.property.ext.viewmodel.addLifecycle
import com.huaqing.property.view.WowSplashView
import com.huaqing.property.view.WowView
import kotlinx.android.synthetic.main.activity_splash.*
import org.kodein.di.Kodein
import org.kodein.di.android.ActivityRetainedScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val SPLASH_MODULE_TAG = "SPLASH_MODULE_TAG"

val splashKodeinModule = Kodein.Module(SPLASH_MODULE_TAG) {

    bind<SplashViewModel>() with scoped<FragmentActivity>(ActivityRetainedScope).singleton {
        ViewModelProviders.of(context, SplashViewModelFactory())
            .get(SplashViewModel::class.java)
            .apply {
                addLifecycle(context)
            }
    }

    bind<ImageView>() with scoped<FragmentActivity>(ActivityRetainedScope).singleton {
        (context as SplashActivity).ivSplash
    }

    bind<WowSplashView>() with scoped<FragmentActivity>(ActivityRetainedScope).singleton {
        (context as SplashActivity).wowSplash
    }

    bind<WowView>() with scoped<FragmentActivity>(ActivityRetainedScope).singleton {
        (context as SplashActivity).wowView
    }


}