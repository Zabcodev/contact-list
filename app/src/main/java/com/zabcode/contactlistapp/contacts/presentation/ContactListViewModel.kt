package com.zabcode.contactlistapp.contacts.presentation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContentProviderCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zabcode.contactlistapp.contacts.data.SqlDelightContactDataSource
import com.zabcode.contactlistapp.contacts.domain.Contact
import com.zabcode.contactlistapp.contacts.domain.ContactDataSource
import com.zabcode.contactlistapp.contacts.domain.ContactValidator
import com.zabcode.contactlistapp.di.AppModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val contactDataSource: ContactDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(ContactListState())
    val state = combine(
        _state,
        contactDataSource.getContacts(),
        contactDataSource.getRecentContacts(20)
    ) { state, contacts, recentContacts ->
        state.copy(
            contacts = contacts,
            recentlyAddedContacts = recentContacts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ContactListState())

    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent(event: ContactListEvent) {
        when (event) {
            ContactListEvent.DeleteContact -> {
                viewModelScope.launch {
                    _state.value.selectedContact?.id?.let { id ->
                        _state.update { state ->
                            state.copy(isSelectedContactSheetOpen = false)
                        }
                        contactDataSource.deleteContact(id)
                        delay(300L)
                        _state.update { state ->
                            state.copy(selectedContact = null)
                        }
                    }
                }
            }
            ContactListEvent.DismissContact -> {
                viewModelScope.launch {
                    _state.update { state ->
                        state.copy(
                            isSelectedContactSheetOpen = false,
                            isAddContactSheetOpen = false,
                            firstNameError = null,
                            lastNameError = null,
                            phoneNumberError = null,
                            emailError = null
                        )
                    }
                    delay(300L)
                    newContact = null
                    _state.update { state ->
                        state.copy(
                            selectedContact = null
                        )
                    }
                }
            }
            is ContactListEvent.EditContact -> {
                _state.update { state ->
                    state.copy(
                        selectedContact = null,
                        isAddContactSheetOpen = true,
                        isSelectedContactSheetOpen = false
                    )
                }
                newContact = event.contact
            }
            ContactListEvent.OnAddNewContactClick -> {
                _state.update { state ->
                    state.copy(
                        isAddContactSheetOpen = true
                    )
                }
                newContact = Contact(
                    id = null,
                    firstName = "",
                    lastName = "",
                    phoneNumber = "",
                    email = "",
                    photo = null
                )
            }
            is ContactListEvent.OnEmailChanged -> {
                newContact = newContact?.copy(
                    email = event.value
                )
            }
            is ContactListEvent.OnFirstNameChanged -> {
                newContact = newContact?.copy(
                    firstName = event.value
                )
            }
            is ContactListEvent.OnLastNameChanged -> {
                newContact = newContact?.copy(
                    lastName = event.value
                )
            }
            is ContactListEvent.OnPhoneNumberChanged -> {
                newContact = newContact?.copy(
                    phoneNumber = event.value
                )
            }
            is ContactListEvent.OnPhotoPicked -> {
                newContact = newContact?.copy(
                    photo = event.bitmap
                )
            }
            ContactListEvent.SaveContact -> {
                newContact?.let { contact ->
                    val result = ContactValidator.validateContact(contact)
                    val errors = listOfNotNull(
                        result.firstNameError,
                        result.lastNameError,
                        result.emailError,
                        result.phoneNumberError
                    )

                    if (errors.isEmpty()) {
                        _state.update { state ->
                            state.copy(
                                isAddContactSheetOpen = false,
                                firstNameError = null,
                                lastNameError = null,
                                emailError = null,
                                phoneNumberError = null
                            )
                        }
                        viewModelScope.launch {
                            contactDataSource.insertContact(contact)
                            delay(300L)
                            newContact = null
                        }
                    } else {
                        _state.update { state ->
                            state.copy(
                                firstNameError = result.firstNameError,
                                lastNameError = result.lastNameError,
                                emailError = result.emailError,
                                phoneNumberError = result.phoneNumberError
                            )
                        }
                    }
                }
            }
            is ContactListEvent.SelectContact -> {
                _state.update { state ->
                    state.copy(
                        selectedContact = event.contact,
                        isSelectedContactSheetOpen = true
                    )
                }
            }
            else -> Unit
        }
    }
}