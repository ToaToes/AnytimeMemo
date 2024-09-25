package com.example.anytimememo.ui.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anytimememo.ui.notelist.MemoListScreen
import com.example.anytimememo.ui.theme.AnytimeMemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit
) {
    //enable scroll state inside Content textfield
    val scrollState = rememberScrollState()
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    //Text(text = "Memo")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {onEvent(NoteEvent.NavigateBack)}
                    ){
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {onEvent(NoteEvent.DeleteNote)}
                    ){
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Note"
                        )
                    }
                }
            )
        }
    ){ padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(padding)
                .padding(
                    horizontal = 20.dp,
                    vertical = 15.dp
                )
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .background(color = Color(0xFFF0F0F0))
                    .fillMaxWidth(),
                value = state.title,
                onValueChange = {
                    onEvent(NoteEvent.OnTitleChange(it))
                },
                placeholder = {
                    Text(text = "Title")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .background(color = Color(0xFFF0F0F0))
                        .fillMaxWidth()
                        .heightIn(min = 150.dp, max = 400.dp),
                    value = state.content,
                    onValueChange = {
                        onEvent(NoteEvent.OnContentChange(it))
                    },
                    placeholder = {
                        Text(text = "Content")
                    }
                )
            }

            //Display Date HERE !!!!!
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Button(
                    onClick = {
                        onEvent(NoteEvent.OnSaveNote)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text(text = "Save",
                        fontSize = 18.sp,
                        color = Color.Black)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnytimeMemoTheme {
        MemoScreen(
            state = NoteState(),
            onEvent = {}
        )
    }
}