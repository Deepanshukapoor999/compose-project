package com.deep.compose_project

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import com.deep.compose_project.bottomNavigate.BottomNavigationBar
import com.deep.compose_project.compose_project.screens.TaskDetailScreen
import com.deep.compose_project.screens.HomeScreen
import com.deep.compose_project.screens.LoginScreen
import com.deep.compose_project.screens.SplashScreen
import com.deep.compose_project.screens.TaskScreen
import com.deep.compose_project.ui.theme.Compose_ProjectTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

// Status bar background
        window.statusBarColor =
            ContextCompat.getColor(
                this,
                R.color.newBlueColor
            )

        // Status bar icons = white
        WindowCompat.getInsetsController(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = false

        setContent {
            Compose_ProjectTheme {
                AppNavigation()
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable ("splash") {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate("login") {
                        popUpTo("splash") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable("login") {

            LoginScreen(
                navController = navController,
                onClick = { number, password ->

                    Log.e(
                        "LOGIN",
                        "number = $number password = $password"
                    )

                    navController.navigate(
                        "home/$number/$password"
                    ) {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "home/{number}/{password}"
        ) { backStackEntry ->

            val number = backStackEntry.arguments?.getString("number") ?: ""

            val password = backStackEntry.arguments?.getString("password") ?: ""

            MainScreen(
                navController = navController,
                number = number,
                password = password
            )
        }

        composable("task") {
            TaskScreen(navController)
        }
        composable("taskDetail") {
            TaskDetailScreen(navController)
        }
    }
}




@Composable
fun MainScreen(navController : NavController ,
               number: String,
               password: String) {

    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Screen content
        when (selectedIndex) {

            0 -> {
                HomeScreen(navController,
                    number,
                        password)
            }

            1 -> {
                TaskScreen(navController)
            }

            2 -> {
//                CenterScreen()
            }

            3 -> {
//                MessagesScreen()
            }

            4 -> {
//                AboutScreen()
            }
        }

        // Bottom Navigation
        BottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(80.dp),
            selectedIndex = selectedIndex,
            onItemClick = { index ->
                selectedIndex = index
            }
        )
    }
}