package com.example.staffmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.staffmanager.theme.EventAppTheme

@Composable
fun MessengerItem(
    eventName: String,
    venueName: String,
    date: String,
    modifier: Modifier = Modifier
) {

    Row(
        modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = modifier.padding(4.dp)
        ) {
            Text(
                text = eventName,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 24.sp
            )
            Text(
                text = venueName,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = modifier.padding(4.dp)
        ) {
            Text(
                text = date,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 18.sp
            )
        }
    }
}
@Preview
@Composable
fun PreviewMessengerItem() {
    EventAppTheme {
        MessengerItem("Saimaa", "Tullisali", "17.10.")
    }
}
