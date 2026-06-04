package com.example.staffmanager.repository

import com.example.staffmanager.mockData.Event

interface EventRepository {
    suspend fun getEvents(): List<Event>
    suspend fun getEventById(id: String): Event?
}
