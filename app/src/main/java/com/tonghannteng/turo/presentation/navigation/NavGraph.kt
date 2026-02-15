package com.tonghannteng.turo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tonghannteng.turo.presentation.detail.DetailScreen
import com.tonghannteng.turo.presentation.detail.DetailViewModel
import com.tonghannteng.turo.presentation.main.MainScreen
import com.tonghannteng.turo.presentation.main.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Route.HomeScreen
    ) {
        composable<Route.HomeScreen> {
            val viewModel = koinViewModel<MainViewModel>()
            val state by viewModel.result.collectAsStateWithLifecycle()
            val text by viewModel.textValue.collectAsStateWithLifecycle()


            MainScreen(
                modifier = Modifier,
                state = state,
                event = viewModel.events,
                onItemClick = { business ->
                    navHostController.navigate(
                        Route.DetailScreen(
                            name = business.name ?: "",
                            url = business.imageUrl ?: "",

                        )
                    )
                },
                textValue = text,
                onValueChange = { viewModel.onValueChange(it) }
            )
        }
        composable<Route.DetailScreen> {
            val viewModel = koinViewModel<DetailViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            DetailScreen(
                modifier = Modifier,
                state = state
            )
        }
    }
}
