package com.example.youarenotalone.ui.screens.bottomNav

import com.example.youarenotalone.ui.drawTopBorder
import com.example.youarenotalone.ui.drawBottomBorder

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.youarenotalone.R
import com.example.youarenotalone.my_stories
import com.example.youarenotalone.settings
import com.example.youarenotalone.stories
import com.example.youarenotalone.ui.screens.bottomNav.screens.MyStories
import com.example.youarenotalone.ui.screens.bottomNav.screens.Settings
import com.example.youarenotalone.ui.screens.bottomNav.screens.Stories
import com.example.youarenotalone.ui.theme.black
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.orange
import com.example.youarenotalone.ui.theme.pain
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.youarenotalone.shadowCustom
import com.example.youarenotalone.ui.screens.vms.SignInViewModel
import com.example.youarenotalone.ui.screens.vms.Stories
import com.example.youarenotalone.ui.screens.vms.StoriesViewModel
import com.example.youarenotalone.ui.theme.alphaWhiteBlue
import com.example.youarenotalone.ui.theme.alphaWhitePink
import com.example.youarenotalone.ui.theme.white
import com.example.youarenotalone.ui.theme.zagolovok
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationCompose(
    forgotId: () -> Unit, navController: NavController,
    storiesViewModel: StoriesViewModel,
    signInViewModel:
    SignInViewModel,
    context: Context
) {

    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState()
    val listItems = listOf(
        BottomItem(R.drawable.broken_image, stories),
        BottomItem(R.drawable.dozd, my_stories),
        BottomItem(R.drawable.settings, settings)
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
                    context
                )
            }
        }
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom) {
            BottomNavigationBar(
                currentScreen = pageState.currentPage,
                items = listItems
            ) {
                scope.launch {
                    pageState.animateScrollToPage(it)
                }

            }
        }



    }

}


data class BottomItem(
    val iconId: Int,
    val route: Int,
)

@Composable
fun BottomNavigationBar(
    items: List<BottomItem> = listOf(),
    currentScreen: Int,
    onItemClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .navigationBarsPadding()
            .padding(bottom = 16.dp)
//            .shadowCustom(
//                color = black.copy(alpha = 0.2f), // Цвет тени (полупрозрачный черный)
//                offsetX = 0.dp, // Смещение по X (отступ вправо)
//                offsetY = 0.dp, // Смещение по Y (отступ вниз)
//                blurRadius = 15.dp, // Радиус размытия тени
//                shapeRadius = 30.dp // Радиус скругления для тени (0.dp для прямоугольной)
//            )
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.tertiary),
        Alignment.Center
    ) {
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.fillMaxWidth().height(84.dp),
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
                            .clickable { onItemClick(index) }
                    )
                }
            }
        }

    }

}


