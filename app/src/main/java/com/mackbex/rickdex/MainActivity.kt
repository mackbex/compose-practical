package com.mackbex.rickdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mackbex.rickdex.ui.theme.RickdexTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      RickdexTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          HomeScreen(
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
  Text(
    text = "Rickdex",
    style = MaterialTheme.typography.headlineLarge,
    color = MaterialTheme.colorScheme.primary,
    modifier = modifier
  )
}


@Preview(name = "Light", showBackground = true)
@Preview(
  name = "Dark",
  showBackground = true,
  uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun HomeScreenPreview() {
  RickdexTheme(dynamicColor = false) {
    HomeScreen()
  }
}