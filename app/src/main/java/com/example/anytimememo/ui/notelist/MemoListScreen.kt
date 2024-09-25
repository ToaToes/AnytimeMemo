package com.example.anytimememo.ui.notelist

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import com.example.anytimememo.data.DataSource
import com.example.anytimememo.ui.theme.AnytimeMemoTheme
import com.example.anytimememo.domain.model.Note
import com.example.anytimememo.ui.utility.Route
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun MemoListScreen(
    noteList: List<Note>,
    onNoteClick: (Note) -> Unit,
    onAddNoteClick: () -> Unit
){
    //Log.d("MyTag", "reaching here?")
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    Scaffold (
        floatingActionButton = {
            // there is no onClick = {} here
            FloatingActionButton(onClick = onAddNoteClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            LazyColumn(
                modifier = Modifier.background(color = Color.White),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 15.dp + padding.calculateTopPadding(),
                    bottom = 15.dp + padding.calculateBottomPadding()
                ),
            ) {
                item {
                    Text(
                        text = "  Memo",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(noteList) { note ->
                    ListItem(
                        headlineContent = {
                            var title = note.title
                            if(note.title.isEmpty()){
                                title = "Untitled"
                            }
                            Text(
                                text = title + " :  " + sdf.format(Date()),
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        supportingContent = {
                            Text(
                                text = note.content,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        modifier = Modifier
                            .background(color = Color.DarkGray)
                            .clickable(
                            onClick = {
                                onNoteClick(note)
                            }
                        )
                    )
                }
            }
        }
    }

    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Memo",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }*/
}

//Search Bar
/*@Composable
fun SearchBar(){

    var taskList by remember { mutableStateOf(listOf<String>()) }
    var task by remember { mutableStateOf(TextFieldValue("")) }
    var editingTaskIndex by remember { mutableIntStateOf(-1) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFE0F7FA), Color(0xFFB2EBF2)),
                    center = androidx.compose.ui.geometry.Offset(200f, 200f),
                    radius = 300f
                )
            )


    ){

        Text(
            text = "To-Do List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Row(modifier = Modifier.fillMaxWidth()){

            BasicTextField(value = task,
                onValueChange = {task = it},
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            Button(
                modifier = Modifier.background(Color.White),
                onClick = {
                if(task.text.isNotBlank()){
                    if(editingTaskIndex >= 0){
                        taskList = taskList.toMutableList().apply{
                            set(editingTaskIndex, task.text)
                        }
                        editingTaskIndex = -1
                    } else {
                        taskList = taskList + task.text
                    }
                    task = TextFieldValue("")
                }
            }){
                Text(if (editingTaskIndex >= 0) "Update" else "Add")
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        taskList.forEachIndexed{
                index, taskText ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){

                Text(text = taskText, style = MaterialTheme.typography.bodyLarge)

                Row{
                    TextButton(onClick = {
                        task = TextFieldValue(taskText)
                        editingTaskIndex = index
                    }){
                        Text("Edit")
                    }

                    TextButton(onClick = {
                        taskList = taskList.toMutableList().apply{
                            removeAt(index)
                        }
                    }){
                        Text("Remove")
                    }

                }

            }

        }

    }
}*/

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnytimeMemoTheme {
        MemoListScreen(
            noteList = listOf(
                Note(
                title = "Title",
                content = "Content",
                date = "Date")
            ),
            onNoteClick = {

            },
            onAddNoteClick = {

            }
        )
    }
}
