package com.example.dajoh2062_oblig2.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val friends by viewModel.people.collectAsState()

    var showDialog by remember { mutableStateOf(value=false) }
    var selectedFriend by remember { mutableStateOf<Person?>(value=null) }

    if (showDialog && selectedFriend != null) {
        ConfirmDeletionDialog(
            friendName = selectedFriend!!.name,
            onConfirm = {
                viewModel.removePerson(person=selectedFriend!!)
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
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start=32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SettingsButton(
                    modifier = Modifier,
                    onClick = onSettings
                )
                AddButton(
                    modifier = Modifier,
                    onClick = onAddNew
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues=padding)
        ) {
            Spacer(Modifier.height(height=16.dp))
            Text(
                text = "Venner (${friends.size})",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(space=12.dp)
            ) {
                items(items=friends) { friend ->
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
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val context = LocalContext.current

    val fakeRepository = remember {
        val db = Room.inMemoryDatabaseBuilder(context, klass=AppDatabase::class.java).build()
        PersonRepository(dao=db.personDao())
    }

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
