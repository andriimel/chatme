package com.am.chatme.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.am.chatme.viewmodel.AuthViewModel

@Composable
fun NavigationGraph(
    authViewModel: AuthViewModel,
    navController:NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.SignupScreen.route
    ){
        composable(Screen.SignupScreen.route) {
            SignUpScreen (authViewModel = authViewModel, onNavigationToLogin = {
                navController.navigate(Screen.LoginScreen.route)})
        }

        composable (Screen.LoginScreen.route){
            LoginScreen(authViewModel = authViewModel,
                onNavigationToSignUp = {
                navController.navigate(Screen.SignupScreen.route)
            }){
                navController.navigate(Screen.ChatRoomsScreen.route)
            }
        }
        composable(Screen.ChatRoomsScreen.route) {
            ChatRoomList {
                navController.navigate("${Screen.ChatScreen.route}/${it.id}")
            }
        }
        composable("${Screen.ChatScreen.route}/{roomId}") {
            val roomId: String = it
                .arguments?.getString("roomId") ?: ""
            ChatScreen(roomId = roomId)
        }
    }

}