package kg.azimus.newsapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kg.azimus.newsapp.data.local.dao.NewsDao

import kg.azimus.newsapp.model.News

@Database(
    entities = [News::class],
    version = 3,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getHistoryDao(): NewsDao
}