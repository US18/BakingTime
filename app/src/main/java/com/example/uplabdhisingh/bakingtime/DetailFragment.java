package com.example.uplabdhisingh.bakingtime;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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
    public static SharedPreferences sharedPref;
    public static final String myPreference = "myPref";
    public static final String ingKey = "ingredients_key";
    public static final String recipeKey = "recipe_key";
    public static int sizeOfList;
    TextView ingredientsTextView;
    RecyclerView stepsRecyclerView;
    RecipeDetail recipeDetails;

    String name;
    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    LinearLayoutManager linearLayoutManager;
    private Parcelable mListState = null;


    private String TAG = DetailFragment.class.getSimpleName();
    public DetailFragment()
    {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail,container,false);

             if (getArguments()!=null)
             {
             recipeDetails = getArguments().getParcelable("RecipeDetail");
             Log.e(TAG, "onCreateView: got instance");
           }


       // Log.d(TAG,"RecipeDetails:"+recipeDetails.getName());
       ArrayList<Ingredient> ingredientArrayList = recipeDetails.getIngredients();
       // Log.d(TAG,"Ingredient Arraylist Size :  "+ingredientArrayList.size());
        ingredientsTextView = (TextView) rootView.findViewById(R.id.tv_ingredients);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_steps);

       for(Ingredient a : ingredientArrayList)
                {
                 ingredientsTextView.append("\u2022 "+a.getIngredient()+" : ");
                 ingredientsTextView.append(Float.valueOf(a.getQuantity()).toString());
                 ingredientsTextView.append(a.getMeasure()+"\n");

                }

        sharedPref = getActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(recipeKey,recipeDetails.getName());

        for(int i =0;i<ingredientArrayList.size();i++)
        {
            editor.putString(i+"step",ingredientArrayList.get(i).getIngredient());
            Log.d(TAG,"Data Inserted into Shared Pref");
            editor.putString(ingKey,recipeDetails.getIngredients().get(i).getIngredient());
        }
        sizeOfList=ingredientArrayList.size();
        editor.commit();

        linearLayoutManager=new LinearLayoutManager(getContext());
        stepsRecyclerView.setLayoutManager(linearLayoutManager);

        StepsAdapter recipeStepAdapter=new StepsAdapter((BakingActivityDetails)getActivity());
        stepsRecyclerView.setAdapter(recipeStepAdapter);
       recipeStepAdapter.setStepsData(recipeDetails,getContext());

        setRetainInstance(true);
        return rootView;
    }
    /*@Override
    public void onSaveInstanceState(@Nullable Bundle outState)
    {
        assert outState != null;

        outState.putParcelable("RecipeDetail",recipeDetails);
        super.onSaveInstanceState(outState);
    }*/
   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null)
        {
            recipeDetails = savedInstanceState.getParcelable("RecipeDetail");
        }

//        Log.d(TAG, "onCreateView: got args");
    }*/

    /*
    @Override
    public void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        mListState = stepsRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);
    }
*/
    /*
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mBundleRecyclerViewState != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                    stepsRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);

                }
            }, 50);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        stepsRecyclerView.setLayoutManager(linearLayoutManager);
    }
 */





}
