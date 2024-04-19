package com.task.testtask.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.testtask.R
import com.task.testtask.adapter.DataListAdapter
import com.task.testtask.databinding.ActivityMainBinding
import com.task.testtask.model.DataListViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DataListViewModel
    private var mLastClickTime: Long = 0

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DataListViewModel::class.java]

        val dataListAdapter = DataListAdapter { post ->

            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@DataListAdapter
            }
            mLastClickTime = SystemClock.elapsedRealtime()

            GlobalScope.launch {
                delay(1000L)
                val intent = Intent(this@MainActivity, DescriptionActivity::class.java)
                intent.putExtra("tvTitle", post.title)
                intent.putExtra("tvBody", post.body)
                startActivity(intent)
            }

        }

        binding.rvDataList.adapter = dataListAdapter
        binding.rvDataList.layoutManager = LinearLayoutManager(this)

//        viewModel.dataList.observe(this, Observer { dataList ->
//            dataListAdapter.setData(dataList)
//        })

        viewModel.dataList.observe(this, Observer { dataList ->
            val parentArrayList =
                viewModel.parentArrayList/*.observe(this, Observer { parentArrayList ->*/
            dataListAdapter.setData(dataList, parentArrayList)
            /* })*/
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading!!) {
                binding.idPBLoading.visibility = View.VISIBLE
            } else {
                binding.idPBLoading.visibility = View.GONE
            }
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            Log.e(TAG, "onCreate:errorMessage == $errorMessage ")
        })

        viewModel.fetchPosts()

        binding.rvDataList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && !viewModel.isLoading.value!!) {
                    viewModel.fetchPosts()
                }
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}