package com.archonie.petracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.archonie.petracker.databinding.RecyclerRowBinding
import com.archonie.petracker.model.Post
import com.squareup.picasso.Picasso


class PostAdapter(val posts: ArrayList<Post>):RecyclerView.Adapter<PostAdapter.PostHolder>(){
    class PostHolder(val binding: RecyclerRowBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.recyclerComment.setText(posts.get(position).comment)
        holder.binding.userNameRecycler.setText(posts.get(position).email)
        Picasso.get().load(posts.get(position).downloadUrl).into(holder.binding.recyclerImage)
    }
}