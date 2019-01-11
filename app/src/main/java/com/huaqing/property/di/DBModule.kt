package com.huaqing.property.di

import androidx.room.Room
import com.huaqing.property.base.App
import com.huaqing.property.db.AppDatabase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

private const val DB_MODULE_TAG = "DBModule"
val dbModule = Kodein.Module(DB_MODULE_TAG) {
    bind<AppDatabase>() with singleton {
        Room.databaseBuilder(App.INSTANCE, AppDatabase::class.java, "app.db").build()
    }
}