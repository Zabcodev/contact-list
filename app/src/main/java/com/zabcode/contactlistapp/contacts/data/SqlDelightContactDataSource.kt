package com.zabcode.contactlistapp.contacts.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.zabcode.contactlistapp.ContactDB
import com.zabcode.contactlistapp.contacts.domain.Contact
import com.zabcode.contactlistapp.contacts.domain.ContactDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightContactDataSource(
    db: ContactDB
): ContactDataSource {

    private val queries = db.contactQueries

    override fun getContacts(): Flow<List<Contact>> {
        return queries
            .getContacts()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { contactEntities ->
                contactEntities.map { contactEntity ->
                    contactEntity.toContact()
                }
            }
    }

    override fun getRecentContacts(amount: Int): Flow<List<Contact>> {
        return queries
            .getRecentContacts(amount.toLong())
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { contactEntities ->
                contactEntities.map { contactEntity ->
                    contactEntity.toContact()
                }
            }
    }

    override suspend fun insertContact(contact: Contact) {
        queries.insertContact(
            id = contact.id,
            firstName = contact.firstName,
            lastName = contact.lastName,
            email = contact.email,
            phoneNumber = contact.phoneNumber,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            imagePath = null
        )
    }

    override suspend fun getContactById(id: Long): Contact? {
        return queries
            .getContactById(id)
            .executeAsOneOrNull()?.toContact()
    }

    override suspend fun deleteContact(id: Long) {
        queries.deleteContact(id)
    }
}