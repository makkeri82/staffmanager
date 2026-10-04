package com.example.staffmanager.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.staffmanager.mockData.mockEventsUpcoming
import com.example.staffmanager.theme.EventAppTheme

@Composable
fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    onEventClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

//        EventList(
//            events = state.events,
//            onEventClick = onEventClick
//        )
    }
}

// Preview for HomeScreen. Note: Android Studio Preview rendering requires
// `org.jetbrains.compose.ui:ui-tooling` (`libs.compose.uiTooling`) in `androidMain.dependencies`
// so that `androidx.compose.ui.tooling.ComposeViewAdapter` is present on the runtime classpath.
@Preview
@Composable
fun PreviewHomeScreen() {
    EventAppTheme {
        HomeScreen(state = HomeUiState(events = mockEventsUpcoming), onEventClick = {})
    }
}
