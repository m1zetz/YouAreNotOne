package com.example.youarenotalone.ui.screens.bottomNav

import com.example.youarenotalone.R
import com.example.youarenotalone.my_stories
import com.example.youarenotalone.settings
import com.example.youarenotalone.stories


sealed class BottomItem(
    val iconId: Int,
    val route: String
){
    object Stories: BottomItem(R.drawable.broken_image, stories)
    object MyStories: BottomItem(R.drawable.dozd, my_stories)
    object Settings: BottomItem(R.drawable.settings, settings)
}