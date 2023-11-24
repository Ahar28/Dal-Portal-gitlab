package com.example.dalportal.ui.DiscussionForm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dalportal.R
import com.example.dalportal.model.Post

class PostAdapter(private var posts: MutableList<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    // ViewHolder class for holding the post views
    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val postContent: TextView = view.findViewById(R.id.postContent)
        val likeButton: ImageView = view.findViewById(R.id.likeButton)
        val commentButton: ImageView = view.findViewById(R.id.commentButton)
        val likeCount: TextView = view.findViewById(R.id.likeCount)
        val commentCount: TextView = view.findViewById(R.id.commentCount)
    }

    // Called when RecyclerView needs a new ViewHolder of the given type to represent an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    // Called by RecyclerView to display the data at the specified position
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.postContent.text = post.content
        holder.likeCount.text = post.likes.toString()
        holder.commentCount.text = post.comments.toString()

        // Interaction logic for like button
        holder.likeButton.setOnClickListener {
            // Increment like count for the post
            post.likes++
            holder.likeCount.text = post.likes.toString()
            notifyItemChanged(position)
        }

        // Interaction logic for comment button
        holder.commentButton.setOnClickListener {
            // Handle comment button click
        }
    }

    // Returns the total number of items in the data set held by the adapter
    override fun getItemCount() = posts.size

    // Update the posts and notify the adapter
    fun updatePosts(newPosts: List<Post>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }
}
