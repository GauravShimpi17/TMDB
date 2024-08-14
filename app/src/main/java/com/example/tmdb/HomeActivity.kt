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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tmdb.ui.theme.SearchBg
import com.example.tmdb.ui.theme.TMDBTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        Homepage(
                            headerTitle = getString(R.string.header_title),
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Category(list: List<String>) {
    var selectedItem by remember { mutableIntStateOf(0) }
    LazyRow(
        modifier = Modifier
            .padding(start = 15.dp)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(list) { i, item ->
            SelectedCategory(
                position = i,
                isActive = i == selectedItem,
                text = item
            ) {
                selectedItem = it
            }

        }
    }
}

@Composable
fun SelectedCategory(
    position: Int,
    isActive: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    activeTextColor: Color = Color.White,
    inActiveTextColor: Color = Color.White.copy(alpha = 0.5f),
    activeBackground: Color = Color(0xFFFF1F8A),
    inActiveBackground: Color = Color.Transparent,
    onClick: (Int) -> Unit
) {
    val isSelectedModifier = if (isActive) {
        Modifier
            .clip(RoundedCornerShape(50))
            .background(color = activeBackground)
            .padding(horizontal = 20.dp, vertical = 5.dp)
    } else {
        Modifier
            .background(color = inActiveBackground)
            .clickable { onClick(position) }
            .padding(horizontal = 10.dp, vertical = 10.dp)
    }

    val selectedPosition = if (isActive) {
        activeTextColor
    } else {
        inActiveTextColor
    }

    Text(
        text = text,
        modifier = isSelectedModifier.then(modifier),
        color = selectedPosition,
        fontSize = 16.sp
    )
}


@Composable
fun Homepage(
    modifier: Modifier = Modifier,
    headerTitle: String,
    profileIcon: Int = R.drawable.ic_launcher_foreground,
) {
    val context = LocalContext.current
    val gradColor1 = Color(0xFF8000FF)
    val gradColor2 = Color(0xFF000000)
    val brush = Brush.verticalGradient(
        listOf(gradColor1, gradColor2)
    )
    var searchValue by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = brush)
            .verticalScroll(rememberScrollState())
            .then(modifier),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(start = 30.dp, top = 54.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = headerTitle,
                    color = Color.White,
                    fontSize = 26.sp,
                    lineHeight = 31.47.sp,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Image(
                    painter = painterResource(id = profileIcon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            2.dp, Color.White, RoundedCornerShape(50)
                        )
                )
            }
            TextField(
                placeholder = { Text(text = "Search", color = Color.White) },
                value = searchValue,
                onValueChange = { searchValue = it },
                maxLines = 1,
                singleLine = true,
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .clip(RoundedCornerShape(50)),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SearchBg,
                    unfocusedContainerColor = SearchBg,
                    focusedTextColor = Color.White
                ),

                )

            Category(
                list = stringArrayResource(id = R.array.category_list).toList()
            )

            Text(
                text = "Trending",
                color = Color.White,
                fontSize = 18.sp,
                lineHeight = 21.78.sp,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .padding(vertical = 10.dp)
            )
            MovieList()

            Text(
                text = "Most Popular",
                color = Color.White,
                fontSize = 18.sp,
                lineHeight = 21.78.sp,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .padding(vertical = 10.dp)
            )

            MovieList()
        }
    }
}

data class Movie(
    val title: String,
    val movieImg: Int
)

@Composable
fun MovieList() {
    val context = LocalContext.current
    LazyRow(
        modifier = Modifier.padding(start = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(6) {
            Image(
                painter = painterResource(id = R.drawable.bg_tmdb),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(130.dp, 200.dp)
                    .clip(RoundedCornerShape(5))
                    .clickable { showActivity(context) }
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePagePreview() {
    Homepage(
        headerTitle = "What do you want to watch today?"
    )
}

private fun showActivity(context: Context) {
    Intent(context, ShowActivity::class.java).also {
        context.startActivity(it)
    }
}