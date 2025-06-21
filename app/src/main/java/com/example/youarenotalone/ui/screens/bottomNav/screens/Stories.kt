package com.example.youarenotalone.ui.screens.bottomNav.screens



import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.youarenotalone.R
import com.example.youarenotalone.ui.screens.vms.StoriesViewModel
import com.example.youarenotalone.ui.theme.bgColor
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.comicRelief
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.grayDark
import com.example.youarenotalone.ui.theme.hunninFontFamily
import com.example.youarenotalone.ui.theme.orange
import com.example.youarenotalone.ui.theme.white


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Stories(paddingValues: PaddingValues) {
    val storiesViewModel: StoriesViewModel = viewModel()

    storiesViewModel.getStories()

//    storiesViewModel.addPost(5, "my day is very good")

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
                            Text("Stories", fontFamily = comicRelief)
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
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(storiesViewModel.listOfStories) { card ->

                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = grayDark
                    )) {

                        Column(Modifier.fillMaxWidth()) {
                            Text(card.text.replaceFirstChar { it.uppercase()  }, Modifier.padding(14.dp),
                                fontFamily = comicRelief,
                                color = white)

                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start){
                                Icon(painterResource(
                                    R.drawable.like_border),
                                    contentDescription = "",
                                    tint = orange,
                                    modifier = Modifier.padding(14.dp))
                                Button(
                                    onClick = {},
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonColors(
                                        containerColor = gray,
                                        contentColor = grayDark,
                                        disabledContentColor = grayDark,
                                        disabledContainerColor = gray,
                                    ),
                                    modifier = Modifier.padding(10.dp)) {
                                    Text("help a person")
                                }
                            }
                        }


                    }


                }

            }

        }
    )
}