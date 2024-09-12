package com.zabcode.contactlistapp.core.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.zabcode.contactlistapp.ContactDB

class AndroidDriverFactory(
    private val context: Context
) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = ContactDB.Schema,
            context = context,
            name = "contact.db"
        )
    }
}