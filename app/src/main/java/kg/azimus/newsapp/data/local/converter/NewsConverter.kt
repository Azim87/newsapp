package kg.azimus.newsapp.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.azimus.newsapp.model.Articles

class NewsConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(raw: String?): List<Articles?>? {
        if (raw == null) {
            return null
        }
        val type =
            object : TypeToken<List<Articles?>?>() {}.type
        return gson.fromJson<List<Articles?>>(raw, type)
    }

    @TypeConverter
    fun toList(news: List<Articles?>?): String? {
        return gson.toJson(news)
    }
}