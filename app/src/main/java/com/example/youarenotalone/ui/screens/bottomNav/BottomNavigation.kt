package com.example.youarenotalone.ui.screens.bottomNav

import com.example.youarenotalone.ui.drawTopBorder
import com.example.youarenotalone.ui.drawBottomBorder

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
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

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationCompose() {

    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState()
    val listItems = listOf(
        BottomItem(R.drawable.broken_image, stories),
        BottomItem(R.drawable.dozd, my_stories),
        BottomItem(R.drawable.settings, settings)
    )


    Scaffold(
        containerColor = black,
        bottomBar = {
            BottomNavigationBar(
                currentScreen = pageState.currentPage,
                items = listItems
            ) {
                scope.launch {
                    pageState.animateScrollToPage(it)
                }

            }
        }
    ) { paddingValues ->

        HorizontalPager(
            count = listItems.size,
            state = pageState,
            modifier = Modifier.padding(paddingValues),

            ) { page ->
            when (page) {
                stories -> Stories(paddingValues)
                my_stories -> MyStories(paddingValues)
                settings -> Settings(paddingValues)
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
    Surface(
        color = black,
        modifier = Modifier.drawTopBorder(color = orange, strokeWidth = 5.dp)
    ) {
        NavigationBar(
            containerColor = black
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = currentScreen == index,
                    onClick = { onItemClick(index) },
                    icon = {
                        Icon(painter = painterResource(id = item.iconId), contentDescription = "")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = pain,
                        unselectedIconColor = gray,

                        ),
                )
            }
        }
    }

}


