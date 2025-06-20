package com.example.youarenotalone.ui.screens.bottomNav.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.youarenotalone.ui.screens.bottomNav.BottomNavigationFun
import com.example.youarenotalone.ui.screens.bottomNav.NavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationFun(
                navcontroller = navController
            )
        }
    ){
        NavGraph(navHostController = navController)
    }
}