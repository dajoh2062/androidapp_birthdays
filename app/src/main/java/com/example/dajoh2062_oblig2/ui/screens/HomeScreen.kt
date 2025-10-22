package com.example.dajoh2062_oblig2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dajoh2062_oblig2.data.Person
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.dajoh2062_oblig2.ui.components.AddButton
import com.example.dajoh2062_oblig2.ui.components.FriendCard
import com.example.dajoh2062_oblig2.ui.components.SettingsButton
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ViewModel,
    friends: List<Person>,
    onEdit: (Person) -> Unit,
    onDelete: (Person) -> Unit,
    onAddNew: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            AddButton(
                modifier = modifier,
                onClick = onAddNew
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(friends) { friend ->
                    FriendCard(
                        friend = friend,
                        onEdit = onEdit,
                        onDelete = onDelete
                    )
                }
            }

            SettingsButton(
                onClick = onSettings,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )

        }
    }
}




@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    val sampleFriends = listOf(
        Person(name = "Johan", phone = "12345678", birthday = "12.03.2000"),
        Person(name = "Maria", phone = "98765432", birthday = "05.09.1999")
    )
    val fakeviewModel= remember{PersonViewModel()}

    HomeScreen(
        navController = navController,
        viewModel= fakeviewModel,
        friends = sampleFriends,
        onEdit = {},
        onDelete = {},
        onAddNew = {},
        onSettings={}
    )
}