package com.example.plaintext.ui.screens.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.viewmodel.ListViewState
import com.example.plaintext.R
import com.example.plaintext.data.model.Password
import com.example.plaintext.ui.screens.login.TopBarComponent

@Preview
@Composable
fun ListScreenPreview() {
    val samplePasswords = listOf(
        Password(
            name = "Conta 1", login = "usuario1",
            id = 21,
            password = "usuario1"

        ),
        Password(
            name = "Conta 2", login = "usuario2",
            id = 22,
            password = "usuario2"

        ),
        Password(
            name = "Conta 3", login = "usuario3",
            id = 23,
            password = "usuario3"

        )
    )
    ListScreen(
        listState = ListViewState(isLoading = false, passwords = samplePasswords),
        navigateToEdit = {}
    )
}

@Composable
fun ListScreen(
    listState: ListViewState,
    navigateToEdit: (password: Password) -> Unit
) {
    Scaffold(
        topBar = { TopBarComponent() },
        floatingActionButton = { AddButton { navigateToEdit(Password(0, "", "", "")) } }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            ListItemContent(
                listState = listState,
                navigateToEdit = { navigateToEdit(it) }
            )
        }
    }
}

@Composable
fun AddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Small floating action button.")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItemContent(
    modifier: Modifier = Modifier,
    listState: ListViewState,
    navigateToEdit: (password: Password) -> Unit
) {
    when {
        listState.isLoading -> {
            LoadingScreen()
        }

        else -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                items(listState.passwords.size) {
                    ListItem(
                        listState.passwords[it],
                        navigateToEdit
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Carregando")
    }
}

@Composable
fun ListItem(
    password: Password,
    navigateToEdit: (password: Password) -> Unit
) {
    val title = password.name
    val subTitle = password.login

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable { navigateToEdit(password) }
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxHeight()
        )
        Column(
            modifier = Modifier
                .weight(.7f)
                .padding(horizontal = 5.dp),
        ) {
            Text(title, fontSize = 20.sp)
            Text(subTitle, fontSize = 14.sp)
        }
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Menu",
            tint = Color.White
        )
    }
}
