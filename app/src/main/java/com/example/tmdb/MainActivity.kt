package com.example.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.tmdb.home.presentation.Homepage
import com.example.tmdb.movieDetail.presentation.MovieDetail
import com.example.tmdb.search.presentation.SearchScreen
import com.example.tmdb.ui.theme.TMDBTheme
import com.example.tmdb.ui.theme.btnGrad1
import com.example.tmdb.ui.theme.btnGrad2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.OnboardingScreen) {
                composable<Screen.OnboardingScreen> {
                    OnBoarding(
                        titleText = "The Movie DB",
                        text2 = "Everything about movies, series, anime and much more.",
                        text3 = "Stay up to date with information about films, series, anime and much more.",
                        onClick = { navController.navigate(Screen.HomeScreen) }
                    )
                }
                composable<Screen.HomeScreen> {
                    Homepage(
                        navController =  navController
                    )
                }
                composable<Screen.MovieDetailScreen> {
                    MovieDetail(
                        navController = navController,
                        id = it.toRoute<Screen.MovieDetailScreen>().id
                    )
                }
                composable<Screen.SearchPage> {
                    SearchScreen(navController = navController)
                }
            }
        }
    }
}

/*private fun onCLick(context: Context) {
    Intent(context, HomeActivity::class.java).also {
        context.startActivity(it)
    }
}*/

@Composable
fun OnBoarding(
    image: Int = R.drawable.bg_tmdb,
    titleText: String,
    text2: String,
    text3: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val context = LocalContext.current
    val brushBtn = Brush.horizontalGradient(
        listOf(btnGrad1, btnGrad2)
    )

    Box {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .then(modifier)
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = titleText,
                color = Color.White,
                fontWeight = FontWeight.Bold,
//                fontStyle = FontStyle.Italic,
                fontSize = 15.sp
            )
            Text(
                text = text2,
                color = Color.White,
                fontWeight = FontWeight.Bold,
//                fontStyle = FontStyle.Italic,
                fontSize = 32.sp,
                lineHeight = 37.2.sp
            )
            Text(
                text = text3,
                color = Color.White,
                fontWeight = FontWeight.Bold,
//                fontStyle = FontStyle.Italic,
                fontSize = 15.sp,
                lineHeight = 18.7.sp
            )

            Button(
                onClick = { onClick() },
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier
//                    .padding(horizontal = 12.dp , vertical = 10.dp)
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(brush = brushBtn)
                    .height(40.dp)
            ) {
                Text(
                    text = "Explore",
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
        OnBoarding(
            titleText = "The Movie DB",
            text2 = "Everything about movies, series, anime and much more.",
            text3 = "Stay up to date with information about films, series, anime and much more.",
            modifier = Modifier.padding(),
            onClick = {}
        )
    }
}

@Serializable
object Screen{
    @Serializable
    object OnboardingScreen

    @Serializable
    object HomeScreen

    @Serializable
    data class MovieDetailScreen(val id: Long)

    @Serializable
    object SearchPage
}
