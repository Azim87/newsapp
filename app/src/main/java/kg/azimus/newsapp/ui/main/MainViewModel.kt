package kg.azimus.newsapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.azimus.newsapp.model.News
import kg.azimus.newsapp.repository.MainRepositoryImpl
import kg.azimus.newsapp.util.UseCaseResult
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val mainRepository: MainRepositoryImpl
) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    private var googleNewsList: MutableLiveData<News> = MutableLiveData()
    private val showError: MutableLiveData<String> = MutableLiveData()
    private val mLocalNews: MutableLiveData<News> = MutableLiveData()


    val successState: MutableLiveData<News> get() = googleNewsList
    val errorState: MutableLiveData<String> get() = showError
    val localNewsList: MutableLiveData<News> get() = mLocalNews

    fun getNewsData() {
        launch {
            val newsResult = withContext(IO) {
                mainRepository.getGoogleNews()
            }
            when (newsResult) {
                is UseCaseResult.Success -> successState.value = newsResult.data
                is UseCaseResult.Error -> errorState.value = newsResult.exception.localizedMessage
            }
        }
    }

    fun saveToLocalData(news: News) {
        launch {
            withContext(IO) {
                mainRepository.insertNewsToLocalData(news)
                job.complete()
            }
        }
    }

    fun readFromLocalDataBase() {
        launch {
            withContext(IO) {
                localNewsList.postValue(mainRepository.readFromDataBase())
                job.complete()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}