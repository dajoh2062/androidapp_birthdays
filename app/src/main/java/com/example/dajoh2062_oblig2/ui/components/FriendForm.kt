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
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(value=existingPerson?.name ?: "") }
    var phone by remember { mutableStateOf(value=existingPerson?.phone ?: "") }
    var birthday by remember { mutableStateOf(value=existingPerson?.birthday ?: "") }

    var nameError by remember { mutableStateOf<String?>(value=null) }
    var phoneError by remember { mutableStateOf<String?>(value=null) }
    var birthdayError by remember { mutableStateOf<String?>(value=null) }

    val isEditMode = existingPerson != null

    fun validateInput(): Boolean {
        var isValid = true

        if (!Regex(pattern="^[A-Za-zÆØÅæøå ]+$").matches(input=name.trim())) {
            nameError = "Navn kan kun inneholde bokstaver og mellomrom"
            isValid = false
        } else nameError = null

        if (!Regex(pattern="^\\d{8,}$").matches(input=phone.trim())) {
            phoneError = "Telefonnummer må være minst 8 sifre og kun tall"
            isValid = false
        } else phoneError = null

        if (!Regex(pattern="^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$")
                .matches(input=birthday.trim())
        ) {
            birthdayError = "Dato må være på formatet dd-mm-yyyy"
            isValid = false
        } else birthdayError = null

        return isValid
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all=16.dp),
        verticalArrangement = Arrangement.spacedBy(space=12.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text="Navn") },
            isError = nameError != null,
            supportingText = { nameError?.let { Text(text=it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text(text="Telefonnummer") },
            isError = phoneError != null,
            supportingText = { phoneError?.let { Text(text=it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text(text="Fødselsdato (dd-mm-yyyy)") },
            isError = birthdayError != null,
            supportingText = { birthdayError?.let { Text(text=it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(height=24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space=12.dp)
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
                modifier = Modifier.weight(weight=1f)
            ) {
                Text(text=if (isEditMode) "Lagre" else "Legg til")
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(weight=1f)
            ) {
                Text(text="Avbryt")
            }
        }
    }
}
