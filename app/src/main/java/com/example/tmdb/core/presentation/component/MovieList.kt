package com.example.tmdb.core.presentation.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.tmdb.core.data.model.Movie
import com.example.tmdb.home.data.remote.MovieApi
import com.example.tmdb.home.presentation.MovieListOnLoading

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    image: String,
    onClick: () -> Unit,
) {
    val painter = rememberAsyncImagePainter(
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(LocalContext.current)
            .data("${MovieApi.IMAGE_BASE_URL}${image}")
            .size(Size.ORIGINAL)
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
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
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
        item {
            if (isLoading) {
                MovieListOnLoading()
            }
        }
    }
}