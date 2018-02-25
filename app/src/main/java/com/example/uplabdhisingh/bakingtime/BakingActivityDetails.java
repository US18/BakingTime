package com.example.uplabdhisingh.bakingtime;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BakingActivityDetails extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_details);

        IngredientFragment ingredientFragment = new IngredientFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.ing_container,ingredientFragment)
                .commit();
    }
}
