package com.example.uplabdhisingh.bakingtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uplabdhisingh.bakingtime.Details.Ingredient;
import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.adapter.ListIngredientsAdapter;
import com.example.uplabdhisingh.bakingtime.rest.ApiClient;
import com.example.uplabdhisingh.bakingtime.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uplabdhisingh on 25/02/18.
 */

public class ListIngredientsFragment extends Fragment
{
    public ListIngredientsFragment()
    {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_list_ing,container,false);
        RecyclerView ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list_ing);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        final ListIngredientsAdapter listIngredientsAdapter = new ListIngredientsAdapter(getContext());
        ingredientsRecyclerView.setAdapter(listIngredientsAdapter);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<RecipeDetail>> call = apiService.getRecipeDetail();
        call.enqueue(new Callback<ArrayList<RecipeDetail>>()
        {
            @Override
            public void onResponse(Call<ArrayList<RecipeDetail>> call, Response<ArrayList<RecipeDetail>> response)
            {
                ArrayList<RecipeDetail> recipes = response.body();
                listIngredientsAdapter.setListData(recipes,getContext());
                listIngredientsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeDetail>> call, Throwable t) {

            }
        });


        return rootView;
    }
}
