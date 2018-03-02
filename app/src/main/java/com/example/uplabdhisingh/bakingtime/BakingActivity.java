package com.example.uplabdhisingh.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.adapter.RecipeAdapter;

import java.util.ArrayList;

public class BakingActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking);

        BaseFragment baseFragment = new BaseFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container,baseFragment)
                .commit();
    }

    /*
    Below Method is overrided and says when the specific recipe from first activity is clicked, then
    it will intent to baking activity detail class.
     */

    @Override
    public void onClickItem(RecipeDetail recipeDataObject)
    {
      //  Bundle toShowSecondActivityBundle = new Bundle();
       // ArrayList<RecipeDetail> recipeDetailArrayList = new ArrayList<>();
       // recipeDetailArrayList.add(recipeDataObject);
       // toShowSecondActivityBundle.putParcelableArrayList("SELECTED_RECIPE_DETAIL",recipeDetailArrayList);

        Context thisClass = this;
        Class destinationClass = BakingActivityDetails.class;
        Intent intentToPassOurDataFromThisClass=new Intent(thisClass,destinationClass);
        intentToPassOurDataFromThisClass.putExtra("RecipeDetail",recipeDataObject);
       // intentToPassOurDataFromThisClass.putExtras(toShowSecondActivityBundle);
        if(intentToPassOurDataFromThisClass.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intentToPassOurDataFromThisClass);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
