package com.example.listycity3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

@Composable
fun CityListScreen(
    cities: List<City>,
    modifier: Modifier = Modifier,
    onAddCity: (City) -> Unit,
    onUpdateCity: (City, City) -> Unit = { _, _ -> }
) {
    var newCity by remember { mutableStateOf("") }
    var newProvince by remember { mutableStateOf("") }
    var showFields by remember { mutableStateOf(false) }

    var selectedCity by remember { mutableStateOf<City?>(null) }
    var editCity by remember { mutableStateOf("") }
    var editProvince by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        // Floating Action Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showFields = !showFields
                }
            ) {
                Text("+")
            }
        }

        // Add City input fields
        if (showFields) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newCity,
                    onValueChange = { newCity = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = newProvince,
                    onValueChange = { newProvince = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (
                            newCity.isNotBlank() &&
                            newProvince.isNotBlank()
                        ) {
                            onAddCity(
                                City(
                                    name = newCity,
                                    province = newProvince
                                )
                            )

                            newCity = ""
                            newProvince = ""
                            showFields = false
                        }
                    }
                ) {
                    Text("Add City")
                }
            }
        }

        // Edit selected city
        if (selectedCity != null) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Edit City",
                    fontSize = 20.sp
                )

                OutlinedTextField(
                    value = editCity,
                    onValueChange = { editCity = it },
                    label = { Text("City") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = editProvince,
                    onValueChange = { editProvince = it },
                    label = { Text("Province") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        val oldCity = selectedCity

                        if (
                            oldCity != null &&
                            editCity.isNotBlank() &&
                            editProvince.isNotBlank()
                        ) {
                            onUpdateCity(
                                oldCity,
                                City(
                                    name = editCity,
                                    province = editProvince
                                )
                            )

                            selectedCity = null
                        }
                    }
                ) {
                    Text("Save Changes")
                }
            }
        }

        // City list
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(cities) { index, city ->

                CityRow(
                    city = city,
                    isSelected = selectedCity == city,
                    onClick = {
                        selectedCity = city
                        editCity = city.name
                        editProvince = city.province
                    }
                )

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(
    city: City,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                if (isSelected) Color.LightGray
                else Color.Transparent
            )
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onUpdateCity = { _, _ -> }
        )
    }
}