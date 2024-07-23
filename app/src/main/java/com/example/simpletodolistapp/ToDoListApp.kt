package com.example.simpletodolistapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventStart
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpletodolistapp.ui.theme.poppinsFontFamily

data class toDo(
    val name:String,
    val done: MutableState<Boolean> = mutableStateOf(false)
)
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ToDoListApp(){
    val toDos = remember { mutableStateOf(listOf<toDo>()) }
    val showAlert= remember {
        mutableStateOf(false)
    }
    var addTodoName by remember {
        mutableStateOf("")
    }
    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        LazyColumn (
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 56.dp)
                .padding(top = 20.dp)
        ){
            items(toDos.value){
                item ->
                ToDo(todo = item, onDeleteClick = {
                    toDos.value=toDos.value-item})
            }
        }
        IconButton(
            onClick = { showAlert.value=true },
            modifier= Modifier
                .padding(8.dp)
                .size(60.dp)
                .align(Alignment.BottomCenter),
            colors = IconButtonColors(Color(0xFF800080),Color.White,Color.DarkGray,Color.White)
            ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
        }
        if (showAlert.value){
            AlertDialog(
                modifier = Modifier.align(Alignment.Center),
                onDismissRequest = { showAlert.value=false },
                confirmButton = {
                Button(onClick = {
                    if (addTodoName.isNotBlank()){
                    showAlert.value = false
                    toDos.value = toDos.value + toDo(addTodoName)
                    addTodoName = ""
                    }},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF800080), // Background color
                        contentColor = Color.White // Text color
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                    ) {
                    Text(text = "Add", style = TextStyle(fontFamily = poppinsFontFamily))
                }
            },
                text = {
                    Column {
                        Text(text = "Add a to do", style = TextStyle(fontFamily = poppinsFontFamily, fontSize = 24.sp))
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(
                            colors =TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF800080)
                            ),
                            value = addTodoName,
                            onValueChange = {addTodoName=it},
                            label = { Text(text = "Enter a to do", style = TextStyle(fontFamily = poppinsFontFamily), modifier = Modifier.alpha(0.5f)
                        )})
                    }
                },

            )

        }
    }
}

@Composable
fun ToDo(todo:toDo,onDeleteClick:()->Unit){
    Box(modifier = Modifier
        .padding(4.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(Color(0xFF800080))
        .fillMaxWidth()


        ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
                .background(Color.White)
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
            )
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = todo.done.value,
                    onCheckedChange = { todo.done.value=!todo.done.value},
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF800080)
                    )
                )
                Text(text = todo.name, style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily))
            }
            IconButton(onClick = { onDeleteClick() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                
            }




        }
    }
}
@Preview(showBackground = true)
@Composable
fun ToDoListAppPreview(){
    ToDoListApp()
}

//@Preview(showBackground = true)
//@Composable
//fun ToDoPreview(){
//    ToDo()
//}

