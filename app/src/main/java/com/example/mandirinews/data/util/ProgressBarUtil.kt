package com.example.mandirinews.data.util

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.widget.ProgressBar
import com.example.mandirinews.R

class ProgressBarUtil(private val activity: Activity) {
	private lateinit var progressDialog: Dialog

	fun showProgressBar() {
		// Check if the progress dialog is already shown to avoid duplicate instances
		if (!::progressDialog.isInitialized) {
			val dialogView = LayoutInflater.from(activity).inflate(R.layout.progress_dialog, null)
			val progressBar: ProgressBar = dialogView.findViewById(R.id.progressBar)

			progressDialog = Dialog(activity)
			progressDialog.setContentView(dialogView)
			progressDialog.setCancelable(false)
			progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

			progressDialog.show()
		}
	}

	fun hideProgressBar() {
		// Dismiss the progress dialog if it's shown
		if (::progressDialog.isInitialized && progressDialog.isShowing) {
			progressDialog.dismiss()
		}
	}
}