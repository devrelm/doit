package com.devrelm.doit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.devrelm.doit.navigation.TopLevelDestination
import com.devrelm.doit.navigation.previousDestinationOnBack
import com.devrelm.doit.now.NowScreen
import com.devrelm.doit.nudges.NudgesScreen
import com.devrelm.doit.tasks.TasksScreen
import com.devrelm.doit.ui.theme.DoItTheme

@Composable
fun DoItApp() {
    var selectedDestination by rememberSaveable { mutableStateOf(TopLevelDestination.START_DESTINATION) }

    previousDestinationOnBack(selectedDestination)?.let { previousDestination ->
        BackHandler {
            selectedDestination = previousDestination
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                TopLevelDestination.entries.forEach { destination ->
                    val selected = destination == selectedDestination
                    NavigationBarItem(
                        selected = selected,
                        onClick = { selectedDestination = destination },
                        icon = {
                            Icon(
                                painter = painterResource(
                                    id = if (selected) destination.selectedIconRes else destination.unselectedIconRes,
                                ),
                                contentDescription = stringResource(destination.labelRes),
                            )
                        },
                        label = { Text(stringResource(destination.labelRes)) },
                    )
                }
            }
        },
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        when (selectedDestination) {
            TopLevelDestination.NOW -> NowScreen(modifier = contentModifier)
            TopLevelDestination.TASKS -> TasksScreen(modifier = contentModifier)
            TopLevelDestination.NUDGES -> NudgesScreen(modifier = contentModifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DoItAppPreview() {
    DoItTheme {
        DoItApp()
    }
}
