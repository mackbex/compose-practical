package com.mackbex.rickdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Rickdex",
      style = MaterialTheme.typography.headlineLarge,
      color = MaterialTheme.colorScheme.primary,
      modifier = modifier
    )
    Text(
      text = "Rick and Morty 캐릭터 도감",
      style = MaterialTheme.typography.bodyMedium,
      modifier = modifier
    )
  }

}


@Composable
fun ModifierOrderDemo() {
  Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

    Box(
      Modifier
        .padding(16.dp)
        .background(Color.Red)
        .size(80.dp)
    )
    Box(
      Modifier
        .background(Color.Blue)
        .padding(16.dp)
        .size(80.dp)
    )
  }
}


@Preview(name = "box", showBackground = true)
@Composable
private fun ModifierOrderPreview() {
  RickdexTheme(dynamicColor = false) {
    ModifierOrderDemo()
  }
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