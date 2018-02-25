package com.example.uplabdhisingh.bakingtime;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IngredientDetailsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_details);

        ListIngredientsFragment listIngredientsFragment = new ListIngredientsFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.list_ingredients_container,listIngredientsFragment)
                .commit();
    }
}
