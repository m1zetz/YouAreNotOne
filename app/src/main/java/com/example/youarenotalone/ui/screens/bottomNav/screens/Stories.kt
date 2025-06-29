package com.example.youarenotalone.ui.screens.bottomNav.screens


import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.shapes.Shape
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.youarenotalone.R
import com.example.youarenotalone.commentsScreen
import com.example.youarenotalone.shadowCustom
import com.example.youarenotalone.ui.screens.vms.StoriesViewModel
import com.example.youarenotalone.ui.screens.vms.myUserId
import com.example.youarenotalone.ui.theme.alphaBlack
import com.example.youarenotalone.ui.theme.alphaWhiteBlue
import com.example.youarenotalone.ui.theme.alphaWhitePink
import com.example.youarenotalone.ui.theme.bgColor
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.blue
import com.example.youarenotalone.ui.theme.blueDark
import com.example.youarenotalone.ui.theme.buttonComments
import com.example.youarenotalone.ui.theme.buttonText
import com.example.youarenotalone.ui.theme.comicRelief
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.grayDark
import com.example.youarenotalone.ui.theme.grayDarkDark
import com.example.youarenotalone.ui.theme.grayPurple
import com.example.youarenotalone.ui.theme.grayPurpleDark
import com.example.youarenotalone.ui.theme.grayPurpleLight
import com.example.youarenotalone.ui.theme.grayWh
import com.example.youarenotalone.ui.theme.hordiv
import com.example.youarenotalone.ui.theme.listOfCardColors
import com.example.youarenotalone.ui.theme.orange
import com.example.youarenotalone.ui.theme.pain
import com.example.youarenotalone.ui.theme.pinkLight
import com.example.youarenotalone.ui.theme.white
import com.example.youarenotalone.ui.theme.zagolovok
import kotlin.io.path.Path
import kotlin.random.Random


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Stories(navController: NavController, storiesViewModel: StoriesViewModel) {

    storiesViewModel.getStories()
    storiesViewModel.getComments()


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
                            stringResource(R.string.stories),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 45.sp
                        )
                        Text(
                            stringResource(R.string.anonymous),
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 22.sp
                        )
                    }

                }
            }

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
                    },
                    commentsButtonString = stringResource(R.string.help),
                    storiesViewModel = storiesViewModel,
                    post_id = card.post_id,
                    user_id = card.user_id
                )


            }

        }


    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Comments(storiesViewModel: StoriesViewModel) {

    LaunchedEffect(Unit) {
        storiesViewModel.getComments()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = black,
                        titleContentColor = orange
                    ),
                    title = {},
                )
                HorizontalDivider(color = gray, thickness = 3.dp)
            }

            ExpandableCardForComments(
                title = storiesViewModel.currentPostTitle.value,
                text = storiesViewModel.currentPostText.value,
            )

        },
        content = { paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .background(grayDarkDark)
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding()
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
                        val navBarHeight =
                            WindowInsets.navigationBars.getBottom(LocalDensity.current)
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
                            modifier = Modifier
                                .padding(
                                    bottom = IntPixelsToDp(navBarHeight)
                                )
                                .background(grayDarkDark),
                            interactionSource = interactionSource,
                            colors = ButtonColors(
                                containerColor = black,
                                disabledContainerColor = black,
                                contentColor = orange,
                                disabledContentColor = orange
                            )

                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "",
                                Modifier.size(40.dp),
                                tint = orange
                            )
                        }
                    }

                }
            }
        }
    )


}

@Composable
fun IntPixelsToDp(px: Int): Dp {
    val density = LocalDensity.current
    return with(density) { px.toDp() }
}


@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    storiesViewModel: StoriesViewModel,
    title: String,
    text: String,
    toCommentsScreen: () -> Unit,
    saveCurrentPostId: () -> Unit,
    commentsButtonString: String,
    post_id: Int,
    user_id: Int
) {

    var expandedState by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    val randomIndex = Random.nextInt(0, 12)

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
        shape = RoundedCornerShape(25.dp),
        onClick = {
            expandedState = !expandedState
        },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
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
                    color = white,
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
                        tint = MaterialTheme.colorScheme.surfaceBright,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            if (expandedState) {
                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text.replaceFirstChar { it.uppercase() },
                    Modifier.padding(10.dp),
                    fontFamily = comicRelief,
                    color = white
                )
                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp)
                )

                val likeBorder = painterResource(R.drawable.like_border)
                val likefull = painterResource(R.drawable.like_full)
                var currentIcon: Painter

                var likes by remember { mutableStateOf<Int?>(-1) }
                var idLikesList = remember { mutableStateListOf<Int>() }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    val userId = myUserId.value ?: -1
                    if (idLikesList.contains(userId)) {
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
                                val userId = myUserId.value ?: return@clickable
                                if (!idLikesList.contains(userId)) {
                                    storiesViewModel.addLike(post_id, userId)
                                    idLikesList.add(userId)
                                    likes = (likes ?: 0) + 1
                                } else {
                                    storiesViewModel.dropLike(post_id, userId)
                                    idLikesList.remove(userId)
                                    likes = (likes ?: 0) - 1
                                }
                            }
                    )



                    LaunchedEffect(post_id, user_id) {
                        storiesViewModel.getLikes(post_id, user_id) { count, idArray ->
                            likes = count
                            for (id in idArray) {
                                idLikesList.add(id)
                            }
                        }
                    }

                    Text(
                        text = when (likes) {
                            null -> "Загрузка..."
                            -1 -> "Ошибка"
                            else -> likes.toString()
                        },
                        modifier = Modifier.padding(horizontal = 5.dp),
                        fontSize = 20.sp,
                        fontFamily = comicRelief,
                        color = pinkLight
                    )

                    Button(
                        onClick = {
                            saveCurrentPostId()
                            toCommentsScreen()
                        },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.onSurface,
                            contentColor = MaterialTheme.colorScheme.surfaceTint,
                            disabledContentColor = MaterialTheme.colorScheme.surfaceTint,
                            disabledContainerColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        modifier = Modifier.padding(start = 5.dp)
                    ) {
                        Text(commentsButtonString, fontSize = 14.sp)
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
            .padding(horizontal = 10.dp, vertical = 30.dp),
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


            }
        }
    }

}