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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.navegation.RotasDestinos

@Composable
fun TelaNavegationBar(
    navController: NavHostController
) {
    MaterialTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(navController)
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
fun BottomNavigation(navController: NavHostController) {

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.List,
        BottomNavItem.Profile
    )

    NavigationBar {
        items.forEach { item ->
            AddItem(
                screen = item,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    navController: NavHostController
) {
    NavigationBarItem(
        // Text that shows bellow the icon
        label = {
            Text(text = screen.title)
        },

        // The icon resource
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = screen.title
            )
        },

        // Display if the icon it is select or not
        selected = true,

        // Always show the label bellow the icon or not
        alwaysShowLabel = true,

        // Click listener for the icon
        onClick = { navController.navigate(screen.route) },

        // Control all the colors of the icon
        colors = NavigationBarItemDefaults.colors()
    )
}


@Preview(name = "TelaHome")
@Composable
private fun PreviewTelaHome() {
    val navController = rememberNavController()
    TelaNavegationBar(navController)
}