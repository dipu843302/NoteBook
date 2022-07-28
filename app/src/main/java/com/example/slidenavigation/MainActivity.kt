package com.example.slidenavigation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.notebook.mvvm.NoteViewModel
import com.example.notebook.room.Note
import com.example.slidenavigation.ui.theme.SlideNavigationTheme
import com.example.slidenavigation.ui_compose.AddNotes
import com.example.slidenavigation.ui_compose.SplashScreen
import com.example.slidenavigation.ui_compose.UpdateNote
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    var noteChange: Note? = null
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]
        setContent {
            AnimatedApp()
        }
    }
    companion object {
        val myColorString = "#F7F097"
        val myComposeColorInt = Color(myColorString.toColorInt())
    }
    val tweenSpec = tween<IntOffset>(durationMillis = 500, easing = FastOutSlowInEasing)

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun AnimatedApp() {
        val navController= rememberAnimatedNavController()
        AnimatedNavHost(navController = navController, startDestination="splash_screen"){
            composable( "home_screen",
                enterTransition = { initial, _ ->
                    slideInHorizontally(initialOffsetX = { 300 }, animationSpec = tweenSpec)
                },
                exitTransition = { _, target ->
                    slideOutHorizontally(targetOffsetX = { -300 }, animationSpec = tweenSpec)
                },
                popEnterTransition = { initial, _ ->
                    slideInHorizontally(initialOffsetX = { -300 }, animationSpec = tweenSpec)
                },
                popExitTransition = { _, target ->
                    slideOutHorizontally(targetOffsetX = { 300 }, animationSpec = tweenSpec)
                }
            ){
                HomeScreen(navController)
            }
            composable("splash_screen"){
                SplashScreen(navController = navController)
            }
            composable("add_note",
                enterTransition = { initial, _ ->
                    slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tweenSpec)
                },
                exitTransition = { _, target ->
                    slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tweenSpec)
                },
                popEnterTransition = { initial, _ ->
                    slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tweenSpec)
                },
                popExitTransition = { _, target ->
                    slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tweenSpec)
                }
            ) {
                AddNotes(viewModel, navController)
            }
            composable("update_note",
                enterTransition = { initial, _ ->
                    slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tweenSpec)
                },
                exitTransition = { _, target ->
                    slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tweenSpec)
                },
                popEnterTransition = { initial, _ ->
                    slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tweenSpec)
                },
                popExitTransition = { _, target ->
                    slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tweenSpec)
                }) {
                noteChange?.let { UpdateNote(viewModel, navController, it) }
            }
        }
    }



    @Composable
    fun HomeScreen(navController: NavHostController) {

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
                    // textAlign = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp)
                )
                SetRecyclerView(navController = navController)
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
                        navController.navigate("add_note")
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
    fun SetRecyclerView(navController: NavHostController) {

        val dataList: List<Note> by viewModel.allNote.collectAsState(initial = emptyList())
        LazyColumn {
            items(dataList) {
                DataLayout(note = it, navController)
            }
        }
    }

    @Composable
    fun DataLayout(note: Note, navController2: NavHostController) {
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
                                navController2.navigate("update_note")
                            }
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(.9f),
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

