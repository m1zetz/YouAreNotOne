package com.example.youarenotalone.ui.screens.bottomNav.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.youarenotalone.R
import com.example.youarenotalone.ui.screens.vms.Comments
import com.example.youarenotalone.ui.screens.vms.StoriesViewModel
import com.example.youarenotalone.ui.screens.vms.myUserId
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.blue
import com.example.youarenotalone.ui.theme.comicRelief
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.grayDarkDark
import com.example.youarenotalone.ui.theme.orange
import com.example.youarenotalone.ui.theme.pinkLight
import com.example.youarenotalone.ui.theme.white

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Comments(storiesViewModel: StoriesViewModel) {

    LaunchedEffect(Unit) {
        storiesViewModel.getComments()
    }

    var bottomBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLow)

    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 130.dp)
        ) {

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.tertiary),
                    Alignment.Center
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp)
                            .statusBarsPadding(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ExpandableCardForComments(
                            title = storiesViewModel.currentPostTitle.value,
                            text = storiesViewModel.currentPostText.value,
                        )
                        Log.d("msg", storiesViewModel.currentPostTitle.value)
                    }
                }
            }

            items(storiesViewModel.comments) { comments ->

                if (comments.post_id == storiesViewModel.currentPostId.value) {

                    CardComment(comments, storiesViewModel)

                }
            }


        }

    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {

        val navBarHeight =
            WindowInsets.navigationBars.getBottom(LocalDensity.current)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
            shape = RectangleShape
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = IntPixelsToDp(navBarHeight))) {


                OutlinedTextField(
                    value = storiesViewModel.comment,
                    onValueChange = { storiesViewModel.comment = it },
                    placeholder = { Text("Enter comment", color = gray, fontSize = 20.sp) },
                    textStyle = TextStyle(
                        color = white,
                        fontSize = 20.sp
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .weight(1f)


                )

                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "",
                    Modifier
                        .padding(20.dp)
                        .size(40.dp)
                        .clickable {
                            storiesViewModel.sendComment(
                                storiesViewModel.comment,
                                storiesViewModel.currentPostId.value ?: -1
                            )
                        },
                    tint = MaterialTheme.colorScheme.primary
                )

            }

        }
    }
}

@Composable
fun CardComment(comments: Comments, storiesViewModel: StoriesViewModel){
    Card(
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary
        )

    ) {
        Column {
            Row {
                Text(
                    comments.comment,
                    color = white,
                    fontFamily = comicRelief,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(15.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                val likeBorder = painterResource(R.drawable.like_border)
                val likefull = painterResource(R.drawable.like_full)
                var currentIcon: Painter

                var likes by remember { mutableStateOf<Int?>(-1) }
                var idLikesList = remember { mutableStateListOf<Int>() }


                val user_id = myUserId.value ?: -1
                if (idLikesList.contains(user_id)) {
                    currentIcon = likefull
                } else {
                    currentIcon = likeBorder
                }


                Icon(
                    currentIcon,
                    contentDescription = "",
                    tint = pinkLight,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable {                                                 //LIKE

                            if (!idLikesList.contains(user_id)) {
                                storiesViewModel.addLikeComment(
                                    comments.comment_id,
                                    user_id
                                )
                                idLikesList.add(user_id)
                                likes = (likes ?: 0) + 1
                            } else {
                                storiesViewModel.dropLikeComment(
                                    comments.comment_id,
                                    user_id
                                )
                                idLikesList.remove(user_id)
                                likes = (likes ?: 0) - 1
                            }
                        }
                )

                LaunchedEffect(comments.comment_id, user_id) {
                    storiesViewModel.getLikesComments(
                        comments.comment_id,
                        user_id
                    ) { count, idArray ->
                        likes = count
                        for (id in idArray) {
                            idLikesList.add(id)
                        }
                    }
                }

                Spacer(modifier = Modifier.size(5.dp))

                Text(
                    text = when (likes) {
                        null -> "0"
                        -1 -> "0"
                        else -> likes.toString()
                    },
                    fontSize = 20.sp,
                    fontFamily = comicRelief,
                    color = pinkLight
                )
            }
        }


    }
}