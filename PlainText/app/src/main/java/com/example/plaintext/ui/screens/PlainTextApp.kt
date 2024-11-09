package com.example.plaintext.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.app.viewmodel.ListViewModel
import com.example.plaintext.data.model.Password
import com.example.plaintext.ui.screens.editList.EditList
import com.example.plaintext.ui.screens.list.ListScreen
import com.example.plaintext.ui.screens.login.Login_screen
import com.example.plaintext.ui.screens.preferences.SettingsScreen
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import com.example.plaintext.utils.parcelableType
import kotlin.reflect.typeOf

@Composable
fun PlainTextApp(
    appState: JetcasterAppState = rememberJetcasterAppState()
) {
    val listViewModel: ListViewModel = hiltViewModel()
    val listViewState by listViewModel.viewState.collectAsState()

    val preferencesViewModel: PreferencesViewModel = hiltViewModel()
    NavHost(
        navController = appState.navController,
        startDestination = Screen.Login,
    )
    {
        composable<Screen.Login> {
            Login_screen(
                navigateToSettings = { appState.navigateToPreferences() },
                navigateToList = { appState.navigateToList() },
                preferencesViewModel = preferencesViewModel
            )
        }
        composable<Screen.List> {
            ListScreen(
                listState = listViewState,
                navigateToEdit = { appState.navigateToEditList(it) }
            )
        }
        composable<Screen.EditList>(
            typeMap = mapOf(typeOf<Password>() to parcelableType<Password>())
        ) {
            val args = it.toRoute<Screen.EditList>()
            EditList(
                args,
                navigateBack = { appState.navigateBack() },
                savePassword = { password -> listViewModel.addPassword(password) }
            )
        }
        composable<Screen.Preferences>{
            SettingsScreen(
                preferencesViewModel
            )
        }
    }
}