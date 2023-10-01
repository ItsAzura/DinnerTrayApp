package com.example.dinnertrayapp.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dinnertrayapp.R

@Composable
fun StartOrdrer(
    onNextBtn:() -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.dinner),
                contentDescription = "",
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
            )
            Text(
                text = "Oder Dinner",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onNextBtn,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = if(isSystemInDarkTheme())
                    ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9ECAFF),
                        contentColor = Color(0xFF003258)
                    )
                else
                    ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0061A4),
                        contentColor = Color(0xFFFFFFFF)
                    )
                ,
                enabled = true,
                shape = RoundedCornerShape(size = 0.dp),
            ){
                Text(text = "Start Order")
            }
        }
        Row(modifier = Modifier.weight(1f, false)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ){
                Button(
                    onClick = onNextBtn,
                    modifier = modifier
                        .width(250.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green, contentColor = Color.Cyan),
                    border = BorderStroke(2.dp,Color.Cyan),
                    enabled = true,
                    shape = RoundedCornerShape(size = 0.dp),
                ){
                    Text(text = "Start Order")
                }
            }
        }

    }
}

@Preview
@Composable
fun StartOrderPreview(){
    StartOrdrer(
        onNextBtn = {},
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}