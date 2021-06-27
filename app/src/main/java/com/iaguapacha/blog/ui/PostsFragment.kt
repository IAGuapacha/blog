package com.iaguapacha.blog.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iaguapacha.blog.R
import com.iaguapacha.blog.core.Result
import com.iaguapacha.blog.data.local.AppDatabase
import com.iaguapacha.blog.data.local.LocalPostDataSource
import com.iaguapacha.blog.data.model.Post
import com.iaguapacha.blog.data.remote.RemotePostsDataSource
import com.iaguapacha.blog.data.remote.RetrofitClient
import com.iaguapacha.blog.databinding.FragmentPostsBinding
import com.iaguapacha.blog.presentation.PostViewModel
import com.iaguapacha.blog.presentation.PostViewModelFactory
import com.iaguapacha.blog.repository.PostRepositoryImpl
import com.iaguapacha.blog.ui.adapters.PostAdapter


class PostsFragment : Fragment(R.layout.fragment_posts), PostAdapter.OnPostCliclListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentPostsBinding

    private val viewModel by viewModels<PostViewModel> {
        PostViewModelFactory(
            PostRepositoryImpl(
                RemotePostsDataSource(RetrofitClient.webservice),
                LocalPostDataSource(AppDatabase.getDatabase(requireContext()).postDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostsBinding.bind(view)

        binding.svSearch.setOnQueryTextListener(this@PostsFragment)

        viewModel.getPosts().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvPost.adapter = PostAdapter(result.data, this@PostsFragment)
                }
                is Result.Failure -> {
                    Toast.makeText(requireContext(),"Check your internet connection", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                    Log.e("Post", "Error: ${result.exception} ")
                }

            }
        })
    }

    override fun onPostClick(post: Post) {

        val action = PostsFragmentDirections.actionPostsFragmentToPostDetailFragment(
            post.userId,
            post.id,
            post.title,
            post.body
        )

        findNavController().navigate(action)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        binding.rvPost.adapter?.let { adapter ->
            newText?.let {
                (adapter as PostAdapter).filter(it)
            }
        }
        return false
    }

}