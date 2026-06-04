package com.example.staffmanager.repository

import com.example.staffmanager.mockData.Event
import com.example.staffmanager.mockData.mockEvents

class MockEventRepositoryImpl : EventRepository {
    private val _events = mockEvents.toMutableList()

    override suspend fun getEvents(): List<Event> = _events.toList()

    override suspend fun getEventById(id: String): Event? = _events.find { it.id == id }
}
