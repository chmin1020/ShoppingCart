package com.FallTurtle.shoppingcart.viewModel

import androidx.lifecycle.*
import com.FallTurtle.shoppingcart.repository.RoomDataRepository
import com.FallTurtle.shoppingcart.model.BigList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 이 앱 아키텍쳐의 viewModel 객체.
 * 앱 구조가 간단하기 때문에 repository 객체를 통해 room DB 만 연결된다.
 */
@HiltViewModel
class ShoppingViewModel
    @Inject constructor(private val repository: RoomDataRepository) : ViewModel() {

    private val insideItems = MutableLiveData<List<BigList>>()
    val items: LiveData<List<BigList>> = insideItems

    fun getAllBigList()  {
        viewModelScope.launch(Dispatchers.IO){
            insideItems.postValue(repository.getList())
        }
    }

    fun insert(bigList: BigList) {
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(bigList)
        }
    }
   fun delete(bigList: BigList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(bigList)
        }
    }
}
