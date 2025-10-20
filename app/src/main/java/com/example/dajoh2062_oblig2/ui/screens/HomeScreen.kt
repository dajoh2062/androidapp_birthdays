package com.example.dajoh2062_oblig2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController


@Composable

fun HomeScreen(
    navController: NavController,
    friends: List<Person>,
    onEdit: (Person) -> Unit,
    onDelete: (Person) -> Unit,
    onAddNew: () -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNew) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(friends) { friend ->

            FriendCard(friend = friend, onEdit = onEdit, onDelete = onDelete)
            }
        }
    }
}

@Composable
fun FriendCard(
    friend: Person,
    onEdit: (Person) -> Unit,
    onDelete: (Person) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(friend.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Telefon: ${friend.phone}")
            Text("Fødselsdato: ${friend.birthDate}")

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onEdit(friend) }) {
                    Text("Endre")
                }
                TextButton(onClick = { onDelete(friend) }) {
                    Text("Slett", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    val sampleFriends = listOf(
        Person(name = "Johan", phone = "12345678", birthDate = "12.03.2000"),
        Person(name = "Maria", phone = "98765432", birthDate = "05.09.1999")
    )

    HomeScreen(
        navController = navController,
        friends = sampleFriends,
        onEdit = {},
        onDelete = {},
        onAddNew = {}
    )
}