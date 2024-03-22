package br.sapiens.bellus_app.telas.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.navegation.RotasDestinos
import br.sapiens.bellus_app.viewmodels.TelaNavegationBarViewModel


@Composable
fun TelaNavegationBar(navControllerProvider: NavControllerProvider) {
    val navController = rememberNavController()
    navControllerProvider.navController = navController

    val viewModel: TelaNavegationBarViewModel = viewModel()

    val selectedItem = viewModel.selectedItem.observeAsState()

    MaterialTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(navController, selectedItem.value)
            },
        ) { paddingValues ->
            NavHost(navController, startDestination = RotasDestinos.HOME_ROUTE) {
                composable(RotasDestinos.HOME_ROUTE) {
                    Box(modifier = Modifier.padding(paddingValues)) {
                        TelaHome()
                    }
                }
                composable(RotasDestinos.SPLASH_ROUTE) {
                    Box(modifier = Modifier.padding(paddingValues)) {
                        TelaPerfil()
                    }
                }
            }
        }
    }
}

@Composable
fun TelaPerfil() {
    Text("TELA PERFIL")
}

sealed class BottomNavItem(
    var title: String,
    var icon: Int,
    var route: String
) {
    object Home :
        BottomNavItem(
            "Home",
            R.drawable.ic_home_black_24dp,
            RotasDestinos.HOME_ROUTE
        )

    object List :
        BottomNavItem(
            "Chat",
            R.drawable.ic_home_black_24dp,
            RotasDestinos.SPLASH_ROUTE

        )

    object Profile :
        BottomNavItem(
            "Perfil",
            R.drawable.ic_home_black_24dp,
            RotasDestinos.SPLASH_ROUTE
        )
}


@Composable
fun BottomNavigation(navController: NavHostController, selectedItem: BottomNavItem?) {

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.List,
        BottomNavItem.Profile
    )

    NavigationBar {
        items.forEach { item ->
            AddItem(
                screen = item,
                navController = navController,
                isSelected = item == selectedItem,
                viewModel = viewModel()
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    navController: NavHostController,
    isSelected: Boolean,
    viewModel: TelaNavegationBarViewModel
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = screen.title
            )
        },
        selected = isSelected,
        alwaysShowLabel = true,
        onClick = {
            navController.navigate(screen.route)
            viewModel.selectItem(screen)
        },
        colors = NavigationBarItemDefaults.colors()
    )
}

@Preview(name = "TelaHome")
@Composable
private fun PreviewTelaHome() {
    val navController = rememberNavController()
    val navControllerProvider = NavControllerProvider().apply { this.navController = navController }
    TelaNavegationBar(navControllerProvider)
}