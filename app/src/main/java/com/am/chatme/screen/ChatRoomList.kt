package com.am.chatme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.am.chatme.data.Room
import com.am.chatme.viewmodel.RoomViewModel

@Composable
fun  ChatRoomList(
    roomViewModel: RoomViewModel = viewModel(),
    onJoinClicked: (Room) -> Unit
) {
    val rooms by roomViewModel.rooms.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Chat Rooms", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Display a list of chat rooms
        LazyColumn {
            items(rooms) {
                room ->
                RoomItem(room = room, onJoinClicked = {onJoinClicked(room)})
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to create a new room
        Button(
            onClick = {
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Room")
        }

        if (showDialog){
            AlertDialog(onDismissRequest = {showDialog = true},
                title = {Text("Create new room")},
                text = {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {name = it},
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }, confirmButton = {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Button(onClick = {
                            if(name.isNotBlank()){
                                roomViewModel.createRoom(name)
                                showDialog = false

                            }
                            roomViewModel.loadRooms()
                        }) {
                            Text("Add")
                        }
                        Button(
                            onClick = {showDialog = false}
                        ) {
                            Text("Cancel")
                        }
                    }
                })
        }
    }
}


@Composable
fun RoomItem(room: Room, onJoinClicked:(Room) -> Unit) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween){
        Text(text = room.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal)
        OutlinedButton(
            onClick = {
                onJoinClicked(room)
            }
        ) {
            Text("Join")
        }
    }

}