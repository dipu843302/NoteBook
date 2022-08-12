package com.example.slidenavigation.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.slidenavigation.mvvm.NoteViewModel
import com.example.slidenavigation.room.Note
import com.example.slidenavigation.MainActivity
import com.example.slidenavigation.R
import com.example.slidenavigation.mvvm.NoteRepository
import com.example.slidenavigation.mvvm.NoteViewModelFactory
import com.example.slidenavigation.room.NoteDatabase
import com.example.slidenavigation.room.NotesDao
import java.util.*


class UpdateNoteFragment : Fragment() {

    private val args: UpdateNoteFragmentArgs by navArgs()

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

        val notes = args.updateNote



        return ComposeView(requireContext()).apply {
            setContent {
                UpdateNote(viewModel = viewModel, notes)
            }
        }
    }

    @Composable
    fun UpdateNote(viewModel: NoteViewModel, note: Note) {
        var title2 by remember { mutableStateOf(note.noteTitle) }
        var message2 by remember { mutableStateOf(note.noteDescription) }

        // Fetching the Local Context
        val mContext = LocalContext.current

        // Declaring integer values
        // for year, month and day
        val mYear: Int
        val mMonth: Int
        val mDay: Int

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        // Declaring a string value to
        // store date in string format
        var mDate2 by remember { mutableStateOf(note.dateAdded) }

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate2 = "$mDayOfMonth/${mMonth + 1}/$mYear"
            }, mYear, mMonth, mDay
        )
        Scaffold {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.Black),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,

                    )
                {
                    Card(
                        shape = RoundedCornerShape(25.dp, 25.dp, 25.dp, 25.dp),
                        modifier = Modifier.padding(
                            10.dp
                        )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    findNavController().navigateUp()
                                }
                        )
                    }
                    Column(
                        Modifier
                            .padding(15.dp),
                    )
                    {
                        Text(
                            text = "Notepad",
                            fontSize = 20.sp,
                            color = Color.White,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                        )

                    }
                    val context = LocalContext.current
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_save_24),
                        contentDescription = "",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(end = 10.dp)
                            .clickable {
                                if (title2.isNotEmpty() && message2.isNotEmpty() && mDate2.isNotEmpty()) {
                                    val note2 = Note(title2, message2, mDate2)
                                    note2.id = note.id
                                    viewModel.updateNote(note2)
                                    Toast
                                        .makeText(context, "Data updated", Toast.LENGTH_SHORT)
                                        .show()
                                    findNavController().navigateUp()
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "Fill the credentials",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }

                            }
                    )
                }

                TextField(
                    value = title2,
                    onValueChange = {
                        title2 = it
                    },
                    placeholder = { Text(text = "Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFF7F097)
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(MainActivity.myComposeColorInt),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Selected Date: $mDate2",
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 15.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_calendar_month_24),
                        contentDescription = "",
                        modifier = Modifier
                            .size(45.dp)
                            .clickable {
                                mDatePickerDialog.show()
                            }
                            .padding(end = 15.dp)
                    )
                }

                TextField(
                    value = message2, onValueChange = {
                        message2 = it
                    },
                    placeholder = { Text(text = "Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFF7F097)
                    )
                )
            }
        }

    }
}