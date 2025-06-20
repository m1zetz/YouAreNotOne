package com.example.youarenotalone.ui.screens.bottomNav

import androidx.navigation.compose.composable
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.example.youarenotalone.my_stories
import com.example.youarenotalone.settings
import com.example.youarenotalone.stories
import com.example.youarenotalone.ui.screens.bottomNav.screens.MyStories
import com.example.youarenotalone.ui.screens.bottomNav.screens.Settings
import com.example.youarenotalone.ui.screens.bottomNav.screens.Stories

@Composable
fun NavGraph(
    navHostController: NavHostController
) {

    NavHost(
        navHostController,
        startDestination = my_stories){
        composable(stories){
            Stories()
        }
        composable(my_stories){
            MyStories()
        }
        composable(settings){
            Settings()
        }

    }

}