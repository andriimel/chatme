package com.am.chatme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.chatme.data.Injection
import com.am.chatme.data.MessageRepository
import com.am.chatme.data.Result.*
import com.am.chatme.data.User
import com.am.chatme.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.am.chatme.data.Message
import kotlinx.coroutines.launch

class MessageViewModel: ViewModel() {
    private val messageRepository: MessageRepository
    private val userRepository: UserRepository

    init {
        messageRepository = MessageRepository(Injection.instance())
        userRepository = UserRepository(FirebaseAuth.getInstance(), Injection.instance())

        loadCurrentUser()
    }

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    private fun loadCurrentUser(){
        viewModelScope.launch{
            when(val result = userRepository.getCurrentUser()) {
                is Success-> _currentUser.value = result.data
                is Error -> {
                    //
                }
            }
        }
    }

    fun loadMessages(){
        viewModelScope.launch {
            if(_roomId != null){
                messageRepository.getChatMessages(_roomId.value.toString()).collect {_messages.value = it }
            }
        }
    }

    fun sendMessage(text: String) {
        if (_currentUser.value != null) {
            val message = Message(
                senderFirstName = _currentUser.value!!.firstName,
                senderId = _currentUser.value!!.email,
                text = text
            )
            viewModelScope.launch {
                when (messageRepository.sendMessage(_roomId.value.toString(), message)) {
                    is Success -> Unit
                    is Error -> {

                    }
                }
            }
        }
    }
    fun setRoomId(roomId: String) {
        _roomId.value = roomId
        loadMessages()
    }

}