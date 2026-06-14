package com.example.staffmanager.ui.screen.drawer

import androidx.lifecycle.ViewModel
import com.example.staffmanager.ui.screen.profile.ProfileAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DrawerUiState(val userName: String = "")

sealed interface DrawerAction {
    data object OpenProfile : DrawerAction
    data object OpenAbout : DrawerAction
    data object NavigateBack: DrawerAction
}

class DrawerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DrawerUiState())
    val uiState: StateFlow<DrawerUiState> = _uiState.asStateFlow()

    fun onAction(action: DrawerAction) {
        when (action) {
            //is DrawerAction.OpenProfile -> { }
            // is DrawerAction.OpenAbout -> { }
            // is DrawerAction.NavigateBack -> { }
            else -> { }
        }
    }
}
