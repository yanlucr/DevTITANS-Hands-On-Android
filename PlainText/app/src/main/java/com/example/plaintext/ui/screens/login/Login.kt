package com.example.plaintext.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Data class para representar o estado da LoginScreen
data class LoginViewState(
    val login: String = "",
    val password: String = "",
    val saveCredentials: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    var loginState by mutableStateOf(LoginViewState())
        private set

    fun updateLogin(newLogin: String) {
        loginState = loginState.copy(login = newLogin)
    }

    fun updatePassword(newPassword: String) {
        loginState = loginState.copy(password = newPassword)
    }

    fun toggleSaveCredentials() {
        loginState = loginState.copy(saveCredentials = !loginState.saveCredentials)
    }

    // Função simplificada que recebe um lambda para validação
    fun validateCredentials(
        checkCredentials: (String, String) -> Boolean,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            if (checkCredentials(loginState.login, loginState.password)) {
                onSuccess()
            } else {
                onError()
            }
        }
    }
}

@Composable
fun Login_screen(
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val preferencesViewModel: PreferencesViewModel = hiltViewModel()

    LoginScreenContent(
        navigateToSensores = {  },
        navigateToSettings = { navigateToSettings() },
        navigateToList = { navigateToList() },
        state = viewModel.loginState,
        updateLogin = { viewModel.updateLogin(it) },
        updatePassword = { viewModel.updatePassword(it) },
        toggleSaveCredentials = { viewModel.toggleSaveCredentials() },
        checkCredentials = { login, password ->
            preferencesViewModel.checkCredentials(
                login,
                password
            )
        },
        validateCredentials = { checkCredentials, onSuccess, onFailure ->
            viewModel.validateCredentials(
                checkCredentials,
                onSuccess,
                onFailure
            )
        }
    )
}

@Composable
fun LoginScreenContent(
    navigateToSensores: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit,
    state: LoginViewState,
    updateLogin: (String) -> Unit,
    updatePassword: (String) -> Unit,
    toggleSaveCredentials: () -> Unit,
    checkCredentials: (String, String) -> Boolean,
    validateCredentials: ((String, String) -> Boolean, () -> Unit, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopBarComponent(
                navigateToSettings = { navigateToSettings() },
                navigateToSensores = { navigateToSensores() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = innerPadding.calculateTopPadding(), horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo e Slogan

            Text(
                text = "\"The most secure password manager\"\nBob and Alice",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                    .background(Color(0xFF81C784))
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campos de Login e Senha
            Text(
                text = "Digite suas credenciais para continuar",
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedTextField(
                value = state.login,
                onValueChange = { updateLogin(it) },
                label = { Text("Login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = { updatePassword(it) },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = state.saveCredentials,
                    onCheckedChange = { toggleSaveCredentials() }
                )
                Text(text = "Salvar as informações de login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de Envio
            Button(
                onClick = {
                    validateCredentials(
                        checkCredentials, {
                            Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT)
                                .show()
                            navigateToList()
                        }, {
                            Toast.makeText(
                                context,
                                "Credenciais inválidas. Tente novamente.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 120.dp)
                    .padding(8.dp)
            ) {
                Text("Enviar")
            }
        }
    }
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },

            title = { Text(text = "Sobre") },
            text = { Text(text = "PlainText Password Manager v1.0") },
            confirmButton = {
                Button(
                    onClick = { shouldShowDialog.value = false }
                ) {
                    Text(text = "Ok")
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(
    navigateToSettings: (() -> Unit?)? = null,
    navigateToSensores: (() -> Unit?)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val shouldShowDialog = remember { mutableStateOf(false) }

    if (shouldShowDialog.value) {
        MyAlertDialog(shouldShowDialog = shouldShowDialog)
    }

    TopAppBar(
        title = { Text("PlainText") },
        actions = {
            if (navigateToSettings != null && navigateToSensores != null) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Configurações") },
                        onClick = {
                            navigateToSettings()
                            expanded = false
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Sobre")
                        },
                        onClick = {
                            shouldShowDialog.value = true
                            expanded = false
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreenContent(
        navigateToSensores = {},
        navigateToSettings = {},
        navigateToList = {},
        state = LoginViewState("devtitans", "123", true),
        updateLogin = {},
        updatePassword = {},
        toggleSaveCredentials = {},
        checkCredentials = { _,_ -> true },
        validateCredentials = { _,_,_ -> }
    )
}
