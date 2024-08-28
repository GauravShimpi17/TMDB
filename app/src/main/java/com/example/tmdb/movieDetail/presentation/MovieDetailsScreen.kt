package com.example.tmdb.movieDetail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.tmdb.R
import com.example.tmdb.Screen
import com.example.tmdb.core.presentation.component.MovieList
import com.example.tmdb.core.presentation.component.TextWithStartIcon
import com.example.tmdb.home.data.remote.MovieApi
import com.example.tmdb.home.presentation.MovieListOnLoading
import com.example.tmdb.home.presentation.TitleText
import com.example.tmdb.movieDetail.domain.model.Cast
import com.example.tmdb.ui.theme.btnGrad1
import com.example.tmdb.ui.theme.btnGrad2
import com.example.tmdb.ui.theme.ratingTxtColor
import com.example.tmdb.ui.theme.summaryTxtColor
import com.example.tmdb.ui.theme.summaryBg
import com.example.tmdb.util.Resource
import java.util.Locale

@Composable
fun MovieDetail(
    detailViewModel: MovieDetailViewModel = hiltViewModel(),
    navController: NavController,
    id: Long
) {
    val brushBtn = Brush.horizontalGradient(
        listOf(btnGrad1, btnGrad2)
    )

    val detailResource by detailViewModel.getMovieDetail.collectAsState()
    val recommendation by detailViewModel.getMovieRecommendations.collectAsState()
    LaunchedEffect(Unit) {
        detailViewModel.fetchDetails(id = id)
        detailViewModel.fetchRecommendations(id = id, page = 1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(summaryBg)
            .verticalScroll(rememberScrollState())
    ) {

        when (val detailsBackground = detailResource) {
            is Resource.Success -> detailsBackground.data?.let {
                if (it.backdropPath == null) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .background(summaryBg)
                    )
                } else {
                    SetBackground(movieTitle = it.title, bgImg = it.backdropPath, onBackPressed = {
                        navController.navigateUp()
                    })
                }
            }

            is Resource.Loading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .background(summaryBg)
            ) {

            }

            is Resource.Error -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .background(summaryBg)
            ) { Text("Error: ${detailsBackground.message}") }
        }
        when (val topSummary = detailResource) {
            is Resource.Success -> topSummary.data?.let {
                it.posterPath?.let { it1 ->
                    TopSummary(
                        poster = it1,
                        title = it.title,
                        movieRating = it.voteAverage,
                        year = it.releaseDate,
                        runtime = it.runtime,
                        genre = it.genres[0].name
                    )
                }
                Text(
                    text = it.overview,
                    fontSize = 16.sp,
                    color = summaryTxtColor,
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 10.dp)
                )
            }

            is Resource.Loading -> Text(text = "Loading...")
            is Resource.Error -> Text("Error: ${topSummary.message}")
        }



        Button(
            onClick = { },
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
//                    .padding(horizontal = 12.dp , vertical = 10.dp)
                .padding(vertical = 10.dp, horizontal = 30.dp)
                .clip(RoundedCornerShape(50))
                .background(brush = brushBtn)
                .height(40.dp)
        ) {
            TextWithStartIcon(
                icon = ImageVector.vectorResource(id = R.drawable.ic_trailer),
                text = "Watch Trailer"
            )
        }

        TitleText(title = "Cast")

        when (val castDetails = detailResource) {
            is Resource.Success -> CastList(list = castDetails.data?.credits?.map { cast ->
                Cast(id = cast.id, name = cast.name, profilePath = cast.profilePath)
            } ?: emptyList())

            is Resource.Loading -> MovieListOnLoading()
            is Resource.Error -> Text("Error: ${castDetails.message}")
        }

        TitleText(title = "Recommendations")
        if (recommendation.isLoading) {
            MovieListOnLoading()
        } else {
            MovieList(
                list = recommendation.list,
                onClick = { movie -> navController.navigate(Screen.MovieDetailScreen(movie.id)) },
                onLastReached = {
                    if (!recommendation.isLoading) detailViewModel.fetchRecommendations(
                        id, page = 1
                    )
                }
            )
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun Preview(){
    SetBackground(movieTitle = "abc", bgImg = R.drawable.bg_tmdb) {

    }
}*/
@Composable
fun SetBackground(
    movieTitle: String, bgImg: String, onBackPressed: () -> Unit
) {
    val brush = Brush.verticalGradient(
        listOf(Color.Transparent, Color(0xFF15151D))
    )
    var isImageClicked by remember {
        mutableStateOf(false)
    }
    val favIcon = if (isImageClicked) {
        rememberVectorPainter(Icons.Outlined.Favorite)
    } else {
        rememberVectorPainter(Icons.Outlined.FavoriteBorder)
    }
    val iconColor = ColorFilter.tint(if (isImageClicked) Color.Red else Color.Black)
    val backGroundImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).size(Size.ORIGINAL)
            .data("${MovieApi.IMAGE_BASE_URL}${bgImg}").build(), contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = backGroundImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 40.dp)
                .align(Alignment.TopStart)
                .fillMaxWidth()
        ) {
            TextWithStartIcon(icon = Icons.AutoMirrored.Filled.ArrowBack,
                text = movieTitle,
                textSize = 20.sp,
                color = Color.Black,
                iconColor = Color.Black,
                modifier = Modifier.clickable { onBackPressed() })


            Image(painter = favIcon,
                contentDescription = null,
                colorFilter = iconColor,
                modifier = Modifier
                    .noRippleClickable {
                        isImageClicked = !isImageClicked
                    }
                    .padding(8.dp))
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(brush = brush)
        )
    }
}


