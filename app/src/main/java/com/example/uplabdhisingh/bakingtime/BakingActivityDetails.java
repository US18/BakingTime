package com.example.uplabdhisingh.bakingtime;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.Details.Step;
import com.example.uplabdhisingh.bakingtime.adapter.StepsAdapter;

import java.util.ArrayList;
import java.util.List;

public class BakingActivityDetails extends AppCompatActivity implements StepsAdapter.StepsClickListener
{
    private ArrayList<RecipeDetail> recipeDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_details);

        if(savedInstanceState==null)
        {
            Bundle showThisActivityBundle = getIntent().getExtras();

            recipeDetails = new ArrayList<>();
            recipeDetails=showThisActivityBundle.getParcelableArrayList("SELECTED_RECIPE_DETAIL");

            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(showThisActivityBundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_detail_fragment_container,detailFragment)
                    .commit();

        }
    }

    @Override
    public void onStepClicked(List<Step> stepClicked, int clickedIndex)
    {

    }
}
