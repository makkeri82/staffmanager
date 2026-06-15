package com.example.staffmanager.di

import com.example.staffmanager.repository.AuthRepository
import com.example.staffmanager.repository.AuthRepositoryImpl
import com.example.staffmanager.repository.CommentRepository
import com.example.staffmanager.repository.EventRepository
import com.example.staffmanager.repository.MockCommentRepositoryImpl
import com.example.staffmanager.repository.MockEventRepositoryImpl
import com.example.staffmanager.repository.MockUserRepositoryImpl
import com.example.staffmanager.repository.UserRepository
import com.example.staffmanager.ui.screen.drawer.DrawerViewModel
import com.example.staffmanager.ui.screen.chat.ChatViewModel
import com.example.staffmanager.ui.screen.events.EventDetailsViewModel
import com.example.staffmanager.ui.screen.events.EventsViewModel
import com.example.staffmanager.ui.screen.login.LoginViewModel
import com.example.staffmanager.ui.screen.main.HomeViewModel
import com.example.staffmanager.ui.screen.profile.ProfileViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::EventSelectionState)
    singleOf(::SessionState)
    singleOf(::MockEventRepositoryImpl).bind<EventRepository>()
    singleOf(::MockCommentRepositoryImpl).bind<CommentRepository>()
    singleOf(::MockUserRepositoryImpl).bind<UserRepository>()
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}

val viewModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::EventsViewModel)
    viewModelOf(::EventDetailsViewModel)
    viewModelOf(::ChatViewModel)
    viewModelOf(::DrawerViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::LoginViewModel)
}
