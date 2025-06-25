package com.example.youarenotalone.ui.screens.bottomNav.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.youarenotalone.R
import com.example.youarenotalone.getSavedLanguage
import com.example.youarenotalone.mainScreen
import com.example.youarenotalone.setLanguage
import com.example.youarenotalone.signInScreen
import com.example.youarenotalone.ui.screens.vms.SettingsViewModel
import com.example.youarenotalone.ui.screens.vms.SignInViewModel
import com.example.youarenotalone.ui.screens.vms.myUserId
import com.example.youarenotalone.ui.theme.bgColor
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.comicRelief
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.grayDark
import com.example.youarenotalone.ui.theme.orange
import com.example.youarenotalone.ui.theme.pain
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.annotation.meta.When


@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    forgotId: () -> Unit,
    paddingValues: PaddingValues,
    navController: NavController,
    signInViewModel: SignInViewModel,
    context: Context
) {
    Scaffold(
        containerColor = bgColor,
        topBar = {
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = black,
                        titleContentColor = orange
                    ),
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                stringResource(R.string.settings),
                                fontFamily = comicRelief,
                            )
                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(orange)
                )
            }


        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                val activity = LocalContext.current as? Activity
                DropDownLanguages(activity)

                Spacer(modifier = Modifier.size(10.dp))

                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = grayDark
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    stringResource(R.string.exit), color = pain, modifier = Modifier
                        .clickable {
                            myUserId.value = -1
                            forgotId()
                            navController.navigate(signInScreen) {
                                popUpTo(mainScreen) {
                                    inclusive = true
                                }
                            }
                        }
                        .padding(horizontal = 10.dp),
                    fontSize = 30.sp,
                    fontFamily = comicRelief
                )

            }

        }
    )
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownLanguages(activity: Activity?) {
    val settingsViewModel: SettingsViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        if (activity != null) {
            settingsViewModel.initializeSelectedLanguage(activity)
        }
    }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            "${stringResource(R.string.Language)}: ",
            color = orange,
            fontSize = 20.sp,
            fontFamily = comicRelief,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        ExposedDropdownMenuBox(
            expanded = settingsViewModel.expanded.value,
            onExpandedChange = {
                settingsViewModel.expanded.value = !settingsViewModel.expanded.value
            }
        ) {
            OutlinedTextField(
                value = settingsViewModel.selectedText.value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = settingsViewModel.expanded.value) },
                modifier = Modifier.menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = orange,
                    unfocusedBorderColor = gray,
                    focusedTextColor = orange,
                    unfocusedTextColor = orange
                )
            )

            ExposedDropdownMenu(
                expanded = settingsViewModel.expanded.value,
                onDismissRequest = { settingsViewModel.expanded.value = false }
            ) {
                settingsViewModel.langs.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            settingsViewModel.selectedText.value = item
                            settingsViewModel.expanded.value = false

                            val langCode = when(item){
                                "English" -> "en"
                                "Русский" -> "ru"
                                "Қазақ" -> "kk"
                                else -> "en"
                            }


                            if (activity != null) {
                                setLanguage(activity, langCode) // Устанавливаем язык

                                // *** Добавляем задержку перед recreate() ***
                                coroutineScope.launch {
                                    delay(100) // Попробуйте 100-200 мс
                                    activity.recreate()

                                }
                            } else {

                            }
                        }
                    )
                }
            }
        }
    }
}