package com.example.database.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class Employee(@PrimaryKey (autoGenerate = true) val id:Int? = null, val name:String)

@Dao
interface EmployeeDao {
    @Insert
    fun add(employee: Employee)

    @Delete
    fun delete(employee: Employee)

    @Query("SELECT * FROM employee")
    fun getAll():LiveData<List<Employee>>
}

@Database(entities = [Employee::class], version = 2)
abstract class EmployeeDatabase:RoomDatabase(){
    abstract fun employeeDao():EmployeeDao

}