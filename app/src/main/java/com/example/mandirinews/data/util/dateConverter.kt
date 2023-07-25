package com.example.mandirinews.data.util

import java.text.SimpleDateFormat
import java.util.Locale

fun dateConverter(inputDateString: String): String {
	// Parse the input string into a Date object
	val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
	val outputFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

	val date = inputFormatter.parse(inputDateString)

	// Format the Date object into the desired output string
	return outputFormatter.format(date)
}