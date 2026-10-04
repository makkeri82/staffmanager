package com.example.staffmanager.ui.screen.events

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import com.example.staffmanager.ui.components.EventList
import com.example.staffmanager.mockData.mockEventsUpcoming
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.staffmanager.theme.EventAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    state: EventsUiState,
    // onAction: (EventsAction) -> Unit,
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    Column {
        Text(
            modifier = modifier.padding(10.dp),
            text = "Pending",
            style = MaterialTheme.typography.titleLarge
        )
        EventList(
            events = state.events,
            onEventClick = onEventClick
        )
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            FloatingActionButton(
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = { showSheet = true},
                shape = CircleShape,
            ) {
                Icon(Icons.Default.Add, "Create Event")
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(onDismissRequest = { showSheet = false}) {
            CreateEditEvent(
                onClickClose = { showSheet = false},
                onClickApply = { showSheet = false }
            )
        }
    }

}

@Composable
@Preview
fun PreviewEventsScreen() {
    EventAppTheme {
        EventsScreen(
            state = EventsUiState(events = mockEventsUpcoming, isAdmin = true),
            onEventClick = {}
        )
    }
}
