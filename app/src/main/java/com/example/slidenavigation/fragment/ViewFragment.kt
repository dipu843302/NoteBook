package com.example.slidenavigation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.slidenavigation.room.Note
import com.example.slidenavigation.MainActivity.Companion.myComposeColorInt
import com.example.slidenavigation.R

class ViewFragment : Fragment() {
    private val args: ViewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val note=args.notes

        return ComposeView(requireContext()).apply {
            setContent {
            NoteView(note)
            }
        }
    }

    @Composable
    fun NoteView(note: Note) {
        Column(modifier= Modifier
            .fillMaxSize()
            .background(myComposeColorInt)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Black),

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

                    Text(
                        text = "Notepad",
                        fontSize = 20.sp,
                        color = Color.White,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start=90.dp,top=10.dp)
                    )
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()) {
                Text(text = "Title:- ${note.noteTitle}", fontSize = 17.sp, modifier = Modifier.padding(start=10.dp,top=10.dp))
                Text(text = "Date:-  ${note.dateAdded}", fontSize = 18.sp, modifier = Modifier.padding(start=10.dp,top=10.dp))
                Text(text = "Description:- ${note.noteDescription}", fontSize = 18.sp, modifier = Modifier.padding(start=10.dp,top=10.dp))
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Default(){
       // NoteView()
    }

}