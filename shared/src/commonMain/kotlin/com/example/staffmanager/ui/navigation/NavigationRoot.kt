package com.example.staffmanager.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.staffmanager.ui.screen.events.EventDetailsScreen
import com.example.staffmanager.ui.screen.events.EventDetailsViewModel
import com.example.staffmanager.ui.screen.events.EventsAction
import com.example.staffmanager.ui.screen.events.EventsScreen
import com.example.staffmanager.ui.screen.events.EventsViewModel
import com.example.staffmanager.ui.screen.main.HomeScreen
import com.example.staffmanager.ui.screen.main.HomeViewModel
import com.example.staffmanager.ui.screen.profile.ProfileScreen
import com.example.staffmanager.ui.screen.profile.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavigationRoot(
    navigationState: NavigationState,
    navigator: Navigator,
    modifier: Modifier
) {
    NavDisplay(
        modifier = modifier
            .fillMaxSize(),
        onBack = navigator::goBack,
        entries = navigationState.toEntries(
            entryProvider {
                entry<Route.Home> {
                    val viewModel: HomeViewModel = koinViewModel()
                    val state by viewModel.uiState.collectAsState()
                    HomeScreen(state)
                }
                entry<Route.Event> {
                    val viewModel: EventsViewModel = koinViewModel()
                    val state by viewModel.uiState.collectAsState()
                    EventsScreen(
                        state = state,
                        onEventClick = { eventId ->
                            val eventTitle =
                                state.events.find { it.id == eventId }?.eventName ?: "Details"
                            viewModel.onAction(EventsAction.SelectEvent(eventId))
                            navigator.navigate(Route.EventDetails(eventId, eventTitle))
                        }
                    )
                }
                entry<Route.EventDetails> {
                    val viewModel: EventDetailsViewModel = koinViewModel()
                    val state by viewModel.uiState.collectAsState()
                    EventDetailsScreen(state = state)
                }
                entry<Route.Info> {
                    // TODO
                }
                entry<Route.Profile> {
                    val viewModel: ProfileViewModel = koinViewModel()
                    val state by viewModel.uiState.collectAsState()
                    ProfileScreen(state)
                }
            }
        )
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