package br.sapiens.bellus_app.presentation.telas.barra_navegation


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.presentation.navegation.RotasDestinos
import br.sapiens.bellus_app.presentation.viewmodels.TelaNavegationBarViewModel

@Composable
fun BottomNavigation(navController: NavHostController, selectedItem: BottomNavItem?) {

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Pesquisar,
        BottomNavItem.Perfil
    )

    NavigationBar(
        modifier = Modifier
            .height(60.dp),
        containerColor = Color(0xFF1D2B3D),
    ) {
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
//        label = {
//            Text(text = screen.title)
//        },
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = screen.title,
                modifier = Modifier
                            .scale(if (isSelected) 1.5f else 1f)
                            .animateContentSize()
                            .align(Alignment.CenterVertically)
            )
        },
        selected = isSelected,
        alwaysShowLabel = true,
        onClick = {
            navController.navigate(screen.route)
            viewModel.selectItem(screen)
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent,
            selectedIconColor = Color.Transparent,
            unselectedIconColor = Color.Transparent
        )
    )
}

sealed class BottomNavItem(
    var title: String,
    var icon: Int,
    var route: String
) {
    data object Home :
        BottomNavItem(
            "Home",
            R.drawable.ic_home_black_24dp,
            RotasDestinos.Home.rota
        )

    data object Pesquisar :
        BottomNavItem(
            "Pesquisar",
            R.drawable.ic_search_black_18dp,
//            RotasDestinos.Pesquisar.rota
            RotasDestinos.Home.rota
        )

    data object Perfil :
        BottomNavItem(
            "Perfil",
            R.drawable.ic_home_black_24dp,
//            RotasDestinos.Perfil.rota
            RotasDestinos.Home.rota
        )
}