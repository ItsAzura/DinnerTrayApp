package com.example.dinnertrayapp

import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.dinnertrayapp.Screen.OrderSummary
import com.example.dinnertrayapp.Screen.SelectOption
import com.example.dinnertrayapp.Screen.StartOrdrer
import com.example.dinnertrayapp.data.DataSource


enum class DinnerScreen(val title: String){
    Start(title = "DinnerApp"),
    MainDish(title = "Chose Main Dish"),
    SideDish(title = "Chose Side Dish"),
    Dessert(title = "Chose Dessert"),
    Summary(title = "Order Summary")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DinnerAppBar(
    currentScreen: DinnerScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title =
        {
            Text(currentScreen.title)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DinnerApp(
    viewModel: ViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = DinnerScreen.valueOf(
        backStackEntry?.destination?.route ?: DinnerScreen.Start.name
    )
    Scaffold(
        topBar = {
            DinnerAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                //Kiểm tra xem trước không có màn hình nào
                navigateUp = { navController.navigateUp() }
                //Thực hiện việc điều hướng trở lại trang trước đó
            )
        }
    ){ innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = DinnerScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route =DinnerScreen.Start.name){
                StartOrdrer(onNextBtn = {
                    navController.navigate(DinnerScreen.MainDish.name)
                },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)

                )
            }
            composable(route =DinnerScreen.MainDish.name){
                SelectOption(
                    onNextButtonClicked = { navController.navigate(DinnerScreen.SideDish.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.maindish,
                    onSelectionChanged = { viewModel.setMainDish(it) },
                    modifier = Modifier.fillMaxHeight(),
                )
            }
            composable(route =DinnerScreen.SideDish.name){
                SelectOption(
                    onNextButtonClicked = { navController.navigate(DinnerScreen.Dessert.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.sidedish,
                    onSelectionChanged = { viewModel.setSideDish(it) },
                    modifier = Modifier.fillMaxHeight(),
                )
            }
            composable(route =DinnerScreen.Dessert.name){
                SelectOption(
                    onNextButtonClicked = { navController.navigate(DinnerScreen.Summary.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.dessert,
                    onSelectionChanged = { viewModel.setDessert(it) },
                    modifier = Modifier.fillMaxHeight(),
                )
            }
            composable(route =DinnerScreen.Summary.name){
                val context = LocalContext.current
                OrderSummary(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSendButtonClicked = {
                        subject: String, summary: String ->
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: ViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(DinnerScreen.Start.name, inclusive = false)
}

private fun shareOrder(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_dinner_order)
        )
    )
}