package com.example.youarenotalone.ui.screens.bottomNav.screens

import com.example.youarenotalone.ui.drawTopBorder
import com.example.youarenotalone.ui.drawBottomBorder

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.youarenotalone.mainScreen
import com.example.youarenotalone.signInScreen
import com.example.youarenotalone.ui.screens.vms.SignInViewModel
import com.example.youarenotalone.ui.screens.vms.myUserId
import com.example.youarenotalone.ui.theme.bgColor
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.comicRelief
import com.example.youarenotalone.ui.theme.hunninFontFamily
import com.example.youarenotalone.ui.theme.orange


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    forgotId: () -> Unit,
    paddingValues: PaddingValues,
    navController: NavController,
    signInViewModel: SignInViewModel
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
                                "Settings",
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
                    .padding(paddingValues)     // padding от внешнего Scaffold (из аргумента)
                    .padding(innerPadding)      // padding от topBar
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    myUserId.value = -1
                    forgotId()
                    navController.navigate(signInScreen){
                        popUpTo(mainScreen){
                            inclusive = true
                        }
                    }
                }) {
                    Text("Exit", color = orange)
                }

            }

        }
    )
}