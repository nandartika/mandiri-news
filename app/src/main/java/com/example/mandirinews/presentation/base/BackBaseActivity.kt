package com.example.mandirinews.presentation.base

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

abstract class BackBaseActivity : AppCompatActivity() {
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> {
				onBackPressedDispatcher.onBackPressed()
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}
}