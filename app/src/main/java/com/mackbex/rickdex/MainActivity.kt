package com.mackbex.rickdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.mackbex.rickdex.ui.theme.RickdexTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      RickdexTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          CharacterListScreen(
            characters = sampleCharacters,
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}