package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularItemsAdapter:MostPopularAdapter
    private lateinit var CategoryAdapter:CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.example.recipeapp.fragments.idMeal"
        const val MEAL_NAME = "com.example.recipeapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.recipeapp.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.recipeapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        observeRandomMeal()
        onRandomMealClick()



        viewModel.getPopularItems()
        observePopularItemLivedata()
        onPopularItemClicked()


        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observeCategoriesItemLivedata()


        onCategoryClicked()

        onPopularItemLongClicked()

        onSearchItemCLicked()

    }

    private fun onSearchItemCLicked() {
        binding.imgSearch.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClicked() {
        popularItemsAdapter.onLongitemclicked = {meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClicked() {
        CategoryAdapter.onItemClicked = { category ->
            val intent = Intent(activity,CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        CategoryAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = CategoryAdapter
        }
    }

    private fun observeCategoriesItemLivedata() {

        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories->
                CategoryAdapter.setCategoryList(categories)
        })

    }

    private fun onPopularItemClicked() {
        popularItemsAdapter.onitemclicked = {
            meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {

            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter

        }
    }

    private fun observePopularItemLivedata() {
        viewModel.observePopularMealLiveData().observe(viewLifecycleOwner
        ) { mealList ->

            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)

        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal

        }
    }
}