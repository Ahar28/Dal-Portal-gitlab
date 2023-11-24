package com.example.dalportal.util

import android.util.Log
import com.example.dalportal.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

object FirestoreHelper {
    private val db = FirebaseFirestore.getInstance()

    fun addPost(post: Post, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("posts")
            .add(post)
            .addOnSuccessListener {
                Log.d("CreatePostActivity", "Post added successfully")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("CreatePostActivity", "Error adding post", e)
                onFailure(e)
            }
    }

    fun fetchPosts(onSuccess: (List<Post>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING) // Assuming you want to fetch by timestamp
            .get()
            .addOnSuccessListener { documents ->
                val posts = documents.mapNotNull { it.toObject(Post::class.java) }
                onSuccess(posts)
            }
            .addOnFailureListener { e ->
                Log.e("PostListActivity", "Error fetching posts", e)
                onFailure(e)
            }
    }
}


