package com.example.uesanapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.uesanapp.calcular
import kotlin.math.pow

@Composable
fun Calculadora(){
    var monto by remember {mutableStateOf("")}
    var plazo by remember {mutableStateOf("")}
    var tasa by remember {mutableStateOf("")}
    var mes by remember{mutableStateOf(false)}

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text("CALCULADORA DE PRÉSTAMOS",
            style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = monto,
            onValueChange = {monto = it},
            label = { Text("Monto  del Préstamo")},
            placeholder = { Text("Monto  del Préstamo")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = plazo,
            onValueChange = {plazo = it},
            label = { Text("Plazo del Préstamo")},
            placeholder = { Text("Plazo del Préstamo")},
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Calcular en: ${if (mes) "Meses" else "Años"}")
            Switch(
                checked = mes,
                onCheckedChange = { mes = it }
            )
        }
        OutlinedTextField(
            value = tasa,
            onValueChange = {tasa = it},
            label = { Text("Tasa de Interés Anual (%)")},
            placeholder = { Text("Tasa de Interés Anual (%)")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        PopupWindowDialog()
        Button(
            onClick={
                val m = monto.toDoubleOrNull() ?: 0.0
                val p = plazo.toIntOrNull() ?: 0
                val t = tasa.toDoubleOrNull() ?: 0.0
                calcular(m, p, t, mes)

            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Calcular")
        }

    }

}

fun calcular(montoInicial: Double, plazoInicial: Int, tasaInicial: Double, mesInicial: Boolean) {
    // Creamos copias mutables de los parámetros
    val monto = montoInicial
    var plazo = plazoInicial
    var tasa = tasaInicial
    val mes = mesInicial
    if(!mes){
        plazo *= 12
    }
    tasa = (tasa/12)/100
    var cuota: Double
    cuota = monto*(tasa*((1+tasa).pow(plazo)))/((1+tasa).pow(plazo-1))

    var totalPagado: Double = cuota*plazo
    val interesTotal: Double = totalPagado - monto

}

@Composable
fun PopupWindowDialog() {
    // on below line we are creating variable for button title
    // and open dialog.
    val openDialog = remember { mutableStateOf(false) }
    val buttonTitle = remember {
        mutableStateOf("Show Pop Up")
    }
    val greenColor = Color(0xFF0F9D58)

    // on the below line we are creating a column
    Column(

        // in this column we are specifying
        // modifier to add padding and fill
        // max size
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),

        // on below line we are adding horizontal alignment
        // and vertical arrangement
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // on the below line we are creating a button
        Button(

            // on below line we are adding modifier.
            // and padding to it,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),

            // on below line we are adding
            // on click to our button
            onClick = {

                // on below line we are updating
                // boolean value of open dialog.
                openDialog.value = !openDialog.value

                // on below line we are checking if dialog is close
                if (!openDialog.value) {

                    // on below line we are updating value
                    buttonTitle.value = "Show Pop Up"
                }
            }
        ) {

            // on the below line we are creating a text for our button.
            Text(text = buttonTitle.value, modifier = Modifier.padding(3.dp))
        }

        // on below line we are creating a box to display box.
        Box {
            // on below line we are specifying height and width
            val popupWidth = 300.dp
            val popupHeight = 100.dp

            // on below line we are checking if dialog is open
            if (openDialog.value) {
                // on below line we are updating button
                // title value.
                buttonTitle.value = "Hide Pop Up"
                // on below line we are adding pop up
                Popup(
                    // on below line we are adding
                    // alignment and properties.
                    alignment = Alignment.TopCenter,
                    properties = PopupProperties()
                ) {

                    // on the below line we are creating a box.
                    Box(
                        // adding modifier to it.
                        Modifier
                            .size(popupWidth, popupHeight)
                            .padding(top = 5.dp)
                            // on below line we are adding background color
                            .background(greenColor, RoundedCornerShape(10.dp))
                            // on below line we are adding border.
                            .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))
                    ) {

                        // on below line we are adding column
                        Column(
                            // on below line we are adding modifier to it.
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp),
                            // on below line we are adding horizontal and vertical
                            // arrangement to it.
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // on below line we are adding text for our pop up
                            Text(
                                // on below line we are specifying text
                                text = "Welcome to Geeks for Geeks",
                                // on below line we are specifying color.
                                color = Color.White,
                                // on below line we are adding padding to it
                                modifier = Modifier.padding(vertical = 5.dp),
                                // on below line we are adding font size.
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}