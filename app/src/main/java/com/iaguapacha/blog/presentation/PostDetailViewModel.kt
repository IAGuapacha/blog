package com.iaguapacha.blog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.iaguapacha.blog.core.Result
import com.iaguapacha.blog.data.model.Commentary
import com.iaguapacha.blog.repository.PostDetailRepository
import kotlinx.coroutines.Dispatchers

class PostDetailViewModel(private val repo:PostDetailRepository):ViewModel() {

    fun getComments(postId:Int) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.getComments(postId)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

    fun saveCommentary(commentary: Commentary) = liveData(Dispatchers.IO) {

        emit(Result.Loading())
        try {
            emit(Result.Success(repo.saveCommentary(commentary)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }
}

class PostDetailViewModelFactory(private val repo: PostDetailRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostDetailViewModel(repo) as T
    }

}