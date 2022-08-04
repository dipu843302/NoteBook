package com.example.slidenavigation.fragment

import android.app.DatePickerDialog
import android.os.Bundle
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
import com.example.slidenavigation.mvvm.NoteViewModel
import com.example.slidenavigation.room.Note
import com.example.slidenavigation.MainActivity
import com.example.slidenavigation.R
import java.util.*


class AddNoteFragment : Fragment() {
    private lateinit var noteViewModel: NoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[NoteViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent {
                AddNotes(viewModel = noteViewModel)
            }
        }
    }

    @Composable
    fun AddNotes(viewModel: NoteViewModel) {
        var title by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }

        val mContext = LocalContext.current

        val mYear: Int
        val mMonth: Int
        val mDay: Int

        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        // Declaring a string value to
        // store date in string format
        var mDate by remember { mutableStateOf("") }

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
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
                                if (title.isNotEmpty() && message.isNotEmpty() && mDate.isNotEmpty()) {
                                    val note = Note(title, message, mDate)
                                    viewModel.addNote(note)
                                    Toast
                                        .makeText(context, "Data saved", Toast.LENGTH_SHORT)
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
                    value = title, onValueChange = {
                        title = it
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
                        .wrapContentHeight()
                        .background(MainActivity.myComposeColorInt),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Selected Date: $mDate",
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
                    value = message, onValueChange = {
                        message = it
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
