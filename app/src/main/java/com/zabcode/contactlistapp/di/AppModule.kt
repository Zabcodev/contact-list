package com.zabcode.contactlistapp.di

import android.content.Context
import com.zabcode.contactlistapp.ContactDB
import com.zabcode.contactlistapp.contacts.data.SqlDelightContactDataSource
import com.zabcode.contactlistapp.contacts.domain.ContactDataSource
import com.zabcode.contactlistapp.core.data.AndroidDriverFactory

class AppModule(
    private val context: Context
) {

    val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(
            db = ContactDB(
                driver = AndroidDriverFactory(context = context).createDriver()
            )
        )
    }

}