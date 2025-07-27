package com.example.youarenotalone.ui.screens.bottomNav

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.youarenotalone.R
import com.example.youarenotalone.my_stories
import com.example.youarenotalone.settings
import com.example.youarenotalone.stories
import com.example.youarenotalone.ui.screens.bottomNav.screens.MyStories
import com.example.youarenotalone.ui.screens.bottomNav.screens.Settings
import com.example.youarenotalone.ui.screens.bottomNav.screens.Stories
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity

import androidx.navigation.NavController
import com.example.youarenotalone.ui.screens.vms.SignInViewModel
import com.example.youarenotalone.ui.screens.vms.StoriesViewModel
import com.example.youarenotalone.ui.theme.transparentWhite
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationCompose(
    forgotId: () -> Unit, navController: NavController,
    storiesViewModel: StoriesViewModel,
    signInViewModel:
    SignInViewModel,
    context: Context,
    useDarkTheme: Boolean,
    bottomNavViewModel: BottomNavViewModel
) {
    val coordinatesState = bottomNavViewModel.isCoordinatesLoaded.collectAsState()
    val offset = bottomNavViewModel.currentOffset.collectAsState()
    val currentBottomItem = bottomNavViewModel.currentBottomItem.collectAsState()
    val isCoordinatesLoaded = bottomNavViewModel.isCoordinatesLoaded.collectAsState()
    val firstOffset = bottomNavViewModel.currentFirstOffset.collectAsState()

    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState()
    val listItems = listOf(
        BottomItem(R.drawable.broken_image, stories, BottomNavViewModel.Screens.STORIES),
        BottomItem(R.drawable.dozd, my_stories, BottomNavViewModel.Screens.MY_STORIES),
        BottomItem(R.drawable.settings, settings, BottomNavViewModel.Screens.SETTINGS)
    )


    Scaffold(
        containerColor = Color.Transparent,
    ) { paddingValues ->

        HorizontalPager(
            count = listItems.size,
            state = pageState,

            ) { page ->
            when (page) {
                stories -> Stories(navController, storiesViewModel)
                my_stories -> MyStories(storiesViewModel, navController)
                settings -> Settings(
                    forgotId = { forgotId() },
                    paddingValues,
                    navController,
                    signInViewModel,
                    context,
                    useDarkTheme
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {

            BottomNavigationBar(
                currentScreen = pageState.currentPage,
                items = listItems,
                isCoordinatesLoaded = isCoordinatesLoaded.value,
                bottomNavViewModel = bottomNavViewModel,
                offset = offset.value?: Offset.Zero,
                onItemClick = { index ->
                    scope.launch {
                        pageState.animateScrollToPage(index)
                    }
                },
                firstOffset = firstOffset.value?: Offset.Zero
            )


        }


    }

}


data class BottomItem(
    val iconId: Int,
    val route: Int,
    val screenState: BottomNavViewModel.Screens
)

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun BottomNavigationBar(
    items: List<BottomItem> = listOf(),
    currentScreen: Int,
    onItemClick: (Int) -> Unit,
    isCoordinatesLoaded: Boolean,
    bottomNavViewModel: BottomNavViewModel,
    offset: Offset,
    firstOffset: Offset,
) {
    val density = LocalDensity.current

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {


        Box(
            modifier = Modifier.fillMaxSize(),
            Alignment.BottomStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(MaterialTheme.colorScheme.tertiary),
            ) {
                var animateOffset: State<Offset>
                if (firstOffset != Offset.Zero) {
                    if (offset != null){
                        animateOffset = animateOffsetAsState(
                            targetValue = offset,
                            animationSpec = tween(300)
                        )
                    }
                    else{
                        animateOffset = animateOffsetAsState(
                            targetValue = firstOffset,
                            animationSpec = tween(300)
                        )
                    }



                    Box(
                        Modifier
                            .offset(
                                x = with(density) { animateOffset.value.x.toDp() - 11.dp },
                                y = 17.dp
                            )
                            .size(50.dp)
                            .clip(
                                shape = RoundedCornerShape(25.dp)
                            )
                            .background(transparentWhite)
                    )
                }

                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(84.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        items.forEachIndexed { index, item ->
                            Icon(
                                painter = painterResource(id = item.iconId),
                                contentDescription = null,
                                tint = if (currentScreen == index) MaterialTheme.colorScheme.surfaceContainerLowest else MaterialTheme.colorScheme.background,
                                modifier = Modifier
                                    .size(28.dp)
                                    .onGloballyPositioned { coordinates ->
                                        if (!isCoordinatesLoaded && index == 0){
                                            bottomNavViewModel.changeFirstButtonOffset(coordinates.positionInParent())
                                        }
                                        if (currentScreen == index) {
                                            bottomNavViewModel.changeOffset(coordinates.positionInParent())
                                        }

                                    }
                                    .clickable(
                                        interactionSource = bottomNavViewModel.interactionSourceInternal,
                                        indication = null
                                    ) {
                                        onItemClick(index)
                                        bottomNavViewModel.changeCurrentBottomItem(item.screenState)
                                        bottomNavViewModel.changeIsLoadedOnTrue()
                                    }

                            )
                        }
                    }


                }

            }
        }

    }


}