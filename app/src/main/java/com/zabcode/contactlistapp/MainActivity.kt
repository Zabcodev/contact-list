package com.zabcode.contactlistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zabcode.contactlistapp.contacts.presentation.ContactListViewModel
import com.zabcode.contactlistapp.core.presentation.App
import com.zabcode.contactlistapp.di.AppModule

class MainActivity : ComponentActivity() {

    private val viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val appModule = AppModule(this@MainActivity)
            return ContactListViewModel(appModule.contactDataSource) as T
        }

    }
    private val viewModel by viewModels<ContactListViewModel> { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true,
                viewModel = viewModel
            )
        }
    }
}