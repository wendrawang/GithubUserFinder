package app.wendra.githubuserfinder.data.datasource.remote

import app.wendra.githubuserfinder.util.Constants
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.JsonParser
import org.json.JSONObject

class RemoteDataSource {
    fun searchUser(name: String, page: Int, perPage: Int = 30, callback: (JSONObject?, String) -> Unit) {
        var url = "${Constants.base_url}/search/users?q=$name+in:login&page=$page&per_page=$perPage"

        AndroidNetworking.get(url)
            .doNotCacheResponse()
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    callback(response, "")
                }

                override fun onError(anError: ANError?) {
                    if(anError != null){
                        val parser = JsonParser()
                        val obj = parser.parse(anError.errorBody)
                        val errorMsg = obj.asJsonObject.get("message").asString

                        callback(null, errorMsg)
                    }else {
                        callback(null, Constants.error_msg)
                    }
                }
            })
    }

    fun getUserData(url: String, callback: (JSONObject?, String) -> Unit) {
        AndroidNetworking.get(url)
            .doNotCacheResponse()
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    callback(response, "")
                }

                override fun onError(anError: ANError?) {
                    if(anError != null){
                        val parser = JsonParser()
                        val obj = parser.parse(anError.errorBody)
                        val errorMsg = obj.asJsonObject.get("message").asString

                        callback(null, errorMsg)
                    }else {
                        callback(null, Constants.error_msg)
                    }
                }
            })
    }
}