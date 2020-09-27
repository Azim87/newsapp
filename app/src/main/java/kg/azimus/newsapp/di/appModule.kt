package kg.azimus.newsapp.di

import androidx.room.Room
import kg.azimus.newsapp.data.local.db.AppDataBase
import kg.azimus.newsapp.data.remote.NewsApiBuilder
import kg.azimus.newsapp.repository.MainRepositoryImpl
import kg.azimus.newsapp.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val BASE_URL = "http://newsapi.org/"
const val API_KEY = "35ee10f97ef44461956339bf1a164225"

val viewModelModel = module {
    viewModel { MainViewModel(get()) }
}

val repoModule = module {
    factory { MainRepositoryImpl(get(), get()) }
}

val networkModule = module {
    single { NewsApiBuilder() }
}

val localDataModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDataBase::class.java, "News.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDataBase>().getHistoryDao() }
}

val appModules = listOf(viewModelModel, repoModule, networkModule, localDataModule)