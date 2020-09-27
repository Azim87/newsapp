package kg.azimus.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kg.azimus.newsapp.model.News


@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: News)

    @Query("SELECT * FROM news")
    suspend fun getAllNews(): News
}