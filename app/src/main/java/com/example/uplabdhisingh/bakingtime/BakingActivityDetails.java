package com.example.uplabdhisingh.bakingtime;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.Details.Step;
import com.example.uplabdhisingh.bakingtime.adapter.StepsAdapter;

import java.util.ArrayList;

public class BakingActivityDetails extends AppCompatActivity
        implements StepsAdapter.StepsClickListener
{
     RecipeDetail recipeDetails;
    DetailFragment detailFragment;
    private boolean twoPane;
    int count = 0;
    Bundle b;
    String TAG = BakingActivityDetails.class.getSimpleName();

    Fragment fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_details);
           Intent intent = getIntent();
           recipeDetails = intent.getParcelableExtra("RecipeDetail");
           detailFragment = new DetailFragment();
           b = new Bundle();
           b.putParcelable("RecipeDetail",recipeDetails);
           FragmentManager fragmentManager = getSupportFragmentManager();
           detailFragment.setArguments(b);
        if(savedInstanceState==null || !savedInstanceState.containsKey("TEST") )
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_fragment_container,detailFragment)
                    .commit();
        }
           if(findViewById(R.id.linear_recipe_landscape)!=null)
           {
               twoPane=true;
               if(savedInstanceState==null || !savedInstanceState.containsKey("TEST") )
               {
                   StepDetailFragment stepDetailFragment = new StepDetailFragment();
                   stepDetailFragment.setArguments(b);
                   fragmentManager.beginTransaction()
                           .replace(R.id.recipe_detail_fragment_container2,stepDetailFragment)
                           .commit();
               }
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
        } else
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_fragment_container,stepDetailFragment)
                    .commit();
        }
        count=0;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
            int id = item.getItemId();
            if(id==R.id.action_bar_home)
            {
                Intent intent = new Intent(this, BakingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else if(id==android.R.id.home)
            {
                onBackPressed();
                return true;
            }
        return super.onOptionsItemSelected(item);
    }



  /*  @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }*/


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
      //  getSupportFragmentManager().putFragment(outState,"detailFragment",fragmentManager);
        outState.putString("TEST","TEST");
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
