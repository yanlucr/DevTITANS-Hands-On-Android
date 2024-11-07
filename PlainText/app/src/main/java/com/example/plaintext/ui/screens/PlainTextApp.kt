package com.example.plaintext.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.plaintext.data.model.Password
import com.example.plaintext.ui.screens.editList.EditList
import com.example.plaintext.ui.screens.login.Login_screen
import com.example.plaintext.utils.parcelableType
import kotlin.reflect.typeOf

@Composable
fun PlainTextApp(
    appState: JetcasterAppState = rememberJetcasterAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.Login,
    )
    {
        composable<Screen.Login> {
            Login_screen(
                navigateToSettings = {},
                navigateToList = {}
            )
        }
        composable<Screen.EditList>(
            typeMap = mapOf(typeOf<Password>() to parcelableType<Password>())
        ) {
            val args = it.toRoute<Screen.EditList>()
            EditList(
                args,
                navigateBack = { appState.navigateBack() },
                savePassword = { password -> Unit }
            )
        }
    }
}