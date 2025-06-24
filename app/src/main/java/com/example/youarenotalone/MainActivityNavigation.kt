package com.example.youarenotalone

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.youarenotalone.ui.screens.ScreenSignIn
import com.example.youarenotalone.ui.screens.ScreenSignUp
import com.example.youarenotalone.ui.screens.bottomNav.BottomNavigationCompose
import com.example.youarenotalone.ui.screens.bottomNav.screens.Comments
import com.example.youarenotalone.ui.screens.bottomNav.screens.ExpandableCard

import com.example.youarenotalone.ui.screens.vms.SignInViewModel
import com.example.youarenotalone.ui.screens.vms.SignUpViewModel
import com.example.youarenotalone.ui.screens.vms.StoriesViewModel
import com.example.youarenotalone.ui.screens.vms.myUserId

import com.example.youarenotalone.ui.theme.YouAreNotAloneTheme
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.orange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    fun saveId(editor: SharedPreferences.Editor) {
        editor.apply {
            putInt("myId", myUserId.value!!)
        }.apply()
    }

    fun loadId(sharedPreferences: SharedPreferences) {
        val savedId = sharedPreferences.getInt("myId", -1)
        myUserId.value = savedId
    }

    fun forgetId(editor: SharedPreferences.Editor) {
        editor.apply {
            putInt("myId", -1)
        }.apply()
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YouAreNotAloneTheme {

                val context = LocalContext.current
                val sharedPreferences =
                    context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                if (myUserId.value!! < 0) {
                    loadId(sharedPreferences)
                }


                val signInViewModel: SignInViewModel = viewModel()
                val vmStories: StoriesViewModel = viewModel()
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = signInScreen,
                    Modifier.background(black),

                    ) {
                    composable(
                        signInScreen,
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(1000),

                                )

                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(1000)
                            )
                        }
                    ) {
                        ScreenSignIn(
                            signInViewModel = signInViewModel,
                            toSignUp = { navController.navigate(signUpScreen) },
                            toMain = { navController.navigate(mainScreen) },
                            sharedPreferences = sharedPreferences
                        )
                    }
                    composable(
                        signUpScreen,
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(1000),

                                )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(1000)
                            )

                        }
                    ) {
                        val signUpViewModel: SignUpViewModel = viewModel()
                        ScreenSignUp(
                            signUpViewModel = signUpViewModel,
                            toSignIn = { navController.popBackStack() },
                            register = {
                                signUpViewModel.register()
                                navController.popBackStack()
                            }
                        )
                    }
                    composable(mainScreen,
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(1000),

                                )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(1000)
                            )

                        }) {
                        BottomNavigationCompose(
                            forgotId = { forgetId(editor) },
                            navController,
                            vmStories,
                            signInViewModel = signInViewModel
                        )
                    }
                    composable(commentsScreen,
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(1000),
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(1000)
                            )

                        }) {

                        Comments(vmStories)
                    }

                }
            }
        }
    }
}
