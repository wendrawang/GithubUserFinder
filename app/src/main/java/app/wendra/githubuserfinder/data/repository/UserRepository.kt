package app.wendra.githubuserfinder.data.repository

import app.wendra.githubuserfinder.data.datasource.remote.RemoteDataSource
import app.wendra.githubuserfinder.data.datasource.remote.model.GithubUser
import app.wendra.githubuserfinder.data.datasource.remote.model.UserResults
import app.wendra.githubuserfinder.util.Constants
import com.google.gson.Gson

class UserRepository {
    private val remoteDataSource = RemoteDataSource()
    private val gson = Gson()

    fun searchUser(name: String, page: Int, callback: (MutableList<String>?, String) -> Unit) {
        remoteDataSource.searchUser(name, page){ datas, errorMsg ->
            if(errorMsg.isEmpty()){
                val results = gson.fromJson(datas.toString(), UserResults::class.java)
                results.items?.let {listUser ->
                    //to filter url is null or not and also mapping to get url only
                    val listUrl = listUser.filter { data -> data.url != null }
                        .map { data -> data.url ?: "" }.toMutableList()

                    //check if no matching account (show as empty list)
                    if(listUrl.isEmpty()){
                        //prevent show error when trying open page > 1 but there is no data
                        if(page == 1){
                            callback(null, Constants.error_not_match_msg)
                        }else {
                            callback(null, "")
                        }
                    }else {
                        callback(listUrl, "")
                    }
                }
            }else {
                callback(null, errorMsg)
            }
        }
    }

    fun getUserData(url: String, callback: (GithubUser?, String)-> Unit) {
        remoteDataSource.getUserData(url) { data, errorMsg ->
            val result = gson.fromJson(data.toString(), GithubUser::class.java)
            callback(result, errorMsg)
        }
    }
}