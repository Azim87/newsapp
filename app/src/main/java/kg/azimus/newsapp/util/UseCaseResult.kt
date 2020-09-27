package kg.azimus.newsapp.util

sealed class UseCaseResult<out R> {

    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
}