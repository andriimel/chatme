package com.am.chatme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.am.chatme.data.Result
import com.am.chatme.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onNavigationToSignUp: ()-> Unit,
    onSignInSuccess: ()-> Unit
){
    val result by authViewModel.authResult.observeAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column ( modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            label = {Text("Email")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
                value = password,
        onValueChange = { password = it},
        label = {Text("Password")},

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                authViewModel.login(email,password)
                when(result) {
                    is Result.Success -> {
                        onSignInSuccess()
                    }

                    is Result.Error -> {}
                    else -> {}
                }


            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Don't have an account? Sign up.",
            modifier = Modifier
                .clickable {
                    onNavigationToSignUp()
                })
    }
}
