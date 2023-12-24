package com.archonie.petracker.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.archonie.petracker.R
import com.archonie.petracker.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun signUp(view: View){
        val email = binding.usernameText.text.toString()
        val password = binding.passwordText.text.toString()
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            val intent = Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun signIn(view: View){
        val email = binding.usernameText.text.toString()
        val password = binding.passwordText.text.toString()
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            val intent = Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}