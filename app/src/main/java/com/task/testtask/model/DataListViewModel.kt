package com.task.testtask.model


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.testtask.retrofit.ApiService
import com.task.testtask.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataListViewModel : ViewModel() {

    private val apiService = RetrofitHelper.getRetrofitInstance().create(ApiService::class.java)

    val dataList = MutableLiveData<List<DataListModel>>()
    val isLoading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String>()

    val parentArrayList = mutableListOf<ArrayList<Int>>()

    private var currentPage = 1
    private val pageSize = 10
    private var startIndex = 0

    fun fetchPosts() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = withContext(Dispatchers.IO) {
                    apiService.getDataList(currentPage, pageSize)
                }
                if (response.isSuccessful) {
                    dataList.value = response.body()

                    for (i in startIndex until startIndex + pageSize) {
                        if (dataList.value != null && dataList.value!!.size > i) {
                            val childArrayList = ArrayList<Int>()
                            childArrayList.add(dataList.value!![i].id)
                            parentArrayList.add(childArrayList)
                            Log.e("TAG", "fetchPosts:childArrayList == $childArrayList")
                        }
                    }
                    Log.e("TAG", "fetchPosts:parentArrayList == $parentArrayList")
                    startIndex += pageSize

                    currentPage++
                } else {
                    errorMessage.value = response.message()
                }
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage
            } finally {
                isLoading.value = false
            }
        }
    }
}