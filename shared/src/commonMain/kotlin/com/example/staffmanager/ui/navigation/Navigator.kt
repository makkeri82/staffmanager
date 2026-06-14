package com.example.staffmanager.ui.navigation

import androidx.compose.runtime.currentRecomposeScope
import androidx.navigation3.runtime.NavKey

class Navigator(val state: NavigationState) {

    fun navigate(route: NavKey) {
        // Top level navigation (bottomNav)
        if (route in state.backStacks.keys) {
            // Update navigation state in top level(home, event, info)
            state.topLevelRoute = route
        } else {
            // Update topLevel navigation backStack routes.
            // - Get the top level backstack from navigationState
            // - add the route to this top level's own navigation stack
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun goBack() {
        val currentStack = state.backStacks[state.topLevelRoute]
            ?: error("Back stack for ${state.topLevelRoute} doesn't exists")
        val currentRoute = currentStack.last()

        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()
        }
    }
}