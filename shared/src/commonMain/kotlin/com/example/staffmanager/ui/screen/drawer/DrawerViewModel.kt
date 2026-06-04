package com.example.staffmanager.ui.screen.drawer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DrawerUiState(val userName: String = "")

sealed interface DrawerAction {
    data object OpenProfile : DrawerAction
    data object OpenAbout : DrawerAction
}

class DrawerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DrawerUiState())
    val uiState: StateFlow<DrawerUiState> = _uiState.asStateFlow()

    fun onAction(action: DrawerAction) {}
}
