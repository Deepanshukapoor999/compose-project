package com.deep.compose_project.screens

import android.util.Log
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deep.compose_project.R
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.deep.compose_project.api.TaskViewModel
import com.deep.compose_project.model.TaskSummaryModel
import com.deep.compose_project.model.UserTotalTaskData
import com.deep.compose_project.model.UserTotalTaskModel
import com.deep.compose_project.states.TaskUiState
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen(navController : NavController?=null,viewModel: TaskViewModel = viewModel()) {


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var dataTask by remember { mutableStateOf(UserTotalTaskData()) }

    LaunchedEffect(Unit) {

        viewModel.getUserTotalTasks(
            token = "3411a09f060a63f51f2f172bff2fe61d",
            type = "month",
            month = 9,
            userId = 6,
            year = 2026
        )
    }





    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                Text(
                    text = "Menu",
                    modifier = Modifier.padding(20.dp),
                    fontSize = 22.sp
                )

                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = true,
                    onClick = {
                        scope.launch {
                            navController!!.navigate("task")
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Profile") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {

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
            // Your main screen content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 80.dp,
                        bottom = 80.dp,
                        start = 20.dp,
                        end = 20.dp
                    ).verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start,

            ) {

                Spacer(Modifier.height(20.dp))

                HomeDayChecker()

                HomeTaskBox(dataTask)

                Spacer(modifier = Modifier.padding(10.dp))

                HomeTaskSummary(dataTask)

                TodaysFocusCard(
                    todayTask = "5 tasks remaining",
                    onViewTasksClick = {
                        println("View Tasks clicked")
                    }
                )

                Spacer(Modifier.height(20.dp))

            }

            // Menu Button - Top Right
            hometoolBar(scope,drawerState)



        }

        when (uiState) {

            is TaskUiState.Loading -> {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

            }

            is TaskUiState.Success -> {

                val data = (uiState as TaskUiState.Success).data
                dataTask = data.data

            }

            is TaskUiState.Error -> {

                val message =
                    (uiState as TaskUiState.Error).message

                Text(
                    text = message,
                    color = Color.Red
                )
            }
        }
    }
}


@Composable
fun hometoolBar(scope: CoroutineScope, drawerState: DrawerState){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Menu
        Image(
            painter = painterResource(R.drawable.menu),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    scope.launch {
                        drawerState.open()
                    }
                },
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.width(20.dp))

        // Bell
        Image(
            painter = painterResource(R.drawable.bell),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                },
            contentScale = ContentScale.FillBounds
        )

        // Push next item to the right
        Spacer(
            modifier = Modifier.weight(1f)
        )

        // Last Image - Right Side
        Image(
            painter = painterResource(R.drawable.userimage),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(30.dp))
                .clickable {
                },
            contentScale = ContentScale.FillBounds
        )

    }

}


@Composable
fun HomeDayChecker(){
    Row() {
        Text("Night,Deepanshu Kapoor\uD83D\uDC4B", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.userimage),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable {

                },
            contentScale = ContentScale.FillBounds
        )
    }

    Text("Let's make today productive", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal))

}


@Composable
fun HomeTaskBox(data: UserTotalTaskData){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp).padding(top = 10.dp)){
        Image(
            painter = painterResource(R.drawable.home_task_bg),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )

        Box(modifier = Modifier.fillMaxSize().padding(start = 10.dp)) {
            Box(modifier = Modifier
                .size(30.dp)
                .align(alignment = Alignment.CenterStart).clip(RoundedCornerShape(30.dp)).background(color = Color.LightGray)) {
                Image(
                    painter = painterResource(R.drawable.pass_document),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp).align(alignment = Alignment.Center),
                    contentScale = ContentScale.FillBounds
                )
            }
            Column(modifier = Modifier.padding(start = 40.dp).align(alignment = Alignment.CenterStart)) {
                Text("Total Task", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.White))
                Text("${data.Total_assigned}", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color.White))
                Text("Assignment in september 2026", style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Normal, color = Color.White))

            }
        }

    }
}

@Composable
fun HomeTaskSummary(data: UserTotalTaskData){
    Text("Task Summary", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))

    var items = ArrayList<TaskSummaryModel>()
    items.add(TaskSummaryModel("New","${data.new}",0.3f,"20",R.color.lightBlue))
    items.add(TaskSummaryModel("In Progress","${data.In_progress}",0.5f,"40",R.color.lightYellow))
    items.add(TaskSummaryModel("Overdue","${data.Over_due}",0.7f,"60",R.color.lightPink))
    items.add(TaskSummaryModel("Complete","${data.Completed}",0.9f,"80",R.color.lightGreen))

    Spacer(modifier = Modifier.height(10.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(0.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.height(200.dp)
    ) {

        items(items.size) { i ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                itemTaskSummary(items[i].name,items[i].totalTask,items[i].progress,items[i].progressTxt+"%",items[i].color)
            }
        }
    }


}

@Composable
fun itemTaskSummary(name: String,total: String,progres: Float,progressTxt: String,color:Int){
    Row(modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .background(colorResource(color)).padding(10.dp)) {

        Box(modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(40.dp)).background(color = Color.Black)
            .align(alignment = Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(R.drawable.pass_document),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp).align(alignment = Alignment.Center),
                contentScale = ContentScale.FillBounds
            )
        }

        Column(modifier = Modifier.padding(4.dp)) {
            Text(name, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black))
            Text(total, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black))
            Row() {

                LinearProgressIndicator(
                    progress = { progres },
                    modifier = Modifier
                        .weight(0.3f)
                        .height(8.dp).padding(top = 4.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    color = Color.Gray,
                    trackColor = Color.Black
                )

                Text(progressTxt, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black, textAlign = TextAlign.Center), modifier = Modifier.weight(0.1f))

            }
        }

    }
}


@Composable
fun TodaysFocusCard(
    modifier: Modifier = Modifier,
    todayTask: String = "",
    onViewTasksClick: () -> Unit = {}
) {

    Card (
        modifier = modifier
            .fillMaxWidth(),

        shape = RoundedCornerShape(10.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // -------------------------
            // Round Icon Background
            // -------------------------
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(
                        color = colorResource(R.color.lightBlue)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(R.drawable.ic_target),
                    contentDescription = "Target",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(
                        colorResource(R.color.newBlueColor)
                    )
                )
            }


            // -------------------------
            // Text Section
            // -------------------------
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Today's Focus",

                    fontSize = 14.sp,

                    color = colorResource(
                        R.color.newBlueColor
                    ),

                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = todayTask,

                    fontSize = 12.sp,

                    color = Color.Gray,

                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Let's get those done!",

                    fontSize = 12.sp,

                    color = colorResource(
                        R.color.grey
                    ),

                    fontWeight = FontWeight.Bold
                )
            }


            // -------------------------
            // View Tasks Button
            // -------------------------
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        colorResource(R.color.newBlueColor)
                    )
                    .clickable {
                        onViewTasksClick()
                    }
                    .padding(
                        horizontal = 8.dp,
                        vertical = 6.dp
                    ),

                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "hello",

                    color = Color.White,

                    fontSize = 12.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.width(5.dp)
                )

                Image(
                    painter = painterResource(
                        R.drawable.ic_spinner_arrow
                    ),

                    contentDescription = "View Tasks",

                    modifier = Modifier
                        .size(22.dp)
                        .rotate(270f),

                    colorFilter = ColorFilter.tint(
                        Color.White
                    )
                )
            }
        }
    }
}
