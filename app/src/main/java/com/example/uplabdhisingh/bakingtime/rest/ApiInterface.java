package com.example.uplabdhisingh.bakingtime.rest;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by uplabdhisingh on 11/02/18.
 */

public interface ApiInterface
{
    @GET("baking.json")
    Call<ArrayList<RecipeDetail>> getRecipeDetail();
}
