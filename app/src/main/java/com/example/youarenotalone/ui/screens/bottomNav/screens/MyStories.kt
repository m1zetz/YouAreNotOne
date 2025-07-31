package com.example.youarenotalone.ui.screens.bottomNav.screens

import ads_mobile_sdk.h6
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import com.example.youarenotalone.ui.drawTopBorder
import com.example.youarenotalone.ui.drawBottomBorder
import androidx.compose.material.Typography

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Shapes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.shape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.youarenotalone.R
import com.example.youarenotalone.commentsScreen
import com.example.youarenotalone.ui.screens.vms.MyStoriesViewModel
import com.example.youarenotalone.ui.screens.vms.StoriesViewModel
import com.example.youarenotalone.ui.screens.vms.myUserId
import com.example.youarenotalone.ui.theme.bgColor
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.comicRelief
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.grayDark
import com.example.youarenotalone.ui.theme.grayDarkDark
import com.example.youarenotalone.ui.theme.grayWh
import com.example.youarenotalone.ui.theme.hunninFontFamily
import com.example.youarenotalone.ui.theme.orange
import com.example.youarenotalone.ui.theme.pain
import com.example.youarenotalone.ui.theme.pinkLight
import com.example.youarenotalone.ui.theme.white


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MyStories(storiesViewModel: StoriesViewModel, navController: NavController) {
    val myStoriesViewModel: MyStoriesViewModel = viewModel()
    myStoriesViewModel.getMyStories()




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
                            stringResource(R.string.my_stories),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 45.sp
                        )
                        Text(
                            stringResource(R.string.myStroryOnly),
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 22.sp
                        )
                    }


                }

            }

            items(myStoriesViewModel.listOfMyStories) { card ->

                MyExpandableCard(
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
                    user_id = card.user_id,
                    myStoriesViewModel = myStoriesViewModel
                )


            }

        }
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 110.dp)
                .padding(end = 10.dp)
                .navigationBarsPadding(),
            verticalArrangement = Bottom,
            horizontalAlignment = Alignment.End
        ) {
            FloatingActionButton(
                onClick = { myStoriesViewModel.stateOfBottomSheet.value = true },
                shape = CircleShape,
                modifier = Modifier.size(90.dp),
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(
                    painter = painterResource(R.drawable.plus),
                    tint = MaterialTheme.colorScheme.surfaceContainerLowest, contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
        }


    }





    AddBottomSheet(myStoriesViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBottomSheet(vmMyStories: MyStoriesViewModel) {

    var scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (vmMyStories.stateOfBottomSheet.value) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.fillMaxHeight(1f),
            sheetState = sheetState,
            onDismissRequest = { vmMyStories.stateOfBottomSheet.value = false },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        stringResource(R.string.describe),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Spacer(modifier = Modifier.size(15.dp))

                    OutlinedTextField(
                        value = vmMyStories.titleStory.value,
                        onValueChange = {
                            if (it.length <= 40) {
                                vmMyStories.titleStory.value = it
                            }
                        },
                        placeholder = {
                            Text(
                                stringResource(R.string.title),
                                color = gray,
                                fontSize = 20.sp,
                            )
                        },
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            fontSize = 20.sp,
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                            unfocusedTextColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        ),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()

                    )

                    Spacer(modifier = Modifier.size(15.dp))

                    OutlinedTextField(
                        value = vmMyStories.textStory.value,
                        onValueChange = {
                            if (it.length <= 1000) {
                                vmMyStories.textStory.value = it
                            }
                        },
                        placeholder = {
                            Text(
                                stringResource(R.string.story),
                                color = gray,
                                fontSize = 20.sp,
                            )
                        },
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            fontSize = 20.sp,
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                            unfocusedTextColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        ),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()

                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceBright),
                        shape = CircleShape,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        onClick = {
                            vmMyStories.addPost(
                                user_id = myUserId.value ?: -1,
                                vmMyStories.titleStory.value,
                                vmMyStories.textStory.value
                            )
                        }) {
                        Text(
                            stringResource(R.string.send_story),
                            color = MaterialTheme.colorScheme.surfaceTint,
                            fontSize = 20.sp,
                        )
                    }

                }

            }

        )
    }


}
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun miFE(storiesViewModel: StoriesViewModel, navController: NavController){
    val myStoriesViewModel: MyStoriesViewModel = viewModel()

    myStoriesViewModel.getMyStories()
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
                            Text(stringResource(R.string.my_stories), fontFamily = comicRelief)
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
                items(myStoriesViewModel.listOfMyStories) { card ->

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
                        commentsButtonString = stringResource(R.string.view_comments),
                        storiesViewModel = storiesViewModel,
                        post_id = card.post_id,
                        user_id = card.user_id

                    )


                }

            }

        },
        floatingActionButton = {

        }
    )
}

@ExperimentalMaterialApi
@Composable
fun MyExpandableCard(
    storiesViewModel: StoriesViewModel,
    title: String,
    text: String,
    toCommentsScreen: () -> Unit,
    saveCurrentPostId: () -> Unit,
    commentsButtonString: String,
    post_id: Int,
    user_id: Int,
    myStoriesViewModel: MyStoriesViewModel
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
                            null -> "0"
                            -1 -> "0"
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

                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = "",
                        tint = pinkLight,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .clickable {                                                 //LIKE
                                myStoriesViewModel.drop_my_story(post_id)
                            }
                    )

                }
            }
        }
    }

}


