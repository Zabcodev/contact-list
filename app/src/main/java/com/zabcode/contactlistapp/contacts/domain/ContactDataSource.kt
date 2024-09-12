package com.zabcode.contactlistapp.contacts.domain

import kotlinx.coroutines.flow.Flow

interface ContactDataSource {
    fun getContacts(): Flow<List<Contact>>
    fun getRecentContacts(amount: Int): Flow<List<Contact>>
    suspend fun insertContact(contact: Contact)
    suspend fun getContactById(id: Long): Contact?
    suspend fun deleteContact(id: Long)
}