package com.example.tmdb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tmdb.ui.theme.TMDBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        onBoarding(
                            titleText = "The Movie DB",
                            text2 = "Everything about movies, series, anime and much more.",
                            text3 = "Stay up to date with information about films, series, anime and much more.",
                            modifier = Modifier.padding(innerPadding)
                        )

                    }
                }
            }
        }
    }
}

private fun onCLick(context: Context) {
    Intent(context, HomeActivity::class.java).also {
        context.startActivity(it)
    }
}

@Composable
fun onBoarding(
    image: Int = R.drawable.bg_tmdb,
    titleText: String,
    text2: String,
    text3: String,
    modifier: Modifier
) {

    val context = LocalContext.current
    val color = Color(0xFF8000FF)
    val color2 = Color(0xFF540BA1)
    val brush = Brush.horizontalGradient(
        listOf(color, color2)
    )
    Box{
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.then(modifier)
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(text = titleText,
                color = Color.White,
                fontWeight = FontWeight.Bold,
//                fontStyle = FontStyle.Italic,
                fontSize = 15.sp)
            Text(text = text2,
                color = Color.White,
                fontWeight = FontWeight.Bold,
//                fontStyle = FontStyle.Italic,
                fontSize = 32.sp,
                lineHeight = 37.2.sp)
            Text(text = text3,
                color = Color.White,
                fontWeight = FontWeight.Bold,
//                fontStyle = FontStyle.Italic,
                fontSize = 15.sp,
                lineHeight = 18.7.sp)

            Button(
                onClick = { onCLick(context = context) },
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier
//                    .padding(horizontal = 12.dp , vertical = 10.dp)
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(brush = brush)
            ) {
                Text(
                    text = "Access",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 19.36.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TMDBTheme {
        onBoarding(
            titleText = "The Movie DB",
            text2 = "Everything about movies, series, anime and much more.",
            text3 = "Stay up to date with information about films, series, anime and much more.",
            modifier = Modifier.padding()
        )
    }
}
