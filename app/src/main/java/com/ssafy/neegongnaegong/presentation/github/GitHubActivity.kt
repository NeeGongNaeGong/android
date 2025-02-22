package com.ssafy.neegongnaegong.presentation.github

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: GitHubViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubRepoScreen(viewModel)
        }
    }
}

@Composable
fun GitHubRepoScreen(viewModel: GitHubViewModel) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is GitHubContract.Effect.ShowRepos -> {
                    Toast.makeText(context, "Repositories loaded successfully!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var username by remember { mutableStateOf("") }

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("GitHub Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.setEvent(GitHubContract.Event.OnSearchClicked(username))
        }) {
            Text("Search Repos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val githubState = state.githubState) {
            is GitHubContract.GitHubState.Idle -> Text("Enter a username to search.")
            is GitHubContract.GitHubState.Loading -> CircularProgressIndicator()
            is GitHubContract.GitHubState.Success -> {
                LazyColumn {
                    items(githubState.repos) { repo ->
                        Text("${repo.name} - ${repo.description}")
                        HorizontalDivider()
                    }
                }
            }

            is GitHubContract.GitHubState.Error -> {
                Text(
                    "Error: ${githubState.message}",
                )
                println("확인 ${githubState.message}")
            }
        }
    }
}
