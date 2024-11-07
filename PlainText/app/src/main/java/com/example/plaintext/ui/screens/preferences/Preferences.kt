package com.example.plaintext.ui.screens.preferences


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.util.PreferenceInput
import com.example.plaintext.ui.screens.util.PreferenceItem
import com.example.plaintext.ui.viewmodel.PreferencesViewModel

@Composable
fun SettingsScreen(navController: NavHostController?,
                   viewModel: PreferencesViewModel = hiltViewModel()
){
    Scaffold(
        topBar = {
            TopBarComponent()
        }
    ){ padding ->
        SettingsContent(modifier = Modifier.padding(padding), viewModel)
    }
}

@Composable
fun SettingsContent(modifier: Modifier = Modifier, viewModel: PreferencesViewModel) {
    var isSwitchChecked by remember { mutableStateOf(viewModel.preferencesState.preencher) }
    var loginText by remember { mutableStateOf(viewModel.preferencesState.login) }
    var passwordText by remember { mutableStateOf(viewModel.preferencesState.password) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())){

        PreferenceInput(
            title = "Preencher Login",
            label = "Login",
            fieldValue = loginText,
            summary = "Preencher login na tela inicial"
        ){
            loginText = it
            viewModel.updateLogin(it)
        }

        PreferenceInput(
            title = "Setar Senha",
            label = "Label",
            fieldValue = passwordText,
            summary = "Senha para entrar no sistema"
        ){
            passwordText = it
            viewModel.updatePassword(it)
        }

        PreferenceItem(
            title = "Preencher Login",
            summary = "Preencher login na tela inicial",
            onClick = {
                isSwitchChecked = !isSwitchChecked
                viewModel.updatePreencher(isSwitchChecked)
            },
            control = {
                Switch(
                    checked = isSwitchChecked,
                    onCheckedChange = {
                        isSwitchChecked = it
                        viewModel.updatePreencher(it)
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(null)
}