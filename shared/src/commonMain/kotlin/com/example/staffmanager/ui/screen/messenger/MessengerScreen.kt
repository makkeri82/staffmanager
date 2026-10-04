package com.example.staffmanager.ui.screen.messenger

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.staffmanager.theme.EventAppTheme
import com.example.staffmanager.ui.components.MessengerItem

// UI development purpose only
data class mock(
    val eventName: String,
    val venueName: String,
    val date: String
)

// UI development purpose only
private val mockList = listOf(
    mock("Metallikausi", "Tullisali", "10.10."),
    mock("Saimaa", "Tullisali", "17.10."),
    mock("Irom Maiden", "Nokia Areena", "24.11."),
    mock("Reppana", "Ouluhalli", "6.12."),
)

@Composable
fun MessengerScreen(
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // When production use Events list
        items(mockList) { mockList ->
            MessengerItem(
                eventName = mockList.eventName,
                venueName = mockList.venueName,
                date = mockList.date)
        }
    }
}

@Composable
@Preview
fun PreviewMessengerScreen() {
    EventAppTheme {
        MessengerScreen()
    }
}