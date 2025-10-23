package com.example.dajoh2062_oblig2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dajoh2062_oblig2.data.Person

@Composable
fun FriendForm(
    modifier: Modifier = Modifier,
    existingPerson: Person? = null,
    onSubmit: (Person) -> Unit
) {
    var name by remember { mutableStateOf(existingPerson?.name ?: "") }
    var phone by remember { mutableStateOf(existingPerson?.phone ?: "") }
    var birthday by remember { mutableStateOf(existingPerson?.birthday ?: "") }

    val isEditMode = existingPerson != null

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text="Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text(text="Phone") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text(text="Birthday") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && phone.isNotBlank()) {
                    val person = existingPerson?.copy(
                        name = name,
                        phone = phone,
                        birthday = birthday
                    ) ?: Person(
                        name = name,
                        phone = phone,
                        birthday = birthday
                    )
                    onSubmit(person)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text=if (isEditMode) "Save Changes" else "Add Friend")
        }
    }
}
