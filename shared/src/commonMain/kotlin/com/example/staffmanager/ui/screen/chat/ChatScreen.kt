package com.example.staffmanager.ui.screen.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.staffmanager.mockData.Comment

@Composable
fun ChatScreen(
    state: ChatUiState,
    onAction: (ChatAction) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var messageText by remember { mutableStateOf("") }

    LaunchedEffect(state.messageSent) {
        if (state.messageSent) {
            messageText = ""
            snackbarHostState.showSnackbar("Message sent")
            onAction(ChatAction.MessageSentConsumed)
        }
    }

    if (state.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.comments) { comment ->
                    CommentCard(comment)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Write a message…") },
                    singleLine = true
                )
                IconButton(
                    onClick = { onAction(ChatAction.AddComment(messageText)) },
                    enabled = messageText.isNotBlank()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send"
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CommentCard(comment: Comment) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "${comment.author.firstName} ${comment.author.lastName}",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = comment.content,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = comment.createdTime.take(10),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
