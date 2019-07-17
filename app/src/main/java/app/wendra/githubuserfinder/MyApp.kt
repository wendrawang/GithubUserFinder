package app.wendra.githubuserfinder

import android.app.Application
import com.androidnetworking.AndroidNetworking

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //init Fast Android Network
        AndroidNetworking.initialize(this)
    }
}