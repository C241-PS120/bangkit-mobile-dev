import com.example.coffeeprotectionandanalysissystem.response.ArticleResponse
import com.example.coffeeprotectionandanalysissystem.response.PredictionResponse
import com.example.coffeeprotectionandanalysissystem.response.WeatherResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @Multipart
    @POST("predict")
    fun getPrediction(
        @Part photo: MultipartBody.Part
    ): Call<PredictionResponse>

    @GET("articles")
    suspend fun getAllArticles(): ArticleResponse

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") aqi: String
    ): WeatherResponse

}
