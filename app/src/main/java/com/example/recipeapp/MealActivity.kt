package com.example.recipeapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.ActivityMealBinding

class MealActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMealBinding
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var YoutubeLink : String
    private lateinit var mealMVVM : MealViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealRoomDataBase = MealRoomDataBase.getInstances(this)
        val viewModelFactory = MealViewModelFactory(mealRoomDataBase)
        mealMVVM = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

       // mealMVVM = ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealInformationFromIntent()

        setInformationinViews()

        loadingCase()
        mealMVVM.getMealDetail(mealId)
        observeMealDetailsLiveData()

        onYoutubeImageClick()

        onFavouriteClick()
    }

    private fun onFavouriteClick() {
        binding.btnAddToFavourites.setOnClickListener{
            mealToSave?.let {
                mealMVVM.insertData(it)
                Toast.makeText(this,"We have saved the meal",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imageYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(YoutubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave:Meal?=null
    private fun observeMealDetailsLiveData() {
        mealMVVM.observeMealDetaillivedata().observe(this,object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value
                mealToSave = meal
                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal!!.strArea}"
                binding.tvInstructionStep.text = meal.strInstructions

                YoutubeLink = meal.strYoutube.toString()
            }
        })
    }

    private fun setInformationinViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.imageYoutube.visibility = View.INVISIBLE
        binding.btnAddToFavourites.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.imageYoutube.visibility = View.VISIBLE
        binding.btnAddToFavourites.visibility = View.VISIBLE
    }
}