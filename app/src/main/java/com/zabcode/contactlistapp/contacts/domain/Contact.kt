package com.zabcode.contactlistapp.contacts.domain

import android.graphics.Bitmap

data class Contact(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val photo: Bitmap?
)
