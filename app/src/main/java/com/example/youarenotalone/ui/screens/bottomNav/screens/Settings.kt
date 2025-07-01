package com.example.youarenotalone.ui.screens.bottomNav.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log

import androidx.compose.foundation.BorderStroke

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.youarenotalone.R
import com.example.youarenotalone.commentsScreen
import com.example.youarenotalone.getSavedLanguage
import com.example.youarenotalone.mainScreen
import com.example.youarenotalone.saveTheme
import com.example.youarenotalone.setLanguage
import com.example.youarenotalone.setTheme
import com.example.youarenotalone.signInScreen
import com.example.youarenotalone.ui.screens.vms.SettingsViewModel
import com.example.youarenotalone.ui.screens.vms.SignInViewModel
import com.example.youarenotalone.ui.screens.vms.myUserId
import com.example.youarenotalone.ui.theme.bgColor
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.checked
import com.example.youarenotalone.ui.theme.comicRelief
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.grayDark
import com.example.youarenotalone.ui.theme.grayDarkDark
import com.example.youarenotalone.ui.theme.grayPurpleDarkDark
import com.example.youarenotalone.ui.theme.orange
import com.example.youarenotalone.ui.theme.pain
import com.example.youarenotalone.ui.theme.pinkLight
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
    context: Context,
    useDarkTheme: Boolean
) {

    val settingsViewModel: SettingsViewModel = viewModel()
    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.tertiary)
                    ,
                    Alignment.Center
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, bottom = 20.dp, top = 20.dp)
                            .statusBarsPadding(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            stringResource(R.string.settings),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 45.sp
                        )
                        Text(
                            stringResource(R.string.app_settings),
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 22.sp
                        )
                    }

                }
            }

            item {
                Column(
                    modifier = Modifier
                        .padding()
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {



                    Card(
                        modifier = Modifier.padding(10.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ){

                            val activity = LocalContext.current as? Activity

                            DropDownLanguages(activity)

                            Spacer(modifier = Modifier.size(10.dp))

                            HorizontalDivider(
                                thickness = 3.dp,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            Text(
                                stringResource(R.string.themes), color = pinkLight, modifier = Modifier
                                    .clickable {
                                        settingsViewModel.themesBottomSheetState = true
                                    }
                                    .padding(horizontal = 10.dp),
                                fontSize = 30.sp,
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            HorizontalDivider(
                                thickness = 3.dp,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colorScheme.onSurface
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
                            )
                        }
                    }


                }


            }

        }


    }
    ThemesBottomSheet(settingsViewModel, context, useDarkTheme)
}




@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState", "ContextCastToActivity")
@Composable
fun ThemesBottomSheet(settingsViewModel: SettingsViewModel, context: Context, activeTheme: Boolean) {
    val activity = LocalContext.current as? Activity
    val sheetState = rememberModalBottomSheetState()

    settingsViewModel.stateOfDark = activeTheme
    settingsViewModel.stateOfLight = !activeTheme

    if(settingsViewModel.themesBottomSheetState){

        ModalBottomSheet(
            onDismissRequest = {
                settingsViewModel.themesBottomSheetState = false
            },
            sheetState = sheetState
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)) {

                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.light_theme),
                        fontSize = 40.sp)

                    Spacer(Modifier.size(20.dp))

                    Switch(
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = grayDark,
                            checkedTrackColor = checked,
                            uncheckedThumbColor = grayDark,
                            uncheckedTrackColor = grayDarkDark),

                        checked = settingsViewModel.stateOfLight,
                        onCheckedChange = { isChecked ->
                            settingsViewModel.stateOfLight  = isChecked

                            if (isChecked) {
                                settingsViewModel.stateOfLight = true
                                settingsViewModel.stateOfDark = false
                                saveTheme(context, false)
                            } else {

                                if (!settingsViewModel.stateOfDark) {
                                    settingsViewModel.stateOfLight = true

                                } else { // Если Dark theme была включена, то выключаем Light theme
                                    settingsViewModel.stateOfLight = false
                                    saveTheme(context, true)
                                }
                            }
                            activity?.recreate()
                        },
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(Modifier.size(10.dp))

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        stringResource(R.string.dark_theme),
                        fontSize = 40.sp
                    )

                    if (activeTheme){
                        settingsViewModel.stateOfDark = true
                    }
                    else {
                        settingsViewModel.stateOfDark = false
                    }

                    Spacer(Modifier.size(20.dp))

                    Switch(
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = grayDark,
                            checkedTrackColor = checked,
                            uncheckedThumbColor = grayDark,
                            uncheckedTrackColor = grayDarkDark),
                        checked = settingsViewModel.stateOfDark,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                settingsViewModel.stateOfDark = true
                                settingsViewModel.stateOfLight = false
                                saveTheme(context, true)
                            } else {

                                if (!settingsViewModel.stateOfLight) {
                                    settingsViewModel.stateOfDark = true

                                } else {

                                    settingsViewModel.stateOfDark = false
                                    saveTheme(context, false)
                                }
                            }
                            activity?.recreate()


                        },
                        modifier = Modifier.size(40.dp)
                    )
                }

            }
        }
    }
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
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            fontSize = 20.sp,
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
                    focusedBorderColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    focusedTextColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    unfocusedTextColor = MaterialTheme.colorScheme.surfaceContainerLowest
                ),
                textStyle = TextStyle(fontSize = 20.sp)
            )

            ExposedDropdownMenu(
                expanded = settingsViewModel.expanded.value,
                onDismissRequest = { settingsViewModel.expanded.value = false }
            ) {
                settingsViewModel.langs.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item, color = MaterialTheme.colorScheme.surfaceContainerLowest, fontSize = 20.sp) },
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
                                setLanguage(activity, langCode)


                                coroutineScope.launch {
                                    delay(100)
                                    activity.recreate()

                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

