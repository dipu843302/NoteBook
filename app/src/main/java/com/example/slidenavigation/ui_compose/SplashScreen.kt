package com.example.slidenavigation.ui_compose

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.slidenavigation.MainActivity.Companion.myComposeColorInt
import com.example.slidenavigation.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember {
        Animatable(0f)
    }

    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 400,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        // Customize the delay time
        delay(2000L)
        navController.navigate("home_screen")
    }

    // Image
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(myComposeColorInt)
    ) {
        // Change the logo
        Image(
            painter = painterResource(id = R.drawable.splash_icon),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}