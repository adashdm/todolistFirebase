package com.example.database
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var editText: EditText
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance("https://todolist-38a33-default-rtdb.europe-west1.firebasedatabase.app").reference

        editText = findViewById(R.id.editText)
        listView = findViewById(R.id.listView)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        listView.adapter = adapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val todoText = editText.text.toString().trim()
            if (todoText.isNotEmpty()) {
                addTodoToDatabase(todoText)
            } else {
                Toast.makeText(this, "Ввведіть текст завдання", Toast.LENGTH_SHORT).show()
            }
        }

        database.child("myToDo").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                adapter.clear()
                for (taskSnapshot in snapshot.children) {
                    val taskName = taskSnapshot.getValue(String::class.java)
                    if (taskName != null) {
                        adapter.add(taskName)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Ошибка получения данных: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addTodoToDatabase(todoText: String) {
        val todoId = database.child("myToDo").push().key
        if (todoId != null) {
            database.child("myToDo").child(todoId).setValue(todoText)
                .addOnSuccessListener {
                    Toast.makeText(this, "Завдання додане!", Toast.LENGTH_SHORT).show()
                    editText.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Помилка додавання завдання", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
