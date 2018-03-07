package com.example.uplabdhisingh.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
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
    private static Bundle mBundleRecyclerViewState;
    RecyclerView recipeRecyclerView;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    LinearLayoutManager layoutManager;
    public BaseFragment(){}
    public String TAG = BaseFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment,container,false);


        recipeRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipe);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
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
       // recipeRecyclerView.scrollToPosition(savedInstanceState.getInt("RecyclerPosition"));

        setRetainInstance(true);
        return rootView;
    }

    /*
    @Override
    public void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        mListState = recipeRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (mBundleRecyclerViewState != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                    recipeRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);

                }
            }, 50);
        }
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        recipeRecyclerView.setLayoutManager(layoutManager);
    }
 */

  /*   @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putInt("RecyclerPosition",recipeRecyclerView.getChildAdapterPosition());
        super.onSaveInstanceState(outState);

/*mListState = layoutManager.onSaveInstanceState();
        outState.putParcelable("RECYCLER_SAVE_INSTANCE",mListState);
    } */
/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }*/
}
