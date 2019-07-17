package app.wendra.githubuserfinder.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.wendra.githubuserfinder.R
import app.wendra.githubuserfinder.data.UserDataClass
import app.wendra.githubuserfinder.data.repository.UserRepository
import app.wendra.githubuserfinder.util.Constants
import app.wendra.githubuserfinder.util.KeyboardUtil
import app.wendra.githubuserfinder.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private var userAdapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        initView()
        initListener()
        initObserver()
    }

    private fun initView(){
        users_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }

        users_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                linearLayoutManager?.let {
                    if (it.itemCount <= it.findLastVisibleItemPosition() + 2) {
                        if(search_txt.text.toString().isNotEmpty()) {
                            viewModel.searchUser(search_txt.text.toString())
                        }
                    }
                }
            }
        })
    }

    private fun initListener(){
        search_btn.setOnClickListener {
            //close keyboard
            KeyboardUtil.hideKeyboard(this)

            if(search_txt.text.toString().isEmpty()) {
                Toast.makeText(this, Constants.error_empty_msg, Toast.LENGTH_LONG).show()
            }else {
                viewModel.searchUser(search_txt.text.toString(), true)
            }
        }
    }

    private fun initObserver(){
        viewModel.resultSearch.observe(this, Observer {
            if(it.listUser != null) {
                it.listUser?.let { list ->
                    val listUser = list.toMutableList()
                    userAdapter.refreshList(listUser)
                }
            }else {
                Toast.makeText(this, it.errorMsg, Toast.LENGTH_LONG).show()
            }
        })
    }
}