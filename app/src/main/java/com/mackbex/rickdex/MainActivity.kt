package com.mackbex.rickdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Rickdex",
      style = MaterialTheme.typography.headlineLarge,
      color = MaterialTheme.colorScheme.primary,
    )
    CounterScreen()
  }

}


@Composable
fun CounterScreen(modifier: Modifier = Modifier) {
  var count by rememberSaveable { mutableIntStateOf(0) }

  CounterDisplay(
    count = count,
    onIncrement = { count++ },
    modifier = modifier
  )
}


@Composable
fun CounterDisplay(
  count: Int,
  onIncrement: () -> Unit,
  modifier: Modifier = Modifier
) {

  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Text(
      text = "Count: $count",
      style = MaterialTheme.typography.headlineMedium
    )

    Button(onClick = onIncrement) {
      Text("incresed")
    }
  }
}


@Preview(name = "box", showBackground = true)
@Composable
private fun CounterPreview() {
  RickdexTheme(dynamicColor = false) {
    CounterScreen()
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