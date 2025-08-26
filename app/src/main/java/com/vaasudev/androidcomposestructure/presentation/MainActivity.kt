package com.vaasudev.androidcomposestructure.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vaasudev.androidcomposestructure.domain.routes.ScreenHome
import com.vaasudev.androidcomposestructure.presentation.home.HomeScreen
import com.vaasudev.androidcomposestructure.ui.theme.AdnroidComposeStructureTheme
import dagger.hilt.android.AndroidEntryPoint

/** # Created by `V J R` */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdnroidComposeStructureTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = ScreenHome,
                    enterTransition = {
                        fadeIn(tween(1000))
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start, tween(1000)
                        )
                    }/*, popEnterTransition = {
                        fadeIn(tween(1000))
                    }*/, popExitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.End, tween(1000)
                        )
                    }) {
                    composable<ScreenHome> {
                        HomeScreen(navController = navController)
                    }
                }
            }
        }
    }
}