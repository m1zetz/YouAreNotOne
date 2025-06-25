package com.example.youarenotalone.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.youarenotalone.R
import com.example.youarenotalone.ui.screens.vms.SignUpViewModel
import com.example.youarenotalone.ui.theme.Purple40
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.comicRelief
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.hunninFontFamily
import com.example.youarenotalone.ui.theme.listOfColorsSlogan
import com.example.youarenotalone.ui.theme.orange

@Composable
fun ScreenSignUp(
    signUpViewModel: SignUpViewModel,
    toSignIn: () -> Unit,
    register: () -> Unit
) {

    Column(
        modifier = Modifier
            .background(color = black)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "We can help you.",
            style = TextStyle(
                Brush.linearGradient(
                    colors = listOfColorsSlogan
                )
            ),
            fontSize = 40.sp,
            fontFamily = comicRelief
        )


        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = signUpViewModel.messageLogin.value,
            onValueChange = { signUpViewModel.messageLogin.value = it },
            placeholder = { Text(stringResource(R.string.enter_login), color = gray) },
            textStyle = TextStyle(
                color = gray
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = orange,
                unfocusedBorderColor = gray
            ),
            shape = RoundedCornerShape(15.dp)

        )

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = signUpViewModel.messagePassword.value,
            onValueChange = { signUpViewModel.messagePassword.value = it },
            placeholder = { Text(stringResource(R.string.enter_password), color = gray) },
            textStyle = TextStyle(
                color = gray
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = orange,
                unfocusedBorderColor = gray
            ),
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            stringResource(R.string.signup),
            Modifier.clickable { register() },
            color = orange,
            fontFamily = comicRelief
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            stringResource(R.string.signin),
            Modifier.clickable { toSignIn() },
            color = orange,
            fontFamily = comicRelief
        )
    }

}









