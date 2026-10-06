package com.deep.compose_project.compose_project.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.deep.compose_project.api.TaskViewModel
import com.deep.compose_project.screens.getStatus
import com.deep.compose_project.states.AllTaskByIDUiState
import com.deep.compose_project.states.AllTaskUiState
import com.deep.compose_project.R
import com.deep.compose_project.utils.Constants


@Preview
@Composable
fun TaskDetailScreen(navController : NavController?=null,viewModel: TaskViewModel = viewModel()) {
///ApiCall
    val uiTaskByIdState by viewModel.uiTaskByIdState.collectAsStateWithLifecycle()
    val arrayList = remember {
        mutableListOf<String>()
    }
    LaunchedEffect(Unit) {
        arrayList.clear()
        viewModel.getTaskById(
            task_id="1264",
            timezone="Asia/Kolkata",
            user_id="6",
            token=Constants.token,
        )
    }

    ///ApiResponse
    when (val state = uiTaskByIdState) {
        is AllTaskByIDUiState.Loading -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable(
                    enabled = true,
                    onClick = {

                    }
                ),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AllTaskByIDUiState.Success -> {
            var taskList = state.data
            taskList?.data?.TaskAttachment.orEmpty().forEach { attachment ->
                arrayList.add(attachment)
            }
            Log.e("dsadasdasd","Dfdsfdsfdsfsd"+arrayList)
        }

        is AllTaskByIDUiState.Error -> {
            val message = (uiTaskByIdState as AllTaskByIDUiState.Error).message
            Text(
                text = message,
                color = Color.Red
            )
        }
    }

    Column(modifier = Modifier
        .background(colorResource(id = R.color.lightGray))
        .padding(10.dp)
        .fillMaxSize(1f)){
        if (arrayList.isNotEmpty()) {
            Log.e("sdsddasd","dsadsdasd"+arrayList)

            ImageViewPager(arrayList)
        }
    }





}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageViewPager(images:List<String>) {


    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { images.size }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth().clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.lightGray))

    )
    {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) { page ->

            AsyncImage(
                model = images[page],
                contentDescription = "Image ${page + 1}",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }



        // Page indicator
        Row(
            modifier = Modifier.fillMaxWidth().align(alignment = Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(colorResource(id = R.color.oldYellow))
                        .size(if (isSelected) 10.dp else 8.dp)
                        .background(
                            if (isSelected) {
                                Color.Black
                            } else {
                                Color.Gray
                            }
                        )
                )
            }
        }
    }
}