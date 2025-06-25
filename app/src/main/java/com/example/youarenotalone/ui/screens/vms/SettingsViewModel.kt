package com.example.youarenotalone.ui.screens.vms

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.youarenotalone.getSavedLanguage

class SettingsViewModel : ViewModel() {
    val langs = arrayOf("English", "Русский", "Қазақ")

    var expanded = mutableStateOf(false)

    var selectedText = mutableStateOf(langs[0])

    fun initializeSelectedLanguage(context: Context) {
        val savedLangCode = getSavedLanguage(context)
        selectedText.value = when (savedLangCode) {
            "en" -> "English"
            "ru" -> "Русский"
            "kk" -> "Қазақ"
            else -> "English"
        }
    }

}