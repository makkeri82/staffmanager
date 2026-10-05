package com.example.staffmanager.repository

import com.example.staffmanager.mockData.Event
import com.example.staffmanager.mockData.mockEventsAttended
import com.example.staffmanager.mockData.mockEventsUpcoming

class MockEventRepositoryImpl : EventRepository {
    private val _events = mockEventsUpcoming.toMutableList()
    private val _eventsAttended = mockEventsAttended.toMutableList()

    override suspend fun getEvents(): List<Event> = _events.toList()
    override suspend fun getAttendedEvents(): List<Event> = _eventsAttended.toList()


    override suspend fun getEventById(id: String): Event? = _events.find { it.id == id } ?: _eventsAttended.find { it.id == id }
}
