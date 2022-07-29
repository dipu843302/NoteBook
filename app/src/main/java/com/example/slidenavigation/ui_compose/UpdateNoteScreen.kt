package com.example.slidenavigation.ui_compose


import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.notebook.mvvm.NoteViewModel
import com.example.notebook.room.Note
import com.example.slidenavigation.MainActivity.Companion.myComposeColorInt
import com.example.slidenavigation.R
import java.util.*

@Composable
fun UpdateNote(viewModel: NoteViewModel, navController: NavHostController,note:Note) {
    var title by remember { mutableStateOf(note.noteTitle) }
    var message by remember { mutableStateOf(note.noteDescription) }

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
    var mDate by remember { mutableStateOf(note.dateAdded) }

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
                                navController.navigateUp()
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
                                val note2 = Note(title, message, mDate)
                                note2.id = note.id
                                viewModel.updateNote(note2)
                                Toast
                                    .makeText(context, "Data updated", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigate("home_screen")
                            } else {
                                Toast
                                    .makeText(context, "Fill the credentials", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                )
            }

            TextField(
                value = title,
                onValueChange = {
                    title=it
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
                    .background(myComposeColorInt),
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
                    message =it
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

@Composable
@Preview(showBackground = true)
private fun Default2() {
    // UpdateNote()
}
