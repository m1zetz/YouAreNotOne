package com.example.youarenotalone

import android.annotation.SuppressLint
import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.youarenotalone.ui.screens.ScreenSignIn
import com.example.youarenotalone.ui.screens.ScreenSignUp
import com.example.youarenotalone.ui.screens.bottomNav.BottomNavViewModel
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
import com.example.youarenotalone.ui.theme.white
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        // Получаем сохраненный язык очень рано в жизненном цикле
        val languageCode = getSavedLanguage(newBase) ?: "en"
        val config = Configuration(newBase.resources.configuration)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Для API 33+ LocaleManager.applicationLocales должен работать
            // Но мы все равно можем убедиться, что Configuration Activity тоже установлена.
            config.setLocales(LocaleList.forLanguageTags(languageCode))

        } else {
            // Для API < 33 используем LocaleListCompat
            config.setLocale(LocaleListCompat.forLanguageTags(languageCode).get(0))

        }

        // Применяем изменения конфигурации к базовому контексту
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)

        // Убедимся, что AppCompatDelegate тоже обновлен.
        // Это может быть избыточно, но гарантирует синхронизацию.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))

        }



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


    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current
            var theme = setTheme(context)
            var useDarkTheme by remember { mutableStateOf(theme) }
            saveTheme(context, theme)

            YouAreNotAloneTheme(darkTheme = useDarkTheme) {


                val sharedPreferences =
                    context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                if (myUserId.value!! < 0) {
                    loadId(sharedPreferences)
                }
                var screenStartDestination = signInScreen

                if(myUserId.value!! != -1){
                    screenStartDestination = mainScreen
                }



                val signInViewModel: SignInViewModel = viewModel()
                val vmStories: StoriesViewModel = viewModel()
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = screenStartDestination,
                    Modifier.background(color = black),

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
                            toMain = { navController.navigate(mainScreen){
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            } },
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
                        val bottomNavViewModel: BottomNavViewModel = viewModel()
                        BottomNavigationCompose(
                            forgotId = { forgetId(editor) },
                            navController,
                            vmStories,
                            signInViewModel = signInViewModel,
                            context = context,
                            useDarkTheme = useDarkTheme,
                            bottomNavViewModel = bottomNavViewModel
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

fun getSavedLanguage(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
    val savedLang = sharedPreferences.getString("language", null)
    return savedLang
}

fun saveLanguage(context: Context, languageCode: String) {
    val sharedPreferences = context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("language", languageCode).apply()
}

fun setLanguage(context: Context, languageCode: String) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java)
            .applicationLocales = LocaleList.forLanguageTags(languageCode)

    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))

    }
    saveLanguage(context, languageCode)

}




fun saveTheme(context: Context, theme: Boolean) {
    val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean("theme", theme).apply()
}
fun setTheme(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    val savedTheme = sharedPreferences.getBoolean("theme", false)
    return savedTheme
}