package com.example.recipeapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.CategoryItemBinding

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryViewModelHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClicked : ((Category) -> Unit?)? = null

    fun setCategoryList(categoriesList: List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryViewModelHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewModelHolder {
        return CategoryViewModelHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewModelHolder, position: Int) {
        Glide.with(holder.itemView).load(categoriesList[position]
            .strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener {

            onItemClicked!!.invoke(categoriesList[position])

        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}