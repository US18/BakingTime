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

public class BakingActivityDetails extends AppCompatActivity
        implements StepsAdapter.StepsClickListener
{
    RecipeDetail recipeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_details);

            Intent intent = getIntent();
            recipeDetails = intent.getParcelableExtra("RecipeDetail");

            DetailFragment detailFragment = new DetailFragment();

            Bundle b = new Bundle();
            b.putParcelable("RecipeDetail",recipeDetails);

            FragmentManager fragmentManager = getSupportFragmentManager();
            detailFragment.setArguments(b);

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_fragment_container,detailFragment)
                    .commit();

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

        fragmentManager.beginTransaction()
                .replace(R.id.recipe_detail_fragment_container,stepDetailFragment)
                .commit();

    }

}
