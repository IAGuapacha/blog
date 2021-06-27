package com.iaguapacha.blog.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.iaguapacha.blog.R
import com.iaguapacha.blog.core.Result
import com.iaguapacha.blog.data.local.AppDatabase
import com.iaguapacha.blog.data.local.LocalPostDetailDataSource
import com.iaguapacha.blog.data.model.Commentary
import com.iaguapacha.blog.data.remote.RemotePostDetailDataSource
import com.iaguapacha.blog.data.remote.RetrofitClient
import com.iaguapacha.blog.databinding.FragmentPostDetailBinding
import com.iaguapacha.blog.presentation.PostDetailViewModel
import com.iaguapacha.blog.presentation.PostDetailViewModelFactory
import com.iaguapacha.blog.repository.PostDetailRepositoryImpl
import com.iaguapacha.blog.ui.adapters.CommentaryAdapter

class PostDetailFragment : Fragment(R.layout.fragment_post_detail) {

    private val args by navArgs<PostDetailFragmentArgs>()
    private var comments:MutableList<Commentary> = mutableListOf()
    private lateinit var binding: FragmentPostDetailBinding

    private val viewModel by viewModels<PostDetailViewModel> {
        PostDetailViewModelFactory(
            PostDetailRepositoryImpl(
                RemotePostDetailDataSource(
                    RetrofitClient.webservice
                ),
                LocalPostDetailDataSource(
                    AppDatabase.getDatabase(
                        requireContext()
                    ).commentaryDao()
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostDetailBinding.bind(view)
        binding.txtPostTitle.text = args.title
        binding.txtPostBody.text = args.body

        getComments()
        binding.btnSendCommentary.setOnClickListener {
            val bodyCommentary = binding.txtCommentary.text.toString().trim()
            val commentary = Commentary(args.id, 0, "Name", "Email", bodyCommentary)
            viewModel.saveCommentary(commentary).observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {

                        hideKeyboard()
                        comments.add(commentary)
                        binding.rvComments.adapter?.notifyDataSetChanged()
                        binding.txtCommentary.setText("")
                        Toast.makeText(requireContext(),"Comment added",Toast.LENGTH_LONG).show()
                    }
                    is Result.Failure -> {


                    }
                }
            })
        }


    }

    private fun hideKeyboard(){
        val view = requireActivity().currentFocus
        if (view != null){
            val im:InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(view.windowToken,0)
        }
    }

    private fun getComments() {
        viewModel.getComments(args.id).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    comments = result.data as MutableList<Commentary>
                    binding.rvComments.adapter = CommentaryAdapter(comments)
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Check your internet connection",Toast.LENGTH_LONG).show()
                    Log.e("Commentary", "Error: ${result.exception} ")
                }
            }
        })
    }

}