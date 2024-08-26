package com.example.tmdb.presentation.home

import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.tmdb.R
import com.example.tmdb.Screen
import com.example.tmdb.data.model.PopularDto
import com.example.tmdb.data.remote.MovieApi
import com.example.tmdb.domain.model.Movie
import com.example.tmdb.ui.theme.SearchBg
import com.example.tmdb.ui.theme.TMDBTheme
import com.example.tmdb.ui.theme.summaryTxtColor
import com.example.tmdb.util.Categories
import com.example.tmdb.util.Resource

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBTheme {
                Homepage(
                    headerTitle = getString(R.string.header_title),
                    navController = NavController(this)
                )
            }
        }
    }
}

@Composable
fun Homepage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    headerTitle: String = "What do you want to watch today ?",
    profileIcon: Int = R.drawable.ic_launcher_foreground,
    navController: NavController,
) {
    val gradColor1 = Color(0xFF8000FF)
    val gradColor2 = Color(0xFF000000)
    val brush = Brush.verticalGradient(
        listOf(gradColor1, gradColor2)
    )

    val movieListPopular by viewModel.movieListPopular.collectAsState()
    val movieListUpComing by viewModel.movieListUpcoming.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMovies(category = Categories.POPULAR.value, page = 1)
        viewModel.fetchMovies(category = Categories.UPCOMING.value, page = 1)
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
                    .fillMaxWidth()
                    .padding(top = 85.dp, bottom = 10.dp)
                    .padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = headerTitle,
                    color = Color.White,
                    fontSize = 26.sp,
                    lineHeight = 31.47.sp,
                    fontWeight = FontWeight.Medium,
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(SearchBg)
                    .clickable { navController.navigate(Screen.SearchPage) },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Search",
                    color = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier
                        .padding(horizontal = 25.dp, vertical = 15.dp),
                )
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp, vertical = 5.dp
                        )
                )
            }

            Category(
                list = stringArrayResource(id = R.array.category_list).toList()
            )

            TitleText(title = "Trending")

            if (movieListPopular.isLoading) {
                MovieListOnLoading()
            } else {
                MovieList(
                    list = movieListPopular.list,
                    onClick = { movie -> navController.navigate(Screen.MovieDetailScreen(movie.id)) },
                    onLastReached = {
                        if (!movieListPopular.isLoading) viewModel.fetchMovies(
                            Categories.POPULAR.value,
                            movieListPopular.page
                        )
                    }
                )
            }

            if (movieListUpComing.isLoading) {
                MovieListOnLoading()
            }
            TitleText(title = "Upcoming")
            MovieList(
                list = movieListUpComing.list,
                onClick = { movie -> navController.navigate(Screen.MovieDetailScreen(movie.id)) },
                onLastReached = {
                    if (!movieListUpComing.isLoading) viewModel.fetchMovies(
                        Categories.UPCOMING.value,
                        movieListUpComing.page
                    )
                }
            )
            /*when (val upcomingMovies = movieListUpComing) {
                is Resource.Error -> Text(text = "Error : ${ upcomingMovies.message }")
                is Resource.Loading -> MovieListOnLoading()
                is Resource.Success -> {
                    LazyRow(
                        modifier = Modifier.padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(movieListUpComing.data ?: emptyList()) { movie ->
                            MovieItem(image = movie.posterPath ?: "",
                                onClick = {
                                    navController.navigate(Screen.MovieDetailScreen(movie.id))
                                })
                        }
                    }
                }
            }*/
            /*when (val trendingMovies = movieListUpComing) {
                *//*is Resource.Success -> MovieList(
                    list = trendingMovies.data?.map { movieDto ->
                        Movie(id = movieDto.id, imageId = movieDto.posterPath)
                    } ?: emptyList(),
                    onLastReached = {},
                    onClick = {
                        navController.navigate(Screen.MovieDetailScreen(it.id))
                    }
                )

                is Resource.Loading ->
                    MovieListOnLoading()

                is Resource.Error ->
                    Text("Error: ${trendingMovies.message}")*//*
            }*/
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
                position = i, isActive = i == selectedItem, text = item
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
fun MovieListOnLoading(

) {
    LazyRow(
        modifier = Modifier.padding(start = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(6) {
            Spacer(
                modifier = Modifier
                    .size(130.dp, 200.dp)
                    .clip(RoundedCornerShape(10))
                    .background(Color.Black.copy(alpha = 0.2f))
            )
        }
    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    image: String,
    onClick: () -> Unit,
) {
    val painter = rememberAsyncImagePainter(
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(LocalContext.current)
            .size(Size.ORIGINAL)
            .data("${MovieApi.IMAGE_BASE_URL}${image}")
            .build(),
    )

    when (painter.state) {
        AsyncImagePainter.State.Empty -> {
            Log.d("LoadImg", "Empty")
        }

        is AsyncImagePainter.State.Error -> Box(
            modifier = Modifier
                .size(130.dp, 200.dp)
                .clip(RoundedCornerShape(10))
                .background(Color.Red.copy(alpha = 0.2f))
        ) {
        }

        is AsyncImagePainter.State.Loading -> {
            Spacer(
                modifier = Modifier
                    .size(130.dp, 200.dp)
                    .clip(RoundedCornerShape(10))
                    .background(Color.Black.copy(alpha = 0.2f))
            )
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                modifier = Modifier
                    .size(130.dp, 200.dp)
                    .clip(RoundedCornerShape(10))
                    .clickable { onClick() },
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = "Movie Image",
            )
        }
    }

}

@Composable
fun MovieList(
    list: List<Movie>,
    onClick: (Movie) -> Unit,
    onLastReached: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = Modifier
            .padding(start = 10.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        itemsIndexed(items = list) { i, movie ->
            if (i == list.size - 1) {
                onLastReached()
            }
            MovieItem(image = movie.imageId ?: "") {
                onClick(movie)
            }

        }
    }
}

@Composable
fun TitleText(
    title: String, modifier: Modifier = Modifier
) {
    Text(
        text = title,
        color = summaryTxtColor,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .then(modifier)
            .padding(start = 15.dp)
            .padding(top = 10.dp, bottom = 10.dp)
    )
}


/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePagePreview() {
    Homepage(
        headerTitle = "What do you want to watch today?",
        navController = NavController(())
    )
}*/
