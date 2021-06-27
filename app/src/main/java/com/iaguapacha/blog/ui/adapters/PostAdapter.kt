package com.iaguapacha.blog.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iaguapacha.blog.core.BaseViewHolder
import com.iaguapacha.blog.data.model.Post
import com.iaguapacha.blog.databinding.PostItemBinding

class PostAdapter() : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var postList = mutableListOf<Post>()
    private lateinit var itemClickListener: OnPostCliclListener
    private val postListOriginal = mutableListOf<Post>()

    constructor(
        postList: List<Post>,
        itemClickListener: OnPostCliclListener,
    ) : this() {
        this.postList = postList as MutableList<Post>
        this.itemClickListener = itemClickListener

        postListOriginal.addAll(postList)
    }

    interface OnPostCliclListener {
        fun onPostClick(post: Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = PostViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition
            itemClickListener.onPostClick(postList[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PostViewHolder -> holder.bind(postList[position])
        }
    }

    override fun getItemCount(): Int = postList.size

    fun filter(search: String) {
        if (search.isEmpty()) {
            postList.clear()
            postList.addAll(postListOriginal)
        } else {
            val collect = postListOriginal.filter { i ->
                i.title.lowercase().contains(search)
            }
            postList.clear()
            postList.addAll(collect)
        }
        notifyDataSetChanged()
    }

    private inner class PostViewHolder(
        val binding: PostItemBinding
    ) : BaseViewHolder<Post>(binding.root) {

        override fun bind(item: Post) {
            binding.postTitle.text = item.title
            binding.postPreview.text = item.body
        }

    }
}