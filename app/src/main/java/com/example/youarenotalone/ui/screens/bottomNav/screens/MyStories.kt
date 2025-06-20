package com.example.youarenotalone.ui.screens.bottomNav.screens

import com.example.youarenotalone.ui.drawTopBorder
import com.example.youarenotalone.ui.drawBottomBorder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Bottom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.youarenotalone.R
import com.example.youarenotalone.ui.screens.vms.MyStoriesViewModel
import com.example.youarenotalone.ui.screens.vms.myUserId
import com.example.youarenotalone.ui.theme.bgColor
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.comicRelief
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.grayDark
import com.example.youarenotalone.ui.theme.grayDarkDark
import com.example.youarenotalone.ui.theme.hunninFontFamily
import com.example.youarenotalone.ui.theme.orange
import com.example.youarenotalone.ui.theme.pain
import com.example.youarenotalone.ui.theme.white


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyStories(paddingValues: PaddingValues) {
    val myStoriesViewModel: MyStoriesViewModel = viewModel()
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
                            Text("My stories", fontFamily = comicRelief)
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
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(myStoriesViewModel.listOfMyStories) { card ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = grayDark
                        )
                    ) {

                        Column(Modifier.fillMaxWidth()) {
                            Text(
                                card.text.replaceFirstChar { it.uppercase() },
                                Modifier.padding(14.dp),
                                fontFamily = comicRelief,
                                color = white
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    painterResource(
                                        R.drawable.like_border
                                    ),
                                    contentDescription = "",
                                    tint = orange,
                                    modifier = Modifier.padding(14.dp)
                                )
                                Button(
                                    onClick = {},
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonColors(
                                        containerColor = gray,
                                        contentColor = grayDark,
                                        disabledContentColor = grayDark,
                                        disabledContainerColor = gray,
                                    ),
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text("helps for me")
                                }
                            }
                        }


                    }


                }

            }

        },
        floatingActionButton = {
            FloatingActionButton(
//                onClick = {myStoriesViewModel.addPost(myUserId.value ?: -1, "toyotaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" )},
                onClick = { myStoriesViewModel.stateOfBottomSheet.value = true },
                shape = CircleShape,
                modifier = Modifier.size(70.dp),
                containerColor = black
            ) {
                Icon(
                    painter = painterResource(R.drawable.plus),
                    tint = pain, contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
    AddBottomSheet(myStoriesViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBottomSheet(vmMyStories: MyStoriesViewModel) {

    var scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (vmMyStories.stateOfBottomSheet.value) {
        ModalBottomSheet(
            containerColor = grayDarkDark,
            modifier = Modifier.fillMaxHeight(1f),
            sheetState = sheetState,
            onDismissRequest = { vmMyStories.stateOfBottomSheet.value = false },
            content = {
                Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Spacer(modifier = Modifier.size(15.dp))

                    OutlinedTextField(
                        value = vmMyStories.textStory.value,
                        onValueChange = {
                            if (it.length <= 1000) {
                                vmMyStories.textStory.value = it
                            }
                        },
                        placeholder = { Text("Maximum length is 1000 characters", color = gray, fontSize = 20.sp, fontFamily = comicRelief) },
                        textStyle = TextStyle(
                            color = gray,
                            fontSize = 20.sp,
                            fontFamily = comicRelief
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = orange,
                            unfocusedBorderColor = gray
                        ),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()

                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = grayDark),
                        shape = CircleShape,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        onClick = {
                            vmMyStories.addPost(
                                user_id = myUserId.value ?: -1,
                                vmMyStories.textStory.value)
                        }) {
                        Text("Send story",
                            color = gray,
                            fontSize = 20.sp,
                            fontFamily = comicRelief)
                    }

                }

            }

        )
    }


}

