package com.zabcode.contactlistapp.contacts.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zabcode.contactlistapp.contacts.domain.Contact
import com.zabcode.contactlistapp.contacts.presentation.ContactListEvent
import com.zabcode.contactlistapp.contacts.presentation.ContactListState
import com.zabcode.contactlistapp.core.presentation.BottomSheetFromWish

@Composable
fun AddContactSheet(
    state: ContactListState,
    newContact: Contact?,
    isOpen: Boolean,
    onEvent: (ContactListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomSheetFromWish(
        visible = isOpen,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                if (newContact?.photo == null) {
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(40))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable {
                                onEvent(ContactListEvent.OnAddPhotoClicked)
                            }
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                shape = RoundedCornerShape(40)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add Photo",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else {
                    ContactPhoto(
                        contact = newContact,
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                onEvent(ContactListEvent.OnAddPhotoClicked)
                            }
                    )
                }
                Spacer(Modifier.height(16.dp))
                ContactTextField(
                    value = newContact?.firstName ?: "",
                    onValueChanged = { onEvent(ContactListEvent.OnFirstNameChanged(it)) },
                    placeholder = "First name",
                    error = state.firstNameError,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                ContactTextField(
                    value = newContact?.lastName ?: "",
                    onValueChanged = { onEvent(ContactListEvent.OnLastNameChanged(it)) },
                    placeholder = "Last name",
                    error = state.lastNameError,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                ContactTextField(
                    value = newContact?.email ?: "",
                    onValueChanged = { onEvent(ContactListEvent.OnEmailChanged(it)) },
                    placeholder = "Email",
                    error = state.emailError,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                ContactTextField(
                    value = newContact?.phoneNumber ?: "",
                    onValueChanged = { onEvent(ContactListEvent.OnPhoneNumberChanged(it)) },
                    placeholder = "Phone Number",
                    error = state.phoneNumberError,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        onEvent(ContactListEvent.SaveContact)
                    }
                ) {
                    Text(
                        text = "Save"
                    )
                }
            }

            IconButton(
                onClick = {
                    onEvent(ContactListEvent.DismissContact)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }

        }
    }
}

@Composable
fun ContactTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    error: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            placeholder = {
                Text(text = placeholder)
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }

}