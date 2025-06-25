package com.example.youarenotalone.ui.screens.bottomNav.screens


import ads_mobile_sdk.h6
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.OutlinedButton
import androidx.compose.material.Shapes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.tv.material3.OutlinedButtonDefaults
import com.example.youarenotalone.R
import com.example.youarenotalone.commentsScreen
import com.example.youarenotalone.ui.screens.vms.MyStoriesViewModel
import com.example.youarenotalone.ui.screens.vms.StoriesViewModel
import com.example.youarenotalone.ui.theme.bgColor
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.blue
import com.example.youarenotalone.ui.theme.comicRelief
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.grayDark
import com.example.youarenotalone.ui.theme.grayDarkDark
import com.example.youarenotalone.ui.theme.grayWh
import com.example.youarenotalone.ui.theme.hunninFontFamily
import com.example.youarenotalone.ui.theme.orange
import com.example.youarenotalone.ui.theme.white


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Stories(navController: NavController, storiesViewModel: StoriesViewModel) {

    storiesViewModel.getStories()
    storiesViewModel.getComments()


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
                            Text(stringResource(R.string.stories), fontFamily = comicRelief)
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
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(storiesViewModel.listOfStories) { card ->

                    ExpandableCard(
                        title = card.title,
                        text = card.text,
                        saveCurrentPostId = {
                            storiesViewModel.currentPostTitle.value = card.title
                            storiesViewModel.currentPostText.value = card.text
                            storiesViewModel.currentPostId.value = card.post_id
                        },
                        toCommentsScreen = {
                            navController.navigate(commentsScreen)
                        }

                    )


                }

            }

        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Comments(storiesViewModel: StoriesViewModel) {

    LaunchedEffect(Unit) {
        storiesViewModel.getComments()
    }


    Column(
        Modifier
            .fillMaxSize()
            .background(grayDarkDark)
    ) {
        ExpandableCardForComments(
            title = storiesViewModel.currentPostTitle.value,
            text = storiesViewModel.currentPostText.value,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
        ) {

            items(storiesViewModel.comments) { comments ->

                if (comments.post_id == storiesViewModel.currentPostId.value) {

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .padding(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = blue
                        )

                    ) {
                        Text(
                            comments.comment,
                            color = white,
                            fontFamily = comicRelief,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .padding(5.dp)
                        )
                    }


                }
            }

        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = black),
            shape = RectangleShape
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                val navBarHeight = WindowInsets.navigationBars.getBottom(LocalDensity.current)
                OutlinedTextField(
                    value = storiesViewModel.comment,
                    onValueChange = { storiesViewModel.comment = it },
                    placeholder = { Text("Enter login", color = gray) },
                    textStyle = TextStyle(
                        color = gray
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = orange,
                        unfocusedBorderColor = gray
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = IntPixelsToDp(navBarHeight))


                )

                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()

                val borderColor by animateColorAsState(
                    targetValue = if (isPressed) orange else gray,
                    animationSpec = tween(durationMillis = 10),
                    label = "border color animation"
                )

                OutlinedButton(
                    onClick = {
                        storiesViewModel.sendComment(
                            storiesViewModel.comment,
                            storiesViewModel.currentPostId.value ?: -1
                        )
                    },
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(3.dp, borderColor),
                    modifier = Modifier.padding(
                        bottom = IntPixelsToDp(navBarHeight)
                    ).background(grayDarkDark),
                    interactionSource = interactionSource,
                    colors = ButtonColors(
                        containerColor = black,
                        disabledContainerColor = black,
                        contentColor = orange,
                        disabledContentColor = orange)

                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "", Modifier.size(40.dp), tint = orange)
                }
            }

        }
    }

}

@Composable
fun IntPixelsToDp(px: Int): Dp {
    val density = LocalDensity.current
    return with(density) { px.toDp() }
}


@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    title: String,
    text: String,
    toCommentsScreen: () -> Unit,
    saveCurrentPostId: () -> Unit
) {

    var expandedState by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = TweenSpec(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(10.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            expandedState = !expandedState


        },
        colors = CardDefaults.cardColors(containerColor = grayDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f)
                        .padding(start = 10.dp, end = 10.dp),
                    text = title.replaceFirstChar { it.uppercase() },
                    fontSize = 25.sp,
                    maxLines = 1,
                    color = orange,
                    fontFamily = comicRelief
                )
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop Down Arrow",
                        tint = orange
                    )
                }
            }
            if (expandedState) {
                HorizontalDivider(thickness = 2.dp, color = grayWh, modifier = Modifier.padding(10.dp))
                Text(
                    text.replaceFirstChar { it.uppercase() },
                    Modifier.padding(10.dp),
                    fontFamily = comicRelief,
                    color = white
                )
                HorizontalDivider(thickness = 2.dp, color = grayWh, modifier = Modifier.padding(10.dp))

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
                        modifier = Modifier
                            .padding(14.dp)
                            .clickable { }
                    )
                    Button(
                        onClick = {
                            saveCurrentPostId()
                            toCommentsScreen()
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonColors(
                            containerColor = gray,
                            contentColor = grayDark,
                            disabledContentColor = grayDark,
                            disabledContainerColor = gray,
                        ),
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(stringResource(R.string.help))
                    }
                }
            }
        }
    }

}

@ExperimentalMaterialApi
@Composable
fun ExpandableCardForComments(
    title: String,
    text: String,
) {

    var expandedState by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = TweenSpec(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(10.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            expandedState = !expandedState


        },
        colors = CardDefaults.cardColors(containerColor = grayDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f)
                        .padding(start = 10.dp, end = 10.dp),
                    text = title.replaceFirstChar { it.uppercase() },
                    fontSize = 25.sp,
                    maxLines = 1,
                    color = orange,
                    fontFamily = comicRelief
                )
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop Down Arrow",
                        tint = orange
                    )
                }
            }
            if (expandedState) {
                Text(
                    text.replaceFirstChar { it.uppercase() },
                    Modifier.padding(10.dp),
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
                        modifier = Modifier
                            .padding(14.dp)
                            .clickable { }
                    )
                }
            }
        }
    }

}