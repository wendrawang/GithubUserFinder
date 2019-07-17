package app.wendra.githubuserfinder.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.wendra.githubuserfinder.R
import app.wendra.githubuserfinder.data.UserDataClass
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_layout.view.*

class ListAdapter : RecyclerView.Adapter<UserHolder>() {

    private var users = mutableListOf<UserDataClass>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): UserHolder {
        return UserHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_layout, viewGroup, false))
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bindUser(users[position])
    }

    fun refreshList(listUser: MutableList<UserDataClass>){
        users = listUser
        notifyDataSetChanged()
    }
}

class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvUser = view.username_txt
    private val imgUser = view.user_img

    fun bindUser(user: UserDataClass) {
        tvUser.text = user.name
        Glide.with(itemView).load(user.avatarUrl).into(imgUser)
    }
}