package com.example.database

import android.app.Application
import androidx.room.Room
import com.example.database.db.EmployeeDatabase

class MyApplication : Application() {
    lateinit var repo:EmployeeRepository
    override fun onCreate() {
        super.onCreate()
        instance = this
        val db = Room.databaseBuilder(this, EmployeeDatabase::class.java, "employee_database").build()
        repo = EmployeeRepository(db)
    }
    companion object {
        private lateinit var instance:MyApplication
        fun getApp() = instance
    }
}