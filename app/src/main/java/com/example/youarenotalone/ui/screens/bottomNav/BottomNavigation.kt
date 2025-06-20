package com.example.youarenotalone.ui.screens.bottomNav

import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.youarenotalone.ui.theme.gray
import com.example.youarenotalone.ui.theme.pain

@Composable
fun BottomNavigationFun(
    navcontroller: NavController
) {
    val listItems = listOf(
        BottomItem.Stories, BottomItem.MyStories, BottomItem.Settings
    )

    NavigationBar() {
        val backStackEntry by navcontroller.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItems.forEach{ items ->
            NavigationBarItem(
                selected = currentRoute == items.route,
                onClick = {
                    navcontroller.navigate(items.route)
                },
                icon = {
                    Icon(painterResource(id = items.iconId), contentDescription = "")
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = pain,
                    unselectedIconColor = gray,
                    disabledIconColor = gray,
                    selectedTextColor = gray,
                    selectedIndicatorColor = gray,
                    unselectedTextColor = gray,
                    disabledTextColor = gray
                ),


            )

        }
    }

}