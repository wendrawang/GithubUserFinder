package app.wendra.githubuserfinder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.wendra.githubuserfinder.data.ResponseSearch
import app.wendra.githubuserfinder.data.UserDataClass
import app.wendra.githubuserfinder.data.repository.UserRepository

class MainViewModel: ViewModel() {

    private val userRepository = UserRepository()

    val resultSearch = MutableLiveData<ResponseSearch>()

    private var currentPage = 1

    private var allSearchUsers = mutableListOf<UserDataClass>()
    private var successSearchUsers = mutableListOf<UserDataClass>()

    fun searchUser(name: String, isFirstTime: Boolean = false){
        if(isFirstTime) {
            //reset page every time this method is execute
            currentPage = 1
            successSearchUsers = mutableListOf()
        }

        userRepository.searchUser(name, currentPage) { listUrl, errorMsg ->
            var listUser = mutableListOf<UserDataClass>()
            listUrl?.let {
                it.forEach {url ->
                    var user = UserDataClass()
                    user.pageUrl = url
                    listUser.add(user)
                }
            }

            if(isFirstTime){
                allSearchUsers = listUser
            }else {
                allSearchUsers.addAll(listUser)
            }

            resultSearch.value = ResponseSearch(null, errorMsg)

            getUserData()
            currentPage++
        }
    }

    private fun getUserData(){
        val remainingUsers = mutableListOf<UserDataClass>()
        remainingUsers.addAll(allSearchUsers)

        //remove if already got data
        for (temp in successSearchUsers) {
            remainingUsers.removeAll { y -> y.pageUrl == temp.pageUrl }
        }

        if(remainingUsers.size > 0){
            val user = remainingUsers[0]
            userRepository.getUserData(user.pageUrl){ userData, errorMsg ->
                if(userData != null){
                    val selectedUser = UserDataClass()
                    selectedUser.avatarUrl = userData.avatarUrl ?: ""
                    selectedUser.name = userData.login ?: ""
                    selectedUser.pageUrl = userData.url ?: ""
                    successSearchUsers.add(user)

                    resultSearch.value = ResponseSearch(successSearchUsers, "")

                    getUserData()
                }else {
                    resultSearch.value = ResponseSearch(null, errorMsg)
                }
            }
        }
    }
}