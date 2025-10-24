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
    onSubmit: (Person) -> Unit,
    onCancel: () -> Unit // 👈 new callback for cancel
) {
    var name by remember { mutableStateOf(existingPerson?.name ?: "") }
    var phone by remember { mutableStateOf(existingPerson?.phone ?: "") }
    var birthday by remember { mutableStateOf(existingPerson?.birthday ?: "") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var birthdayError by remember { mutableStateOf<String?>(null) }

    val isEditMode = existingPerson != null

    fun validateInput(): Boolean {
        var isValid = true

        if (!Regex("^[A-Za-zÆØÅæøå ]+$").matches(name.trim())) {
            nameError = "Navn kan kun inneholde bokstaver og mellomrom"
            isValid = false
        } else nameError = null

        if (!Regex("^\\d{8,}$").matches(phone.trim())) {
            phoneError = "Telefonnummer må være minst 8 sifre og kun tall"
            isValid = false
        } else phoneError = null

        if (!Regex("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$")
                .matches(birthday.trim())
        ) {
            birthdayError = "Dato må være på formatet dd-mm-yyyy"
            isValid = false
        } else birthdayError = null

        return isValid
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Navn") },
            isError = nameError != null,
            supportingText = { nameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Telefonnummer") },
            isError = phoneError != null,
            supportingText = { phoneError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text("Fødselsdato (dd-mm-yyyy)") },
            isError = birthdayError != null,
            supportingText = { birthdayError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    if (validateInput()) {
                        val person = existingPerson?.copy(
                            name = name.trim(),
                            phone = phone.trim(),
                            birthday = birthday.trim()
                        ) ?: Person(
                            name = name.trim(),
                            phone = phone.trim(),
                            birthday = birthday.trim()
                        )
                        onSubmit(person)
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isEditMode) "Lagre" else "Legg til")
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Avbryt")
            }
        }
    }
}
