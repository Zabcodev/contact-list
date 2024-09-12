package com.zabcode.contactlistapp.contacts.presentation

import android.graphics.Bitmap
import com.zabcode.contactlistapp.contacts.domain.Contact

sealed interface ContactListEvent {
    data object OnAddNewContactClick: ContactListEvent
    data object DismissContact: ContactListEvent
    data class OnFirstNameChanged(val value: String): ContactListEvent
    data class OnLastNameChanged(val value: String): ContactListEvent
    data class OnEmailChanged(val value: String): ContactListEvent
    data class OnPhoneNumberChanged(val value: String): ContactListEvent
    class OnPhotoPicked(val bitmap: Bitmap): ContactListEvent
    data object OnAddPhotoClicked: ContactListEvent
    data object SaveContact: ContactListEvent
    data class SelectContact(val contact: Contact): ContactListEvent
    data class EditContact(val contact: Contact): ContactListEvent
    data object DeleteContact: ContactListEvent
}