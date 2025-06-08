package com.am.chatme.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.chatme.data.Injection
import com.am.chatme.data.Result.Success
import com.am.chatme.data.Room
import com.am.chatme.data.RoomRepository
import com.am.chatme.data.Result.*
import kotlinx.coroutines.launch

class RoomViewModel: ViewModel() {

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms : LiveData<List<Room>> get() = _rooms
    private val roomRepository: RoomRepository
    init {
        roomRepository = RoomRepository(Injection.instance())
        loadRooms()
    }

    fun createRoom(name: String){
        viewModelScope.launch {
            roomRepository.createRoom(name)
        }
    }

    fun loadRooms() {
        viewModelScope.launch {
            when (val result = roomRepository.getRooms()) {
                is Success -> _rooms.value = result.data
                is Error -> {
                    Log.e("RoomViewModel", "Failed to load rooms", result.exception)
                }
            }
        }
    }

}