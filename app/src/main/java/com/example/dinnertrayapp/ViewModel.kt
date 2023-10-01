package com.example.dinnertrayapp

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.dinnertrayapp.data.OrderUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))

    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun setMainDish(mainDish:String){
        _uiState.update { currentState ->
            currentState.copy(
                maindish = mainDish,
                price = calPrice(mainDish = mainDish)
            )
        }
    }
    fun setSideDish(sideDish:String){
        _uiState.update { currentState ->
            currentState.copy(
                sidedish = sideDish,
                price = calPrice(sideDish = sideDish)
            )

        }
    }
    fun setDessert(dessert_Dish:String){
        _uiState.update { currentState ->
            currentState.copy(
                dessert = dessert_Dish,
                price = calPrice(Dessert = dessert_Dish)
            )

        }
    }
    fun resetOrder(){
        _uiState.value = OrderUiState(pickupOptions = pickupOptions())
    }

    @SuppressLint("SuspiciousIndentation")
    private fun calPrice(
        mainDish: String =  _uiState.value.maindish,
        sideDish: String = _uiState.value.sidedish,
        Dessert: String = _uiState.value.dessert,
    ):String{
        var Main_Dish : Double = 0.0
        var Side_Dish : Double = 0.0
        var Dessert_Dish : Double = 0.0

        Main_Dish =
            if(mainDish == "Grilled Salmon with Lemon-Dill Sauce") 5.00
            else if(mainDish == "Vegetable Stir-Fry with Tofu") 4.00
            else if(mainDish == "Chicken Alfredo Pasta") 6.00
            else if(mainDish == "Beef and Vegetable Stir-Fry") 7.00
            else if(mainDish == "Mushroom Risotto") 4.00
            else 0.0

        Side_Dish =
            if(sideDish == "Garlic Roasted Asparagus" ) 2.00
            else if(sideDish == "Mashed Potatoes" ) 3.00
            else if(sideDish == "Caesar Salad" ) 4.00
            else if(sideDish == "Roasted Brussels Sprouts with Balsamic Glaze" ) 5.00
            else if(sideDish == "Caprese Salad" ) 6.00
            else 0.0

        Dessert_Dish =
            if(Dessert == "Chocolate Fondue" ) 2.00
            else if(Dessert == "Classic Apple Crisp" ) 2.00
            else if(Dessert == "Lemon Bars" ) 3.00
            else if(Dessert == "Chocolate Lava Cake" ) 4.00
            else if(Dessert == "Fresh Berry Parfait" ) 5.00
            else 0.0


        var calPrice: Double = 0.0
        calPrice = if(Main_Dish != 0.0 && Side_Dish != 0.0 && Dessert_Dish != 0.0)
            Main_Dish + Side_Dish + Dessert_Dish
        else 0.0
        val formattedPrice = NumberFormat.getCurrencyInstance().format(calPrice)
        return formattedPrice
    }

    private fun pickupOptions(): List<String>{
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}