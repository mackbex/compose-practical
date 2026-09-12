package com.mackbex.rickdex.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mackbex.rickdex.core.ui.components.BodyText
import com.mackbex.rickdex.core.ui.components.LabelText
import com.mackbex.rickdex.domain.settings.model.ThemeMode


@Composable
fun SettingsRoute(
  onBack: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: SettingsViewModel = hiltViewModel()
) {
  val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
  val dynamicColor by viewModel.dynamicColor.collectAsStateWithLifecycle()

  SettingsScreen(
    themeMode = themeMode,
    dynamicColor = dynamicColor,
    onThemeModeChange = viewModel::setThemeMode,
    onDynamicColorChange = viewModel::setDynamicColor,
    onBack = onBack,
    modifier = modifier
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
  themeMode: ThemeMode,
  dynamicColor: Boolean,
  onThemeModeChange: (ThemeMode) -> Unit,
  onDynamicColorChange: (Boolean) -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("설정") },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "뒤로")
          }
        }
      )
    }
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      LabelText("테마", modifier = Modifier.padding(16.dp))

      ThemeMode.entries.forEach { mode ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .selectable(
              selected = themeMode == mode,
              onClick = { onThemeModeChange(mode) },
              role = Role.RadioButton
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          RadioButton(selected = themeMode == mode, onClick = null)
          Spacer(Modifier.width(12.dp))
          BodyText(
            when (mode) {
              ThemeMode.LIGHT -> "라이트"
              ThemeMode.DARK -> "다크"
              ThemeMode.SYSTEM -> "시스템 설정 따름"
            }
          )
        }
      }

      HorizontalDivider()

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .toggleable(
            value = dynamicColor,
            onValueChange = onDynamicColorChange,
            role = Role.Switch
          )
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        BodyText("배경화면 색상 사용", modifier = Modifier.weight(1f))
        Switch(checked = dynamicColor, onCheckedChange = null)
      }
    }
  }
}