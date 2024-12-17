package com.example.postapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.postapi.MainScreen
import com.example.postapi.OnePostScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = "main",
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("main") {
            MainScreen(navController)
        }
        composable("onePost/{id}") {
            val id = it.arguments?.getString("id")
            OnePostScreen(navController, id!!.toInt())
        }

    }
}