//@Preview(showBackground = true)
@Composable
fun TopSummary(
    poster: String,
    title: String,
    movieRating: Double,
    year: String,
    runtime: Long,
    genre: String
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(summaryBg)
    ) {
        val (image, text, rating, contentInfo) = createRefs()
        val posterImage = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current).size(Size.ORIGINAL)
                .data("${MovieApi.IMAGE_BASE_URL}${poster}").build(),
            contentScale = ContentScale.Crop
        )
        Image(
            modifier = Modifier
                .size(100.dp, 120.dp)
                .clip(RoundedCornerShape(10))
                .constrainAs(image) {
                    start.linkTo(parent.start, margin = 15.dp)
                    top.linkTo(parent.top)
                },
            painter = posterImage,
            contentDescription = null,
        )

        val ratingFormat = String.format(Locale.US, "%.1f", movieRating)
        val annotatedString = buildAnnotatedString {
            appendInlineContent(id = "imageId")
            append(ratingFormat.toString())
        }
        val inlineContentMap = mapOf("imageId" to InlineTextContent(
            Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_rating),
                contentDescription = null
            )
        })

        Text(text = annotatedString,
            inlineContent = inlineContentMap,
            color = ratingTxtColor,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(rating) {
                end.linkTo(parent.end, margin = 30.dp)
                top.linkTo(parent.top, margin = 20.dp)
            }
        )


        Text(text = title,
            color = summaryTxtColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .constrainAs(text) {
                    start.linkTo(image.end, margin = 5.dp)
                    bottom.linkTo(image.bottom, margin = 5.dp)
                }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 30.dp)
                .constrainAs(contentInfo) {
                    top.linkTo(image.bottom, margin = 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextWithStartIcon(icon = Icons.Default.DateRange, text = year.dropLast(6))
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(1.dp)
                    .height(20.dp)
                    .background(summaryTxtColor)
            )
            TextWithStartIcon(
                icon = ImageVector.vectorResource(id = R.drawable.ic_clock),
                text = runtime.toString()
            )
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(1.dp)
                    .height(20.dp)
                    .background(summaryTxtColor)
            )
            TextWithStartIcon(
                icon = ImageVector.vectorResource(id = R.drawable.ic_ticket), text = genre
            )


        }
    }
}



@Composable
fun CastList(
    list: List<Cast>, modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyRow(
        modifier = Modifier.padding(start = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),

        ) {
        items(items = list) { cast ->
            Column(
                modifier = Modifier.padding(bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(50))
                ) {
                    if (cast.profilePath.isNotBlank()) {
                        val castImage = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .size(Size.ORIGINAL)
                                .data("${MovieApi.IMAGE_BASE_URL}${cast.profilePath}")
                                .build(),
                            contentScale = ContentScale.Crop
                        )
                        Image(painter = castImage,
                            contentDescription = "Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .border(2.dp, Color.White, RoundedCornerShape(50))
                                .clickable { })
                    } else {
                        Image(
                            modifier = modifier
                                .fillMaxSize()
                                .border(2.dp, Color.White, RoundedCornerShape(50))
                                .background(summaryTxtColor),
                            imageVector = Icons.Outlined.Person,
                            contentDescription = cast.id.toString()
                        )
                    }
                }


                Text(
                    text = cast.name,
                    color = summaryTxtColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier
                        .width(60.dp)
                        .padding(top = 5.dp)
                )
            }
        }
    }
}


fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(indication = null, interactionSource = remember {
        MutableInteractionSource()
    }) {
        onClick()
    }
}