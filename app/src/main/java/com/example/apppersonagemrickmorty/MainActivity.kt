package com.example.apppersonagemrickmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.apppersonagemrickmorty.navigation.AppNavigation
import com.example.apppersonagemrickmorty.ui.theme.AppPersonagemRickMortyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AppPersonagemRickMortyTheme {
                AppNavigation()
            }
        }
    }
}