package com.example.youarenotalone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.youarenotalone.ui.screens.vms.BottomNavigationViewModel

import com.example.youarenotalone.ui.screens.vms.SignInViewModel
import com.example.youarenotalone.ui.screens.vms.SignUpViewModel
import com.example.youarenotalone.ui.screens.vms.StoriesViewModel

import com.example.youarenotalone.ui.theme.YouAreNotAloneTheme
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.orange

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YouAreNotAloneTheme {
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
                        val signInViewModel: SignInViewModel = viewModel()
                        ScreenSignIn(
                            signInViewModel = signInViewModel,
                            toSignUp = { navController.navigate(signUpScreen) },
                            login = {
                                signInViewModel.login()
                                navController.navigate(mainScreen)
                            }
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
                                signUpViewModel.register(

                                )
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
                        BottomNavigationCompose(navController, vmStories )
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
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(1000)
                            )

                        }){

                        Comments(vmStories)
                    }

                }
            }
        }
    }
}
