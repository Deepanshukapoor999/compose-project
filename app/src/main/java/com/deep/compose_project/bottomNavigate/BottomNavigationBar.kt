package com.deep.compose_project.bottomNavigate

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onItemClick: (Int) -> Unit
) {

    val items = listOf(
        "Home",
        "Task",
        "",
        "Messages",
        "About"
    )

    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.DocumentScanner,
        Icons.Default.Abc,
        Icons.Default.Message,
        Icons.Default.Person
    )

    NavigationBar(
        modifier = modifier
    ) {

        items.forEachIndexed { index, item ->

            NavigationBarItem(

                // Use selectedIndex from MainScreen
                selected = selectedIndex == index,

                onClick = {
                    // Tell MainScreen which tab was clicked
                    onItemClick(index)
                },

                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item
                    )
                },

                label = {
                    if (item.isNotEmpty()) {
                        Text(item)
                    }
                }
            )
        }
    }
}