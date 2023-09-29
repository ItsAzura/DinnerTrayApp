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


private const val Main_Dish = 6.00
private const val Side_Dish = 5.00
private const val Dessert_Dish = 4.00

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
    fun setDessert(Dessert_dish:String){
        _uiState.update { currentState ->
            currentState.copy(
                dessert = Dessert_dish,
                price = calPrice(dessert = Dessert_dish)
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
        dessert: String = _uiState.value.dessert,
    ):String{
      var calPrice : Double = 0.0
        calPrice = Main_Dish + Side_Dish + Dessert_Dish
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