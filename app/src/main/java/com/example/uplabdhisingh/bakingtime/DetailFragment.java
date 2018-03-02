package com.example.uplabdhisingh.bakingtime;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uplabdhisingh.bakingtime.Details.Ingredient;
import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.adapter.StepsAdapter;


import java.util.ArrayList;

/**
 * Created by uplabdhisingh on 27/02/18.
 */

public class DetailFragment extends Fragment
{
    TextView ingredientsTextView;
    RecyclerView stepsRecyclerView;
    RecipeDetail recipeDetails;

    private String TAG = DetailFragment.class.getSimpleName();
    public DetailFragment()
    {}

    @TargetApi(Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail,container,false);

        //recipeDetails = new ArrayList<>();
        if(savedInstanceState!=null)
        {
            recipeDetails = savedInstanceState.getParcelable("RecipeDetail");
            Log.e(TAG, "onCreateView: got instance");
         } else
             {
            recipeDetails = getArguments().getParcelable("RecipeDetail");
            Log.d(TAG, "onCreateView: got args");
             }

       // Log.d(TAG,"RecipeDetails:"+recipeDetails.getName());

       ArrayList<Ingredient> ingredientArrayList = recipeDetails.getIngredients();

       // Log.d(TAG,"Ingredient Arraylist Size :  "+ingredientArrayList.size());

        ingredientsTextView = (TextView) rootView.findViewById(R.id.tv_ingredients);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_steps);

       ingredientArrayList.forEach( (a) ->
                {
                 ingredientsTextView.append(a.getIngredient()+" : ");
                 ingredientsTextView.append(Float.valueOf(a.getQuantity()).toString());
                 ingredientsTextView.append(a.getMeasure()+"\n");
                });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        stepsRecyclerView.setLayoutManager(linearLayoutManager);

        StepsAdapter recipeStepAdapter=new StepsAdapter((BakingActivityDetails)getActivity());
        stepsRecyclerView.setAdapter(recipeStepAdapter);
       recipeStepAdapter.setStepsData(recipeDetails,getContext());
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelable("RecipeDetail",recipeDetails);
    }
}
