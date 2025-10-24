package com.example.dajoh2062_oblig2.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.dajoh2062_oblig2.data.AppDatabase
import com.example.dajoh2062_oblig2.data.Person
import com.example.dajoh2062_oblig2.repositories.PersonRepository
import com.example.dajoh2062_oblig2.ui.components.AddButton
import com.example.dajoh2062_oblig2.ui.components.ConfirmDeletionDialog
import com.example.dajoh2062_oblig2.ui.components.FriendCard
import com.example.dajoh2062_oblig2.ui.components.SettingsButton
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: PersonViewModel,
    onEdit: (Person) -> Unit,
    onAddNew: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    // ✅ Collect the flow of people from the database
    val friends by viewModel.people.collectAsState()

    // Delete confirmation state
    var showDialog by remember { mutableStateOf(false) }
    var selectedFriend by remember { mutableStateOf<Person?>(null) }

    if (showDialog && selectedFriend != null) {
        ConfirmDeletionDialog(
            friendName = selectedFriend!!.name,
            onConfirm = {
                viewModel.removePerson(selectedFriend!!)
                showDialog = false
                selectedFriend = null
            },
            onCancel = {
                showDialog = false
                selectedFriend = null
            }
        )
    }

    Scaffold(
        floatingActionButton = { AddButton(modifier = modifier, onClick = onAddNew) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Venner (${friends.size})",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

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
                            onDelete = {
                                selectedFriend = friend
                                showDialog = true
                            }
                        )
                    }
                }

                SettingsButton(
                    onClick = onSettings,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val context = LocalContext.current // <-- move this outside 'remember'

    // Use fake / in-memory data instead of Application context
    val fakeRepository = remember {
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        PersonRepository(db.personDao())
    }

    // Pass a dummy Application to satisfy constructor
    val fakeApplication = Application()
    val viewModel = remember { PersonViewModel(fakeRepository, fakeApplication) }

    val navController = rememberNavController()
    HomeScreen(
        navController = navController,
        viewModel = viewModel,
        onEdit = {},
        onAddNew = {},
        onSettings = {}
    )
}
