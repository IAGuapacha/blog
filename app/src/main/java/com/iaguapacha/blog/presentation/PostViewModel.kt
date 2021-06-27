package com.iaguapacha.blog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.iaguapacha.blog.core.Result
import com.iaguapacha.blog.repository.PostRepository
import kotlinx.coroutines.Dispatchers

class PostViewModel(private val repo:PostRepository): ViewModel() {

    fun getPosts() = liveData(Dispatchers.IO){

        emit(Result.Loading())
        try{
            emit(Result.Success(repo.getPosts()))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

}

class PostViewModelFactory(private val repo:PostRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repo) as T
    }

}