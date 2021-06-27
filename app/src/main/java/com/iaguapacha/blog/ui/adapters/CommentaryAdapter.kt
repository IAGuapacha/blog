package com.iaguapacha.blog.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iaguapacha.blog.core.BaseViewHolder
import com.iaguapacha.blog.data.model.Commentary
import com.iaguapacha.blog.databinding.CommentaryItemBinding


class CommentaryAdapter(private val commentsList:List<Commentary>) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            CommentaryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CommentaryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is CommentaryViewHolder -> holder.bind(commentsList[position])
        }
    }

    override fun getItemCount(): Int  = commentsList.size


    private inner class CommentaryViewHolder(
        val binding: CommentaryItemBinding
    ) : BaseViewHolder<Commentary>(binding.root) {

        override fun bind(item: Commentary) {

            binding.txtCommentaryTitle.text = "${item.name} - ${item.email}"
            binding.txtCommentaryBody.text = item.body

        }

    }
}