package com.huaqing.property.base

import android.app.Application
import android.content.Context
import com.huaqing.property.BuildConfig
import com.huaqing.property.di.dbModule
import com.huaqing.property.di.httpClientModule
import com.huaqing.property.di.prefsModule
import com.huaqing.property.di.serviceModule
import com.huaqing.property.utils.logger.initLogger
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {
    override val kodein: Kodein
        get() = Kodein.lazy {
            bind<Context>() with singleton {
                this@App
            }

            import(androidModule(this@App))

            import(serviceModule)

            import(dbModule)

            import(httpClientModule)

            import(prefsModule)
        }


    companion object {
        lateinit var INSTANCE: App
    }


    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        initLogger(BuildConfig.DEBUG)
    }
}