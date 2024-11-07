package com.example.plaintext.ui.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.plaintext.data.model.Password
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen() {

    @Serializable
    object Login;

    @Serializable
    data class Hello(
        val name: String?
    )

    @Serializable
    object Preferences;

    @Serializable
    object List;

    @Serializable
    data class EditList(
        val password: Password
    );

    @Serializable
    object sensors;
}

@Composable
fun rememberJetcasterAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(navController, context) {
    JetcasterAppState(navController, context)
}


class JetcasterAppState(
    val navController: NavHostController,
    private val context: Context
) {

    fun checkRoute(route: String): Boolean {
        val currentRoute = navController.currentBackStackEntry?.destination?.route.toString()

        return currentRoute != route
    }

    fun navigateToHello(name: String?){
        navController.navigate(Screen.Hello(name))
    }

    fun navigateToLogin(){
        navController.navigate(Screen.Login)
    }

    fun navigateToEditList(password: Password){
        navController.navigate(Screen.EditList(password))
    }

    fun navigateToPreferences(){
        navController.navigate(Screen.Preferences)
    }

    fun navigateBack() {
        navController.popBackStack()
    }

}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
