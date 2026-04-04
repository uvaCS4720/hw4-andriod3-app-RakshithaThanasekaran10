@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package edu.nd.pmcburne.hello.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import android.util.Log

//composable for a dropdown menu to select a tag from a list
@Composable
fun TagDropdown(
    selectedTag: String,
    tags: List<String>,
    onTagSelected: (String) -> Unit
) {
    // tracks whether the dropdown menu is collapsed or expanded
    var expanded by remember { mutableStateOf(false) }

    // box that manages dropdown state and anchors menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
            Log.d("TagDropdown", "Dropdown expanded: $expanded")
        }
    ) {
        //text field showing the currently selected tag
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

        //actual drop down containing tags
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
