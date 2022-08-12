package com.example.slidenavigation.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.slidenavigation.mvvm.NoteViewModel
import com.example.slidenavigation.room.Note
import com.example.slidenavigation.MainActivity.Companion.myComposeColorInt
import com.example.slidenavigation.R
import com.example.slidenavigation.mvvm.NoteRepository
import com.example.slidenavigation.room.NoteDatabase
import com.example.slidenavigation.room.NotesDao
import com.example.slidenavigation.mvvm.NoteViewModelFactory

class HomeFragment : Fragment() {
    var noteChange: Note? = null


    private lateinit var viewModel: NoteViewModel
    private lateinit var repository: NoteRepository
    private lateinit var noteDao: NotesDao
    private lateinit var noteDatabase: NoteDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteDatabase= NoteDatabase.getDatabase(requireContext())
        noteDao=noteDatabase.getNotesDao()
        repository= NoteRepository(noteDao)
        val viewModelFactoryTest= NoteViewModelFactory(repository)
        viewModel= ViewModelProvider(this, viewModelFactoryTest)[NoteViewModel::class.java]

        return ComposeView(requireContext()).apply {
            setContent {
                HomeScreen()
            }
        }
    }

    @Composable
    fun HomeScreen() {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(myComposeColorInt),

            ) {
            Column {
                Text(
                    text = "NotePad",
                    fontSize = 20.sp,
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp)
                )
                SetRecyclerView()
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End

            ) {
                OutlinedButton(
                    onClick = {
                        findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
                    },
                    modifier = Modifier
                        .size(60.dp),

                    shape = CircleShape,
                    border = BorderStroke(5.dp, Color.Black),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)
                ) {
                    // Adding an Icon "Add" inside the Button
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "content description",
                        tint = Color.Black,
                    )
                }
            }
        }
    }

    @Composable
    fun SetRecyclerView() {

        val dataList: List<Note> by viewModel.allNote.collectAsState(initial = emptyList())
        LazyColumn {
            items(dataList) {
                DataLayout(note = it)
            }
        }
    }

    @Composable
    fun DataLayout(note: Note) {
        val mContext = LocalContext.current
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 5.dp, end = 5.dp, top = 5.dp)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(myComposeColorInt),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(25.dp, 25.dp, 25.dp, 25.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                noteChange = note
                                var notes =
                                    Note(note.noteTitle, note.noteDescription, note.dateAdded)
                                notes.id = note.id
                                val action =
                                    HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(
                                        notes
                                    )
                                findNavController().navigate(action)
                            }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .clickable {
                            val notes = Note(note.noteTitle, note.noteDescription, note.dateAdded)
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToViewFragment(notes)
                            findNavController().navigate(action)
                        },
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = note.noteTitle,
                        fontSize = 15.sp,
                        color = Color.Black,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                    )
                    Text(
                        text = note.noteDescription,
                        fontSize = 15.sp,
                        color = Color.Black,

                        )
                    Text(
                        text = note.dateAdded,
                        fontSize = 15.sp,
                        color = Color.Black,

                        )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            viewModel.deleteNote(note)
                            Toast
                                .makeText(mContext, "Deleted", Toast.LENGTH_SHORT)
                                .show()
                        }
                )
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun Default() {

    Text(text = "dipu")
}
   