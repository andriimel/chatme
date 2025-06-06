package com.am.chatme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.chatme.data.Injection
import com.am.chatme.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import com.am.chatme.data.Result

class AuthViewModel: ViewModel() {
    private val userRepository: UserRepository

    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }

    private val _authResult = MutableLiveData<Result<Boolean>>()
    val authResult: LiveData<Result<Boolean>> get() = _authResult

    fun signUp(emai: String, password: String, firstName: String,lastName: String){
        viewModelScope.launch {
            _authResult.value = userRepository.signUp(emai,password,firstName,lastName)
        }
    }

    fun login(emai: String,password: String){
        viewModelScope.launch {
            _authResult.value = userRepository.login(emai,password)
        }
    }
}