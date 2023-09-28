package com.example.dinnertrayapp.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dinnertrayapp.R
import com.example.dinnertrayapp.data.OrderUiState

@Composable
fun OrderSummary(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    modifier: Modifier = Modifier
){
    val orderSummary = stringResource(
        R.string.order_details,
        orderUiState.maindish,
        orderUiState.sidedish,
        orderUiState.dessert,
        orderUiState.price
    )

    val newOrder = stringResource(id = R.string.new_order)

    val items = listOf(
        Pair("Main Dish", orderUiState.maindish),
        Pair("Side Dish", orderUiState.sidedish),
        Pair("Dessert ", orderUiState.dessert),
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                Text(item.first.uppercase())
                Text(text = item.second, fontWeight = FontWeight.Bold)
                Divider(thickness = 1.dp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            FormattedPriceLabel(
                subtotal = orderUiState.price,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f, false)
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSendButtonClicked(newOrder, orderSummary) }
                ) {
                    Text("Send")
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCancelButtonClicked
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Preview
@Composable
fun OrderSummaryPreview(){
    OrderSummary(
        orderUiState = OrderUiState("Test", "Test", "Test", "$300.00"),
        onSendButtonClicked = { subject: String, summary: String -> },
        onCancelButtonClicked = {},
        modifier = Modifier.fillMaxHeight()
    )
}