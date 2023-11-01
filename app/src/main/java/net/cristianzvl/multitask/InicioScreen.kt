package net.cristianzvl.multitask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.cristianzvl.multitask.ViewModel.MultitaskViewModel
import net.cristianzvl.multitask.ViewModel.UiState


data class InfoItem(
    val name: String,
    val icon: ImageVector,
    val count: Int
)

@Composable
fun InicioScreen(
    multiViewModel: MultitaskViewModel
) {
    val multiUiState by multiViewModel.uiState.collectAsState()

    val info_items = listOf(
        InfoItem(
            name = stringResource(id = R.string.today_inicio),
            icon = Icons.Filled.DateRange,
            count = 1
        ),
        InfoItem(
            name = stringResource(id = R.string.notes_inicio),
            icon = Icons.Filled.Edit,
            count = multiUiState.countNotes
        ),
        InfoItem(
            name = stringResource(id = R.string.pro_inicio),
            icon = Icons.Filled.Build,
            count = multiUiState.countHomeworks
        )
    )

    Column {
        TopBar()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(info_items.size){ index ->
                val item = info_items[index]
                Card {
                    Column(
                        modifier = Modifier
                            .padding(PaddingValues(16.dp))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = item.count.toString(),
                                style = typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = item.name,
                            style = typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.start_title))
        }
    )
}
