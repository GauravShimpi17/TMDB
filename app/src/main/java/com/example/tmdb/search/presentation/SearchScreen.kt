package com.example.tmdb.search.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.tmdb.R
import com.example.tmdb.Screen
import com.example.tmdb.core.presentation.component.TextWithStartIcon
import com.example.tmdb.home.data.remote.MovieApi
import com.example.tmdb.search.domain.model.Search
import com.example.tmdb.ui.theme.SearchBg
import com.example.tmdb.ui.theme.gradColor1
import com.example.tmdb.ui.theme.gradColor2
import com.example.tmdb.ui.theme.ratingTxtColor
import com.example.tmdb.ui.theme.summaryTxtColor
import com.example.tmdb.util.Resource
import java.util.Locale

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
//    SearchScreen(navController = NavController(LocalContext.current))
    SearchItem(
        image = R.drawable.bg_tmdb.toString(),
        title = "SpiderMan Far From Home",
        releaseDate = "2001",
        rating = 5.5,
        onClick = {}

    )
}
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchResultViewModel = hiltViewModel()
) {
    val bgColor = Brush.verticalGradient(
        listOf(gradColor1, gradColor2)
    )
    var query by remember { mutableStateOf("") }
    var page by remember { mutableIntStateOf(1) }

    val searchResults by viewModel.searchResults.collectAsState()

    LaunchedEffect(query, page) {
        if (query.isNotEmpty()) {
            viewModel.searchMovie(query, page)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = bgColor)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(top = 40.dp, start = 25.dp)
                .clickable { navController.navigateUp() }
        )
        SearchBar(
            onQueryChanged = { newQuery ->
                query = newQuery
                page = 1 // Reset page number on new search
            }
        )



       /* SearchList(list = searchResults.list,
            onClick = { navController.navigate(Screen.MovieDetailScreen(id = it)) },
            onLastReached = { TODO })
*/
        if (searchResults is Resource.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        } else {
            LazyColumn {
                items(searchResults.data ?: emptyList()) { item ->
                    SearchItem(
                        image = item.posterPath ?: "",
                        title = item.title,
                        releaseDate = item.releaseDate,
                        rating = item.voteAverage,
                        onClick = { navController.navigate(Screen.MovieDetailScreen(id = item.id)) }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchList(
    list: List<Search>,
    onClick: () -> Unit,
    onLastReached: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
){
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
            SearchItem(image = movie.posterPath ?: "",
                title = movie.title,
                releaseDate = movie.releaseDate,
                rating = movie.voteAverage) {
                onClick()
            }
        }
        item {
            if (isLoading) {
                SearchItemLoading()
            }
        }
    }
}

@Composable
fun SearchItemLoading(){
    Column {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.Black.copy(alpha = 0.5f)))
    }
}

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    releaseDate: String,
    rating: Double,
    onClick : () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 15.dp)
            .clip(RoundedCornerShape(5))
            .clickable { onClick() },
//            .border(1.dp, Color.Black, RoundedCornerShape(10)),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${MovieApi.IMAGE_BASE_URL}$image")
                .size(Size.ORIGINAL)
                .build()
        )

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
//                .padding(end = 5.dp)
                .size(100.dp, 120.dp)
                .clip(RoundedCornerShape(10))
        )
        Column {
            Text(
                text = title,
                fontSize = 22.sp,
                color = summaryTxtColor,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(
                        bottom = 5.dp
                    )
            )

            val ratingFormat = String.format(Locale.US, "%.1f", rating)
            val annotatedString = buildAnnotatedString {
                appendInlineContent(id = "imageId")
                append(ratingFormat)
            }
            val inlineContentMap = mapOf("imageId" to InlineTextContent(
                Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_rating),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null
                )
            })
            Row(
                modifier = Modifier.padding(bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = annotatedString,
                    inlineContent = inlineContentMap,
                    color = ratingTxtColor,
                    fontSize = 18.sp
                )
                TextWithStartIcon(
                    icon = Icons.Default.DateRange,
                    text = releaseDate.dropLast(6),
                    textSize = 18.sp,
                    iconColor = summaryTxtColor,
                    color = summaryTxtColor
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit
) {
    var searchValue by remember { mutableStateOf("") }
    TextField(
        placeholder = {
            Text(
                text = "Search",
                color = Color.White.copy(alpha = 0.5f)
            )
        },
        value = searchValue,
        onValueChange = {
            searchValue = it
            onQueryChanged(it)
        },
        textStyle = TextStyle(fontSize = 18.sp),
        maxLines = 1,
        singleLine = true,
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 10.dp)
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(50)),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White.copy(alpha = 0.5f)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SearchBg,
            unfocusedContainerColor = SearchBg,
            focusedTextColor = Color.White

        ),
    )
}

/*@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchResultViewModel = hiltViewModel()
) {
    val bgColor = Brush.verticalGradient(
        listOf(gradColor1, gradColor2)
    )
    var query by remember { mutableStateOf("") }
    var page by remember { mutableStateOf(1) }

    val searchResults by viewModel.searchResults.collectAsState()

    LaunchedEffect(query, page) {
        if (query.isNotEmpty()) {
            viewModel.searchMovie(query, page)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = bgColor)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(top = 40.dp, start = 25.dp)
                .clickable { navController.navigateUp() }
        )
        SearchBar(
            onQueryChanged = { newQuery ->
                query = newQuery
                page = 1 // Reset page number on new search
            }
        )

        LazyColumn {
            items(searchResults.data ?: emptyList()) { item ->
                item.posterPath?.let {
                    SearchItem(
                        image = it, // Placeholder for image
                        title = item.title,
                        releaseDate = item.releaseDate,
                        rating = item.voteAverage
                    )
                }
            }
        }

        if (searchResults is Resource.Loading) {
            // Show loading indicator if needed
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
            )
        }
    }
}


@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    releaseDate: String,
    rating: Double,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 15.dp)
            .clip(RoundedCornerShape(10))
            .border(1.dp, Color.Black, RoundedCornerShape(10)),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .size(Size.ORIGINAL)
                .data("${MovieApi.IMAGE_BASE_URL}${}")
                .build(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = painter),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 5.dp)
                .size(100.dp, 120.dp)
                .clip(RoundedCornerShape(10))
        )
        Column {
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            val ratingFormat = String.format(Locale.US, "%.1f", rating)
            val annotatedString = buildAnnotatedString {
                appendInlineContent(id = "imageId")
                append(ratingFormat)
            }
            val inlineContentMap = mapOf("imageId" to InlineTextContent(
                Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_rating),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null
                )
            })
            Row(
                modifier = Modifier.padding(bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = annotatedString,
                    inlineContent = inlineContentMap,
                    color = ratingTxtColor,
                    fontSize = 18.sp
                )
                TextWithStartIcon(
                    icon = Icons.Default.DateRange,
                    text = releaseDate,
                    textSize = 18.sp,
                    iconColor = Color.Black,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit
) {
    var searchValue by remember { mutableStateOf("") }
    TextField(
        placeholder = {
            Text(
                text = "Search",
                color = Color.White.copy(alpha = 0.5f)
            )
        },
        value = searchValue,
        onValueChange = {
            searchValue = it
            onQueryChanged(it)
        },
        maxLines = 1,
        singleLine = true,
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 10.dp)
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(50)),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White.copy(alpha = 0.5f)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SearchBg,
            unfocusedContainerColor = SearchBg,
            focusedTextColor = Color.White
        ),
    )
}*/
