package com.example.uplabdhisingh.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.adapter.RecipeAdapter;
import com.example.uplabdhisingh.bakingtime.rest.ApiClient;
import com.example.uplabdhisingh.bakingtime.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uplabdhisingh on 11/02/18.
 */

public class BaseFragment extends Fragment
{
    public BaseFragment(){}
    public String TAG = BaseFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment,container,false);

        RecyclerView recipeRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipe);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recipeRecyclerView.setHasFixedSize(true);
        recipeRecyclerView.setLayoutManager(layoutManager);
        final RecipeAdapter recipeAdapter = new RecipeAdapter((BakingActivity)getActivity());
        recipeRecyclerView.setAdapter(recipeAdapter);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<RecipeDetail>> call = apiService.getRecipeDetail();
        call.enqueue(new Callback<ArrayList<RecipeDetail>>()
        {
            @Override
            public void onResponse(Call<ArrayList<RecipeDetail>> call, Response<ArrayList<RecipeDetail>> response)
            {
                ArrayList<RecipeDetail> recipes = response.body();
                recipeAdapter.setRecipeData(recipes,getContext());
                recipeAdapter.notifyDataSetChanged();
                Log.d(TAG,recipes.toString());
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeDetail>> call, Throwable t) {

                Log.e(TAG,t.toString());
            }
        });
        return rootView;
    }

}
