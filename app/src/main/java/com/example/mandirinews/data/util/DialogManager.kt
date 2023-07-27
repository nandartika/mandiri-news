package com.example.mandirinews.data.util

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogManager(private val context: Context) {
	fun showSingleButtonDialog(
		message: String, onButtonClickListener: (() -> Unit)? = null
	) {
		MaterialAlertDialogBuilder(context).setTitle("Information").setMessage(message)
			.setPositiveButton("OK") { dialog, _ ->
				onButtonClickListener?.invoke()
				dialog.dismiss()
			}.setCancelable(false).show()
	}
}