package com.example.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.tmdb.ui.theme.TMDBTheme
import com.example.tmdb.ui.theme.summartTxtColor
import com.example.tmdb.ui.theme.summaryBg

class ShowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    MovieDetail(

                    )


                }
            }
        }
    }
}

@Composable
fun MovieDetail() {
    Column(modifier = Modifier.fillMaxSize()) {
        SetBackground()
        TopSummary()
        Text(
            text = "Após cumprir pena por um crime violento, Ruth volta ao convívio na sociedade, que se recusa a perdoar seu passado. Discriminada no lugar que já chamou de lar, sua única esperança é encontrar a irmã, que ela havia sido forçada a deixar para trás.",
            color = summartTxtColor,
            modifier = Modifier
                .background(summaryBg)
                .padding(30.dp)
        )
    }
}

//@Preview(showBackground = true)
@Composable
fun SetBackground() {
    val brush = Brush.verticalGradient(
        listOf(Color.Transparent, Color(0xFF15151D))
    )
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {

        Image(
            painter = painterResource(id = R.drawable.bg_tmdb),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(brush = brush)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TopSummary() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(summaryBg)
    ) {
        val (
            image,
            text,
            rating,
        ) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.bg_tmdb),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp, 100.dp)
                .clip(RoundedCornerShape(10))
                .constrainAs(image) {
                    start.linkTo(parent.start, margin = 30.dp)
                    top.linkTo(parent.top, margin = 20.dp)
                })
        Text(text = "Spiderman No Way Home",
            color = summartTxtColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .constrainAs(text) {
                    start.linkTo(image.end, margin = 10.dp)
                    bottom.linkTo(image.bottom, margin = 5.dp)
                })

        Row {
            Text(text = )
        }
    }
}


//@Preview(showBackground = true)
@Composable
fun MovieDetailPreview() {
    TMDBTheme {
        MovieDetail()
    }
}
