package com.example.youarenotalone.ui.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.youarenotalone.R
import com.example.youarenotalone.ui.screens.vms.SignInViewModel
import com.example.youarenotalone.ui.screens.vms.myUserId
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.comicRelief
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.hunninFontFamily
import com.example.youarenotalone.ui.theme.listOfColorsSlogan
import com.example.youarenotalone.ui.theme.orange
import com.example.youarenotalone.ui.theme.red
import com.example.youarenotalone.ui.theme.white
import kotlinx.coroutines.delay
import kotlin.math.tan

@Composable
fun ScreenSignIn(
    signInViewModel: SignInViewModel,
    toSignUp: () -> Unit,
    toMain: () -> Unit,
    sharedPreferences: SharedPreferences
) {

    var tryLogin by remember { mutableStateOf(false) }
    var wrong by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val savedId = sharedPreferences.getInt("myId", -1)
        if (savedId > 0) {
            myUserId.value = savedId
            toMain()
        }
    }

    LaunchedEffect(tryLogin) {

        if (myUserId.value!! > 0){
            toMain()
            tryLogin = false
        }

        if (tryLogin) {
            wrong = false
            val result = signInViewModel.login()


            when {
                result == -2 -> wrong = true
                result > 0 -> {
                    myUserId.value = result
                    sharedPreferences.edit().putInt("myId", result).apply()
                    toMain()
                }
            }

            tryLogin = false

        }
    }


    Column(
        modifier = Modifier
            .background(color = black)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text(
            "You are not alone.",
            style = TextStyle(
                Brush.linearGradient(
                    colors = listOfColorsSlogan
                )
            ),
            fontSize = 40.sp,
            fontFamily = comicRelief,

            )


        if (wrong){
            Spacer(modifier = Modifier.size(20.dp))

            Text(
                stringResource(R.string.wrong),
                color = red,
                fontSize = 20.sp,
                fontFamily = comicRelief,
                )
        }

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = signInViewModel.messageLogin.value,
            onValueChange = { signInViewModel.messageLogin.value = it
                if (wrong) wrong = false},
            placeholder = { Text(stringResource(R.string.enter_login), color = gray) },
            textStyle = TextStyle(
                color = white
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = gray
            ),
            shape = RoundedCornerShape(15.dp)

        )

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = signInViewModel.messagePassword.value,
            onValueChange = { signInViewModel.messagePassword.value = it
                if (wrong) wrong = false},
            placeholder = { Text(stringResource(R.string.enter_password), color = gray) },
            textStyle = TextStyle(
                color = white
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = gray
            ),
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            stringResource(R.string.signin),
            Modifier.clickable {tryLogin = true},
            color = MaterialTheme.colorScheme.primary,
            fontFamily = comicRelief
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            stringResource(R.string.signup),
            Modifier.clickable { toSignUp() },
            color = MaterialTheme.colorScheme.primary,
            fontFamily = comicRelief
        )
    }
}

