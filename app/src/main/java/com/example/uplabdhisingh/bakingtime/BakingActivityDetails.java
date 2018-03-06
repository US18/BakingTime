package com.example.uplabdhisingh.bakingtime;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.Details.Step;
import com.example.uplabdhisingh.bakingtime.adapter.StepsAdapter;

import java.util.ArrayList;
import java.util.List;

public class BakingActivityDetails extends AppCompatActivity
        implements StepsAdapter.StepsClickListener
{   RecipeDetail recipeDetails;
    private boolean twoPane;
    int count = 0;
    Bundle b;
    String TAG = BakingActivityDetails.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_details);

        if(savedInstanceState==null)
        {
            Intent intent = getIntent();
            recipeDetails = intent.getParcelableExtra("RecipeDetail");
            DetailFragment detailFragment = new DetailFragment();
            b = new Bundle();
            b.putParcelable("RecipeDetail",recipeDetails);
            FragmentManager fragmentManager = getSupportFragmentManager();
            detailFragment.setArguments(b);

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_fragment_container,detailFragment,"recipeDetailFragment")
                    .commit();

            if(findViewById(R.id.linear_recipe_landscape)!=null)
            {
                twoPane=true;
                if(savedInstanceState==null)
                {
                    StepDetailFragment stepDetailFragment = new StepDetailFragment();
                    stepDetailFragment.setArguments(b);
                    fragmentManager.beginTransaction()
                            .replace(R.id.recipe_detail_fragment_container2,stepDetailFragment)
                            .commit();
                }
            }
        } else {
            DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentByTag("recipeDetailFragment");
        }
    }
    @Override
    public void onStepClicked(ArrayList<Step> stepClicked, int clickedIndex)
    {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList("STEPS_SELECTED",stepClicked);
        stepBundle.putInt("INDEX_SELECTED",clickedIndex);
        stepDetailFragment.setArguments(stepBundle);

        if(twoPane)
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_fragment_container2,stepDetailFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_fragment_container,stepDetailFragment)
                    .commit();
        }
        count=0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed()
    {

        if(count==0)
        {
            DetailFragment detailFragment = new DetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            detailFragment.setArguments(b);

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_fragment_container,detailFragment)
                    .commit();
            count++;
            Log.d(TAG,"count = " +count);
        }
        else
            {
            super.onBackPressed();
        }
    }
}
