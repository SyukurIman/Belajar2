package com.example.belajar2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception
import java.text.DecimalFormat

class MainViewModel : ViewModel() {

    private val listWeather = MutableLiveData<ArrayList<WeatherItems>>()

    fun setWeather(cities: String) {
        val listItems = ArrayList<WeatherItems>()

        val apiKey = "fe58c4fa8c45b305a28318dd830d28c6"
        val url = "https://api.github.com/search/users?q=${cities}"

        val client =  AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val weather = list.getJSONObject(i)
                        val weatherItems = WeatherItems()
                        weatherItems.name = weather.getString("login")
                        weatherItems.id = weather.getInt("id")
                        listItems.add(weatherItems)
                    }

                    listWeather.postValue(listItems)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("OnFailure", error?.message.toString())
            }
        })
    }

    fun getWeather(): LiveData<ArrayList<WeatherItems>> {
        return listWeather
    }
}