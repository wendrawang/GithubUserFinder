package app.wendra.githubuserfinder.data

data class UserDataClass(
    var pageUrl: String = "",
    var name: String = "",
    var avatarUrl: String = "",
    var noOfViews: Int = 0
)

data class ResponseSearch(
    var listUser: List<UserDataClass>? = mutableListOf(),
    var errorMsg: String = ""
)