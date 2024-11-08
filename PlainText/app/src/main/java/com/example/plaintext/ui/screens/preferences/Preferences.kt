package com.example.plaintext.ui.screens.preferences


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.util.PreferenceInput
import com.example.plaintext.ui.screens.util.PreferenceItem
import com.example.plaintext.ui.viewmodel.PreferencesState
import com.example.plaintext.ui.viewmodel.PreferencesViewModel

@Composable
fun SettingsScreen(
    viewModel: PreferencesViewModel = hiltViewModel()
) {
    SettingsContent(
        state = viewModel.preferencesState,
        updatePreencher = { viewModel.updatePreencher(it) },
        updateLogin = { viewModel.updateLogin(it) },
        updatePassword = { viewModel.updatePassword(it) }
    )
}

@Composable
fun SettingsContent(
    state: PreferencesState,
    updatePreencher: (Boolean) -> Unit,
    updateLogin: (String) -> Unit,
    updatePassword: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold (
        topBar = { TopBarComponent() }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = innerPadding.calculateTopPadding(), horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            PreferenceInput(
                title = "Preencher Login",
                label = "Login",
                fieldValue = state.login,
                summary = "Preencher login na tela inicial"
            ) {
                updateLogin(it)
            }

            PreferenceInput(
                title = "Setar Senha",
                label = "Label",
                fieldValue = state.password,
                summary = "Senha para entrar no sistema"
            ) {
                updatePassword(it)
            }

            PreferenceItem(
                title = "Preencher Login",
                summary = "Preencher login na tela inicial",
                onClick = {
                    updatePreencher(state.preencher)
                },
                control = {
                    Switch(
                        checked = state.preencher,
                        onCheckedChange = {
                            updatePreencher(it)
                        }
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsContent(
        state = PreferencesState("devtitans", "123", true),
        updatePreencher = {  },
        updateLogin = {  },
        updatePassword = {  }
    )
}