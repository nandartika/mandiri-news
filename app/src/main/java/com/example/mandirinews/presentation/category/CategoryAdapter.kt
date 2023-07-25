package com.example.mandirinews.presentation.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mandirinews.data.model.category.Category
import com.example.mandirinews.databinding.ItemCategoryCardBinding

class CategoryAdapter(
	private val categoryList: List<Category>,
	val onClickCategoryItem: ((categoryId: String) -> Unit)? = null
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding =
			ItemCategoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun getItemCount(): Int = categoryList.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val category = categoryList[position]
		holder.bind(category)
	}

	inner class ViewHolder(private val binding: ItemCategoryCardBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(category: Category) {
			binding.categoryName.text = category.name
//			Glide.with(itemView.context).load().into(binding.icon)
			onClickCategoryItem?.invoke(category.id)
		}
	}
}