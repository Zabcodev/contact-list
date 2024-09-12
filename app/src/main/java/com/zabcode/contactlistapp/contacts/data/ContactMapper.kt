package com.zabcode.contactlistapp.contacts.data

import com.zabcode.contactlistapp.ContactEntity
import com.zabcode.contactlistapp.contacts.domain.Contact

fun ContactEntity.toContact(): Contact {
    return Contact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        photo = null
    )
}