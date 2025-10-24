package com.example.dajoh2062_oblig2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dajoh2062_oblig2.R
import com.example.dajoh2062_oblig2.data.Person

@Composable
fun FriendForm(
    modifier: Modifier = Modifier,
    existingPerson: Person? = null,
    onSubmit: (Person) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(value = existingPerson?.name ?: "") }
    var phone by remember { mutableStateOf(value = existingPerson?.phone ?: "") }
    var birthday by remember { mutableStateOf(value = existingPerson?.birthday ?: "") }

    var nameError by remember { mutableStateOf<String?>(value = null) }
    var phoneError by remember { mutableStateOf<String?>(value = null) }
    var birthdayError by remember { mutableStateOf<String?>(value = null) }

    val isEditMode = existingPerson != null
    val validationName = stringResource(R.string.validation_name)
    val validationPhone = stringResource(R.string.validation_phone)
    val validationBirthday = stringResource(R.string.validation_birthday)

    fun validateInput(): Boolean {
        var isValid = true

        if (!Regex("^[A-Za-zÆØÅæøå ]+$").matches(name.trim())) {
            nameError = validationName
            isValid = false
        } else nameError = null

        if (!Regex("^\\d{8,}$").matches(phone.trim())) {
            phoneError = validationPhone
            isValid = false
        } else phoneError = null

        if (!Regex("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$")
                .matches(birthday.trim())
        ) {
            birthdayError = validationBirthday
            isValid = false
        } else birthdayError = null

        return isValid
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 12.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = stringResource(id=R.string.label_name)) },
            isError = nameError != null,
            supportingText = {
                nameError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text(text = stringResource(R.string.label_phone)) },
            isError = phoneError != null,
            supportingText = {
                phoneError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text(text = stringResource(id=R.string.label_birthday)) },
            isError = birthdayError != null,
            supportingText = {
                birthdayError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(height = 24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp)
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
                modifier = Modifier.weight(weight = 1f)
            ) {
                Text(
                    text = if (isEditMode)
                        stringResource(id=R.string.save)
                    else
                        stringResource(id=R.string.add)
                )
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(weight = 1f)
            ) {
                Text(text = stringResource(id=R.string.cancel))
            }
        }
    }
}
