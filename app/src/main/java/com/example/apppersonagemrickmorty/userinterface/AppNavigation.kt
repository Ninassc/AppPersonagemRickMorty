package com.example.apppersonagemrickmorty.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.apppersonagemrickmorty.model.Rota
import com.example.apppersonagemrickmorty.viewmodel.LoginViewModel
import com.example.apppersonagemrickmorty.viewmodel.PersonagemViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // ViewModels compartilhados para o fluxo
    val personagemViewModel: PersonagemViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Rota.Splash
    ) {

        composable<Rota.Splash> {
            SplashScreen(
                onNavegarParaLogin = {
                    navController.navigate(Rota.Login) {
                        popUpTo(Rota.Splash) { inclusive = true }
                    }
                },
                onNavegarParaHome = {
                    navController.navigate(Rota.Home) {
                        popUpTo(Rota.Splash) { inclusive = true }
                    }
                }
            )
        }

        composable<Rota.Login> {
            LoginScreen(
                viewModel = loginViewModel,
                onIrParaCadastro = {
                    navController.navigate(Rota.Cadastro)
                },
                onLoginSucesso = {
                    navController.navigate(Rota.Home) {
                        popUpTo(Rota.Login) { inclusive = true }
                    }
                }
            )
        }


        composable<Rota.Cadastro> {
            CadastroScreen(
                onCadastroSucesso = {
                    navController.navigate(Rota.Login) {
                        popUpTo(Rota.Cadastro) { inclusive = true }
                    }
                },
                onVoltar = {
                    navController.popBackStack()
                }
            )
        }


        composable<Rota.Home> {
            HomeScreen(
                viewModel = personagemViewModel,
                onPersonagemClick = { id ->
                    navController.navigate(Rota.DetalhePersonagem(personagemId = id))
                },
                onIrParaFavoritos = {
                    navController.navigate(Rota.ListaFavoritos)
                }
            )
        }


        composable<Rota.ListaFavoritos> {
            FavoritosScreen(
                viewModel = personagemViewModel,
                onPersonagemClick = { id ->
                    navController.navigate(Rota.DetalhePersonagem(personagemId = id))
                },
                onVoltar = {
                    navController.popBackStack()
                }
            )
        }


        composable<Rota.DetalhePersonagem> { backStackEntry ->
            val rota: Rota.DetalhePersonagem = backStackEntry.toRoute()

            DetalhePersonagemScreen(
                personagemId = rota.personagemId,
                viewModel = personagemViewModel,
                onVoltar = {
                    navController.popBackStack()
                }
            )
        }
    }
}