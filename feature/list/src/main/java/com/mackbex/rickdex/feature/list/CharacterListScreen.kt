package com.mackbex.rickdex.feature.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.mackbex.rickdex.core.ui.components.BodyText
import com.mackbex.rickdex.domain.character.model.Character

@Composable
fun CharacterListRoute(
  onCharacterClick: (Int) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: CharacterListViewModel = hiltViewModel()
) {

  val query by viewModel.query.collectAsStateWithLifecycle()
  val characters = viewModel.characters.collectAsLazyPagingItems()

  CharacterListScreen(
    query = query,
    characters = characters,
    onQueryChange = viewModel::onQueryChange,
    onCharacterClick = onCharacterClick,
    modifier = modifier
  )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
  query: String,
  characters: LazyPagingItems<Character>,
  onQueryChange: (String) -> Unit,
  onCharacterClick: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TopAppBar(title = { Text("Characters") })
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      )

      Box(modifier = Modifier.fillMaxSize()) {
        when (val refresh = characters.loadState.refresh) {
          is LoadState.Loading -> {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
          }

          is LoadState.Error -> {
            Column(
              modifier = Modifier.align(Alignment.Center),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              BodyText(refresh.error.message ?: "failed load")
              Button(onClick = { characters.retry() }) { Text("Retry") }
            }
          }

          else -> {
            if (characters.itemCount == 0) {
              BodyText(
                text = "검색 결과가 없습니다",
                modifier = Modifier.align(Alignment.Center)
              )
            } else {
              LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
              ) {
                items(characters.itemCount, key = characters.itemKey { it.id }) { index ->
                  val character = characters[index]
                  if (character != null) {
                    CharacterCard(
                      character = character,
                      onClick = { onCharacterClick(character.id) }
                    )
                  }
                }

                if (characters.loadState.append is LoadState.Loading) {
                  item {
                    Box(
                      modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                      contentAlignment = Alignment.Center
                    ) {
                      CircularProgressIndicator()
                    }
                  }
                }
              }
            }

          }
        }
      }
    }
  }
}

