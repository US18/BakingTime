package com.example.uplabdhisingh.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.adapter.RecipeAdapter;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class BakingActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler
{
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    //Parcelable parceRecipeState;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking);

        BaseFragment baseFragment = new BaseFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Bundle b = new Bundle();
       // b.putParcelable("RecipeState",parceRecipeState);
       // baseFragment.setArguments(b);

        if(savedInstanceState==null || !savedInstanceState.containsKey("TEST") )
        {
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_container,baseFragment)
                    .commit();
        }

    }


    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    /*
    Below Method is overrided and says when the specific recipe from first activity is clicked, then
    it will intent to baking activity detail class.
     */

    @Override
    public void onClickItem(RecipeDetail recipeDataObject)
    {
        Context thisClass = this;
        Class destinationClass = BakingActivityDetails.class;
        Intent intentToPassOurDataFromThisClass=new Intent(thisClass,destinationClass);
        intentToPassOurDataFromThisClass.putExtra("RecipeDetail",recipeDataObject);
        if(intentToPassOurDataFromThisClass.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intentToPassOurDataFromThisClass);
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("TEST","TEST");
    }
}
