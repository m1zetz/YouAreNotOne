package com.example.youarenotalone.ui.screens.bottomNav

import android.util.Log
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.positionInWindow
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BottomNavViewModel : ViewModel() {

    enum class Screens{
        STORIES,
        MY_STORIES,
        SETTINGS
    }

    val interactionSourceInternal = MutableInteractionSource()

    private var _currentOffset = MutableStateFlow<Offset?>(null)
    var currentOffset = _currentOffset.asStateFlow()

    private var _currentFirstOffset = MutableStateFlow<Offset?>(null)
    var currentFirstOffset = _currentFirstOffset.asStateFlow()

    fun changeOffset(coordinates: Offset){
        _currentOffset.value = coordinates
    }
    fun changeFirstButtonOffset(coordinates: Offset){
        _currentFirstOffset.value = coordinates
    }

    private var _currentBottomItem = MutableStateFlow(Screens.STORIES)
    var currentBottomItem = _currentBottomItem.asStateFlow()

    fun changeCurrentBottomItem(screen: Screens){
        _currentBottomItem.value = screen
    }



    private var _isCoordinatesLoaded = MutableStateFlow(false)
    var isCoordinatesLoaded = _isCoordinatesLoaded.asStateFlow()

    fun changeIsLoadedOnTrue(){
        _isCoordinatesLoaded.value = true
    }

}