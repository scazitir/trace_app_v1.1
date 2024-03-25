package com.example.traceapp.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController
) {
    val userName = "Amanda"
    val motivationPhrase = "Pursuing your goal can transform your life."

    val tasksMap = generateTasksForUpcomingDays()
    val selectedDate = remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)) }
    val completedTasks = remember { mutableStateMapOf<String, Boolean>() }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val (newTaskName, setNewTaskName) = remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentColor = Color.Black
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    IconButton(
                        onClick = {
                            navController.navigate("profile")
                        }) {
                        Icon(Icons.Filled.Person, "Profile")
                    }
                    FloatingActionButton(
                        onClick = { setShowDialog(true) },
                        contentColor = Color.White,
                        containerColor = Color.White,
                        elevation = FloatingActionButtonDefaults.elevation(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Color(0xFF8b55ff),
                                            Color(0xFF5edce7))
                                    ),
                                    shape = CircleShape
                                )
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Add a new task"
                            )
                        }
                    }

                    IconButton(onClick = { navController.navigate("login") }) {
                        Icon(Icons.Filled.ExitToApp, "Login")
                    }
                }

            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF8b55ff),
                                Color(0xFF5edce7)
                            )
                        )
                    )
                    .border(
                        width = 2.dp,
                        color = Color.Blue,
                        shape = RoundedCornerShape(10.dp)
                    )
            ){
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(120.dp)
                ) {
                    Text(
                        text = "Hey, $userName",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = motivationPhrase,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            HorizontalDateList(selectedDateString = selectedDate.value, availableDates = tasksMap.keys.toList()) { newDate ->
                selectedDate.value = newDate
            }
            Spacer(modifier = Modifier.height(30.dp))
            ToDoListContainer(tasksMap[selectedDate.value] ?: listOf(), completedTasks)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { setShowDialog(false) },
            title = { Text(text = "Add a new Task") },
            text = {
                OutlinedTextField(
                    value = newTaskName,
                    onValueChange = setNewTaskName,
                    label = { Text("Task Description") }
                )
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF292046),
                        contentColor = Color.White
                    ),
                    onClick = {
                    if (newTaskName.isNotBlank()) {
                        tasksMap[selectedDate.value]?.add(newTaskName)
                        completedTasks[newTaskName] = false
                        setShowDialog(false)
                        setNewTaskName("")
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(
                    onClick = { setShowDialog(false) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF292046),
                        contentColor = Color.White
                    )
                ) {
                    Text("Cancel")
                }

            }
        )
    }
}


@Composable
fun generateTasksForUpcomingDays(): Map<String, MutableList<String>> {
    // Define how many days ahead you want to generate tasks for
    val numberOfDays = 4 // This will include today and the next three days

    val tasksMap = remember {
        // LinkedHashMap to preserve the order of dates
        linkedMapOf<String, MutableList<String>>().apply {
            val today = LocalDate.now()
            for (i in 0 until numberOfDays) {
                val date = today.plusDays(i.toLong())
                val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd"))
                this[formattedDate] = mutableStateListOf("Task for $formattedDate")
            }
        }
    }

    return tasksMap
}

@Composable
fun HorizontalDateList(
    selectedDateString: String,
    availableDates: List<String>,
    onDateSelected: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.horizontalScroll(scrollState)) {
        availableDates.forEach { dateString ->
            Button(
                onClick = { onDateSelected(dateString) },
                modifier = Modifier.padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF292046),
                    contentColor = Color.White
                )
            ) {
                val textColor = if (dateString == selectedDateString) Color.Gray else Color.White
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .height(50.dp)
                ) {
                    Text(text = dateString.substring(0, 3), color = textColor, fontSize = 18.sp,)
                    Text(text = dateString.substring(3, 6), color = textColor, fontSize = 18.sp,)
                }

            }
        }
    }
}

@Composable
fun ToDoListContainer(tasks: List<String>, completedTasks: MutableMap<String, Boolean>) {
    val tasksCompletedCount = tasks.count { completedTasks[it] == true }

    val completionPercentage = if (tasks.isNotEmpty()) {
        tasksCompletedCount.toFloat() / tasks.size
    } else {
        0f
    }

    Box(
        modifier = Modifier
            .padding(start = 5.dp, top = 16.dp, end = 5.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF8b55ff),
                        Color(0xFF5edce7)),
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
            .padding(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                )
                .background(Color(0XFF292046))
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            CompletionMessageView(completionPercentage)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(34.dp)
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(15.dp)),

                    contentAlignment = Alignment.Center
                ) {
                    LinearProgressIndicator(
                        color = Color(0XFF94ecec),
                        progress = completionPercentage,
                        modifier = Modifier
                            .width(300.dp - 4.dp)
                            .height(10.dp)
                            .clip(RoundedCornerShape(15.dp))
                    )
                }
                Text(
                    text = "${(completionPercentage * 100).toInt()}%",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .padding(horizontal = 15.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF6D6DFA),
                                Color(0xFF56B4D3)
                            )
                        )
                    )
            )
            Spacer(modifier = Modifier.height(20.dp))
            tasks.forEach { task ->
                val isCompleted = completedTasks[task] ?: false

                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        CustomCheckbox(
                            checked = isCompleted,
                            onCheckedChange = { isChecked ->
                                completedTasks[task] = isChecked
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = task,
                            modifier = Modifier.weight(1f),
                            color = if (isCompleted) Color.Gray else Color.White
                        )
                    }
                }

            }
        }
    }
}

fun getCompletionMessage(completionProgress: Float): String {
    return when {
        completionProgress <= 0.3f -> "A good start, you can do it!"
        completionProgress <= 0.7f -> "You're making progress!"
        completionProgress < 0.99f -> "Wow! Almost there!"
        else -> "Congratulations! All done!"
    }
}

@Composable
fun CompletionMessageView(completionProgress: Float) {
    val message = getCompletionMessage(completionProgress)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 15.dp),
            text = message,
            color = Color.White
        )
    }

}

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val checkedGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF8b55ff), Color(0xFF5edce7))
    )
    val uncheckedGradient = Brush.linearGradient(
        colors = listOf(Color.White, Color.White)
    )

    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(if (checked) checkedGradient else uncheckedGradient)
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Checked",
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
//    DashboardScreen()
}
