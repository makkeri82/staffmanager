package com.example.staffmanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.example.staffmanager.ui.screen.events.EventDetailsScreen
import com.example.staffmanager.ui.screen.events.EventDetailsViewModel
import com.example.staffmanager.ui.screen.events.EventsAction
import com.example.staffmanager.ui.screen.events.EventsScreen
import com.example.staffmanager.ui.screen.events.EventsViewModel
import com.example.staffmanager.ui.screen.main.HomeScreen
import com.example.staffmanager.ui.screen.main.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavigationRoot(
    backStack: MutableList<NavKey>,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryProvider = { key ->
            when (key) {
                is Route.Home -> {
                    NavEntry(key) {
                        val viewModel: HomeViewModel = koinViewModel()
                        val state by viewModel.uiState.collectAsState()
                        HomeScreen(state = state)
                    }
                }

                is Route.Event -> {
                    NavEntry(key) {
                        val viewModel: EventsViewModel = koinViewModel()
                        val state by viewModel.uiState.collectAsState()
                        EventsScreen(
                            state = state,
                            onEventClick = { eventId ->
                                val eventTitle =
                                    state.events.find { it.id == eventId }?.eventName ?: "Details"
                                viewModel.onAction(EventsAction.SelectEvent(eventId))
                                backStack.add(Route.EventDetails(eventId, eventTitle))
                            }
                        )
                    }
                }

                is Route.EventDetails -> {
                    NavEntry(key) {
                        val viewModel: EventDetailsViewModel = koinViewModel()
                        val state by viewModel.uiState.collectAsState()
                        EventDetailsScreen(
                            state = state,
                            onClickBack = {
                                backStack.removeLastOrNull()
                            }
                        )
                    }
                }

                else -> error("Unknown NavKey: $key")
            }
        }
    )
}
/*
@Composable
@Preview(name = "Navigation root")
fun PreviewNavigationRoot() {

    KoinApplication(
        configuration = koinConfiguration(declaration = { modules(appModule) }),
        content = {
            MaterialTheme {
                NavigationRoot(
                    backStack = TODO(),
                    modifier = TODO()
                )
            }
        })
//    MaterialTheme {
//        NavigationRoot()
//    }
}*/