package com.vaasudev.androidcomposestructure.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vaasudev.androidcomposestructure.domain.response.UserDetailsResponse
import com.vaasudev.androidcomposestructure.domain.utility.Status
import com.vaasudev.androidcomposestructure.domain.utility.printLog
import io.ktor.client.utils.EmptyContent.status

@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        HomeScreenContent(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        )
    }
}

@Composable
fun HomeScreenContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var userName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .width(40.dp)
                    .padding(top = 60.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onPrimary,
            )
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 60.dp),
                text = userName,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                )
            )
        }

        Button(
            modifier = Modifier
                .align(Alignment.Center),
            onClick = {
                viewModel.restGetAPI(context)
            }
        ) { Text("Call API") }
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { status ->
            when (status) {
                Status.Loading -> {
                    isLoading = true
                }

                is Status.Error -> {
                    isLoading = false
                    printLog(
                        "API Error",
                        "Status - ${status.error.statusCode} -> ${status.error.message}"
                    )
                    Toast.makeText(context, status.error.message, Toast.LENGTH_SHORT).show()
                }

                is Status.Success<*> -> {
                    isLoading = false
                    when (status.data) {
                        is UserDetailsResponse -> {
                            status.data.let { data ->
                                userName = data.data.employeeName
                            }
                        }
                    }
                }
            }
        }
    }
}