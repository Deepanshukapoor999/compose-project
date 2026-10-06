package com.deep.compose_project.screens

import android.os.Build
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deep.compose_project.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deep.compose_project.api.TaskViewModel
import com.deep.compose_project.model.AllTaskData
import com.deep.compose_project.model.AllTaskShowModel
import com.deep.compose_project.model.UserTotalTaskData
import com.deep.compose_project.states.AllTaskUiState
import com.deep.compose_project.states.TaskUiState
import com.deep.compose_project.utils.Constants
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TaskScreen(navController : NavController?=null,viewModel: TaskViewModel = viewModel()){
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    var taskList by remember {
        mutableStateOf<AllTaskShowModel?>(null)
    }
    val context = LocalContext.current

    var statusTop by remember {
        mutableStateOf("0")
    }
    var selectedDate by remember {
        mutableStateOf("")
    }

    Log.e("HowMany","HowMany")
///ApiCall
    val uiAllTaskState by viewModel.uiAllTaskState.collectAsStateWithLifecycle()
    LaunchedEffect(statusTop, selectedDate) {

        viewModel.getAllTasksToUserDateTimeWise(
            year = "2026",
            date = "2026-9-${selectedDate}",
            user_id = "6",
            month = "9",
            user_type = "4",
            token = Constants.token,
            timezone = "Asia/Kolkata",
            status = getStatus(statusTop),
            isMyTask = "2",
            searchByDeparment = "",
            searchBySubCompany = ""
        )
    }


///Ui
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
    {
        Image(
            painter = painterResource(R.drawable.homebackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        hometoolBar(scope,drawerState)
        // Your main screen content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 90.dp,
                    bottom = 80.dp,
                    start = 20.dp,
                    end = 20.dp
                )
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start,

            ) {

            StatusUi(){item->
                statusTop = item
                Log.e("SelectedStaus","SelectedStaus: "+statusTop)

            }

            Spacer(modifier = Modifier.height(10.dp))

            DateUi(){it->
                selectedDate = it
                Log.e("SelectedDate","SelectedDate "+selectedDate)
            }


            Spacer(modifier = Modifier.height(20.dp))


            LazyColumn(
                modifier = Modifier.padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                taskList?.let { data ->

                    if (
                        data.status.equals("true", ignoreCase = true) &&
                        data.data.isNotEmpty()
                    ) {

                        items(
                            items = data.data,
                            key = { it.TaskCode } // apna unique ID field
                        ) { task ->

                            TaskCard(task){
                                navController!!.navigate("taskDetail")
                            }
                        }

                    } else {

                        item {
                            dataNotFound(
                                if (data.message.isNotEmpty()) {
                                    data.message
                                } else {
                                    "No tasks available"
                                }
                            )
                        }
                    }
                }
            }

        }
    }



///ApiResponse
    when (val state = uiAllTaskState) {

        is AllTaskUiState.Loading -> {
            taskList = null
            Box(modifier = Modifier.fillMaxSize().clickable(
                enabled = true,
                onClick = { }
            ),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AllTaskUiState.Success -> {
            Log.d("TASK_API", "Success data = ${state.data}")
            Log.d("TASK_API", "status = ${state.data.status}")
            Log.d("TASK_API", "message = ${state.data.message}")
            Log.d("TASK_API", "data size = ${state.data.data.size}")

            taskList = state.data

        }

        is AllTaskUiState.Error -> {

            val message = (uiAllTaskState as AllTaskUiState.Error).message
            taskList = null
            Text(
                text = message,
                color = Color.Red
            )
        }
    }

}


@Composable
fun StatusUi(onClickItem:(String)-> Unit){
    Spacer(Modifier.height(20.dp))
    val items = listOf(
        "New",
        "Progress",
        "Overdue",
        "Closed",
        "All"
    )
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(items.size) { i ->
            val isSelected = selectedIndex == i

            Card(
                shape = if (isSelected) {RoundedCornerShape(10.dp)}else{RoundedCornerShape(0.dp)},
                modifier = Modifier.clickable {
                    selectedIndex = i
                    onClickItem(i.toString())
                },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) {
                        colorResource(R.color.newBlueColor)
                    } else {
                        colorResource(android.R.color.transparent)
                    }
                )
            ) {

                Text(
                    text = items[i],
                    modifier = Modifier.padding(
                        vertical = 6.dp,
                        horizontal = 4.dp
                    ),
                    color = if (isSelected) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )

            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateUi(onClickItem:(String)-> Unit) {

    val currentMonth = remember {
        YearMonth.now()
    }

    val today = LocalDate.now()

    val dates = remember(currentMonth) {
        (1..currentMonth.lengthOfMonth()).map {
            currentMonth.atDay(it)
        }
    }

    var selectedDate by remember {
        mutableStateOf(today)
    }

    // LazyRow scroll state
    val listState = rememberLazyListState()

    // Scroll to today's date when screen opens
    LaunchedEffect(Unit) {
        val todayIndex = dates.indexOf(today)

        if (todayIndex >= 0) {
            listState.scrollToItem(todayIndex)
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        items(dates) { date ->

            val isSelected = date == selectedDate

            Card(
                modifier = Modifier
                    .width(60.dp)
                    .clickable {
                        selectedDate = date
                        onClickItem(date.dayOfMonth.toString())
                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) {
                        colorResource(R.color.newBlueColor)
                    } else {
                        colorResource(R.color.lightGray)
                    }
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth() ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = date.dayOfMonth.toString(),
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) {
                            Color.White
                        } else {
                            Color.Black
                        }
                    )


                    Text(
                        text = date.dayOfWeek.getDisplayName(
                            java.time.format.TextStyle.SHORT,
                            Locale.getDefault()
                        ),
                        lineHeight = 14.sp,
                        fontSize = 12.sp,
                        color = if (isSelected) {
                            Color.White
                        } else {
                            Color.Black
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun TaskCard(task: AllTaskData,onClick:()-> Unit){
    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.lightGray)
        )
    )
    {

        Row(modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 10.dp)
            .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(color = Color.Black),
                contentAlignment = Alignment.CenterStart) {
                Image(
                    painter = painterResource(R.drawable.pass_document),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .align(alignment = Alignment.Center),
                    contentScale = ContentScale.FillBounds
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = task.TaskTitle,
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )


                Text(
                    text = task.TaskDescription,
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )


                Row() {
                    Text(
                        text = "Created By: " + task.AssignedTaskBy,
                        fontSize = 10.sp,
                        lineHeight = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = task.TaskCreatedTime,
                        fontSize = 10.sp,
                        lineHeight = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }




            }
        }
    }
}

@Composable
fun dataNotFound(msg: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = msg,
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )
    }
}
@JvmName("getStatus1")
fun getStatus(position:String): String {
   var status = when (position.toInt()) {
        0 -> "5"
        1 -> "4"
        2 -> "3"
        3 -> "1"
        4 -> "2"
        else -> {
            "-1"
        }
    }
    return status as String
}

