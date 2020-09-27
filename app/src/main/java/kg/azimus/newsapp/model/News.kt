package kg.azimus.newsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kg.azimus.newsapp.data.local.converter.NewsConverter

@Entity(tableName = "news")
@TypeConverters(NewsConverter::class)

data class News(
	@SerializedName("status") val status: String,
	@PrimaryKey(autoGenerate = true)
	@SerializedName("totalResults") val totalResults: Int,
	@SerializedName("articles") val articles: List<Articles>
)