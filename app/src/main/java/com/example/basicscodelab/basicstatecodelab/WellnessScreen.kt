package com.example.basicscodelab.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

// Here, we are passing the viewmodel which has the business logic for the list checkbox and item removal
@Composable
fun WellnessScreen(modifier: Modifier = Modifier, wellnessViewModel: WellnessViewModel = viewModel()) {
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList(
            list = wellnessViewModel.tasks,
            // calling viewmodel method to implement the logic
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
                            },
            onCloseTask = { task -> wellnessViewModel.remove(task) })
    }
}