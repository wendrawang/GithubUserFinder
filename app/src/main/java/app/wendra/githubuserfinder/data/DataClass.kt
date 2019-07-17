package app.wendra.githubuserfinder.data

data class UserDataClass(
    var pageUrl: String = "",
    var name: String = "",
    var avatarUrl: String = ""
)

data class ResponseSearch(
    var listUser: List<UserDataClass>? = mutableListOf(),
    var errorMsg: String = ""
)