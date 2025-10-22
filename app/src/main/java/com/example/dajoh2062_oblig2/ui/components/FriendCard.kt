package com.example.dajoh2062_oblig2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dajoh2062_oblig2.data.Person

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
            Text("Fødselsdato: ${friend.birthday}")

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