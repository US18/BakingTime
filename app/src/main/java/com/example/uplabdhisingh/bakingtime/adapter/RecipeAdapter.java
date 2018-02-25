package com.example.uplabdhisingh.bakingtime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uplabdhisingh on 11/02/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder>
{
    private ArrayList<RecipeDetail> recipeDetailsList;
    final private RecipeAdapterOnClickHandler mClickHandler;
    private Context mContext;

    public RecipeAdapter(Context context,RecipeAdapterOnClickHandler itemClicked)
    {
        mContext=context;
        mClickHandler=itemClicked;
    }

    public interface RecipeAdapterOnClickHandler
    {
        void onClickItem(RecipeDetail recipeData);
    }
    @Override
    public RecipeAdapter.RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutID = R.layout.recipe_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutID,viewGroup,false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position)
    {
        RecipeDetail recipeClickedPosition = recipeDetailsList.get(position);
        holder.recipeTextView.setText(recipeClickedPosition.getName());
    }

    @Override
    public int getItemCount()
    {
        if(recipeDetailsList==null)
        {
            return 0;
        }
        return recipeDetailsList.size();
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView recipeImageView;
        TextView recipeTextView;

        public RecipeAdapterViewHolder(View itemView)
        {
            super(itemView);
            recipeImageView = (ImageView) itemView.findViewById(R.id.iv_recipe_image);
            recipeTextView = (TextView) itemView.findViewById(R.id.tv_recipe_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int position = getAdapterPosition();
                    RecipeDetail list = recipeDetailsList.get(position);
                    mClickHandler.onClickItem(list);
                }
            }).start();
        }
    }
    public void setRecipeData(ArrayList<RecipeDetail> recipeDetails, Context context)
    {
        recipeDetailsList=recipeDetails;
        mContext=context;
        notifyDataSetChanged();
    }
}
