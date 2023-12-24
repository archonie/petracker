package com.archonie.petracker.view

import android.content.AbstractThreadedSyncAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.archonie.petracker.R
import com.archonie.petracker.adapter.PostAdapter
import com.archonie.petracker.databinding.ActivityFeedBinding
import com.archonie.petracker.model.Post
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var postAdapter: PostAdapter
    private lateinit var posts : ArrayList<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        db = Firebase.firestore

        posts = arrayListOf()
        getData()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter = PostAdapter(posts)
        binding.recyclerView.adapter = postAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.sign_out){
            auth.signOut()
            val intent = Intent(this@FeedActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if (item.itemId == R.id.upload_image){
            val intent = Intent(this@FeedActivity, UploadActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData(){
        db.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            else{
                if(value != null && !value.isEmpty){
                    val documents = value.documents

                    posts.clear()
                    for(document in documents){
                        val comment = document.get("comment") as String
                        val email = document.get("email") as String
                        val downloadUrl = document.get("downloadUrl") as String
                        val post = Post(email, downloadUrl, comment)
                        posts.add(post)
                    }
                    postAdapter.notifyDataSetChanged()
                }
            }
        }

    }
}