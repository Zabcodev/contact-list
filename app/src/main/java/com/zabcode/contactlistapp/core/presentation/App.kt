package com.zabcode.contactlistapp.core.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.zabcode.contactlistapp.contacts.presentation.ContactListScreen
import com.zabcode.contactlistapp.contacts.presentation.ContactListViewModel
import com.zabcode.contactlistapp.ui.theme.ContactListAppTheme

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    viewModel: ContactListViewModel,
) {
    ContactListAppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {

        val state by viewModel.state.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContactListScreen(
                state = state,
                newContact = viewModel.newContact,
                onEvent = viewModel::onEvent
            )
        }
    }
}