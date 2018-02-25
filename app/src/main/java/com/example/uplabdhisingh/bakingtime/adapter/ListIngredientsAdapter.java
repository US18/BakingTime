package com.example.uplabdhisingh.bakingtime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uplabdhisingh.bakingtime.Details.Ingredient;
import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.R;

import java.util.ArrayList;

/**
 * Created by uplabdhisingh on 25/02/18.
 */

public class ListIngredientsAdapter  extends RecyclerView.Adapter<ListIngredientsAdapter.ListIngredientsViewHolder>
{
    private ArrayList<RecipeDetail> recipeDetailsList;
    private Context mContext;
    public ListIngredientsAdapter(Context context)
    {
        mContext=context;
    }

    @Override
    public ListIngredientsAdapter.ListIngredientsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutID = R.layout.ing_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutID,viewGroup,false);
        return new ListIngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListIngredientsAdapter.ListIngredientsViewHolder holder, int position)
    {
        RecipeDetail recipeClickedPosition = recipeDetailsList.get(position);
        holder.ingQuantityTextView.setText(Float.toString(recipeClickedPosition.getIngredients().get(position).getQuantity()));
        holder.ingMeasureTextView.setText(recipeClickedPosition.getIngredients().get(position).getMeasure());
        holder.ingNameTextView.setText(recipeClickedPosition.getIngredients().get(position).getIngredient());
    }

    @Override
    public int getItemCount() {

        if(recipeDetailsList==null)
        {
            return 0;
        }
        return recipeDetailsList.size();
    }

    public class ListIngredientsViewHolder extends RecyclerView.ViewHolder
    {
        TextView ingQuantityTextView, ingMeasureTextView, ingNameTextView;

        public ListIngredientsViewHolder(View itemView)
        {
            super(itemView);
            ingQuantityTextView = (TextView) itemView.findViewById(R.id.tv_ing_quantity);
            ingMeasureTextView = (TextView) itemView.findViewById(R.id.tv_ing_measure);
            ingNameTextView = (TextView) itemView.findViewById(R.id.tv_ing_name);
        }
    }

    public void setListData(ArrayList<RecipeDetail> recipeDetails, Context context)
    {
        recipeDetailsList=recipeDetails;
        mContext=context;
        notifyDataSetChanged();
    }
}
