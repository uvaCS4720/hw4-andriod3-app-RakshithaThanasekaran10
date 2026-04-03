@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package edu.nd.pmcburne.hello.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import android.util.Log

@Composable
fun TagDropdown(
    selectedTag: String,
    tags: List<String>,
    onTagSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
            Log.d("TagDropdown", "Dropdown expanded: $expanded")
        }
    ) {
        OutlinedTextField(
            value = selectedTag,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Tag") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            tags.forEach { tag ->
                DropdownMenuItem(
                    text = { Text(tag) },
                    onClick = {
                        Log.d("TagDropdown", "Clicked tag: $tag")
                        expanded = false
                        onTagSelected(tag)
                    }
                )
            }
        }
    }
}
