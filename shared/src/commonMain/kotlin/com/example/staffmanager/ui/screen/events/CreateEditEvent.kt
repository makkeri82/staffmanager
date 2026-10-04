package com.example.staffmanager.ui.screen.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.staffmanager.mockData.Event
import com.example.staffmanager.mockData.mockEventsAttended
import com.example.staffmanager.theme.EventAppTheme
import com.example.staffmanager.ui.screen.login.LoginAction

@Composable
fun CreateEditEvent(
    event: Event? = null,
    onClickClose: () -> Unit,
    onClickApply: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier

            .padding(12.dp)
    ) {
        Text(
            text= "Event Info",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface
        )
        OutlinedTextField(
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth(),
            state = rememberTextFieldState(),
            label = { Text(text = "Event" ) }
        )
        OutlinedTextField(
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth(),
            state = rememberTextFieldState(),
            label = { Text(text = "Venue" ) }
        )
        OutlinedTextField(
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth(),
            state = rememberTextFieldState(),
            label = { Text(text = "City" ) }
        )
        Text(
            text = "Event time",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface
        )
        Column(
            modifier = modifier
        ) {
            Text(
                text= "Notes",
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(
                onClick = { onClickClose() },
                modifier = Modifier,
            ) {
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = null,
                    modifier = modifier.size(ButtonDefaults.IconSize)
                )
            }
            Button(
                onClick = {  },
                modifier = Modifier,
            ) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    modifier = modifier.size(ButtonDefaults.IconSize)
                )
            }
        }
    }


}

@Preview
@Composable
fun PreviewCreateEditEvent() {
    EventAppTheme {
        CreateEditEvent(
            event = mockEventsAttended.first(),
            onClickClose = {},
            onClickApply = {}
        )
    }
}