package com.example.uplabdhisingh.bakingtime.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.Details.Step;
import com.example.uplabdhisingh.bakingtime.R;
import com.example.uplabdhisingh.bakingtime.StepDetailFragment;
import com.squareup.picasso.Picasso;

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

    public RecipeAdapter(RecipeAdapterOnClickHandler itemClicked)
    {
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

        /*String thumbStepUrl="";
        ArrayList<Step> step = recipeClickedPosition.getSteps();
        for(int i=0;i<step.size();i++)
        {
            thumbStepUrl=recipeClickedPosition.getSteps().get(i).getThumbnailURL();
        }
        String imageUrl = recipeClickedPosition.getImage();*/ //getting the specific image url for that recipe
        /*
        The below method is commented because I want to show m static drawable image on the first screen.
        Also, imageUrl is getting image for the specific recipe and wouldn't fetch any thumbnail url as we are not
        calling the getThumbnailUrl method in imageUrl.
        We are specifically calling the getImage() method, which on call will give the Recipe's Image.
        If the url is not equals null then Picasso will show that image in the imageview.
        Uncomment the below code to check its working.
         */

        /*
        if(imageUrl!="" || thumbStepUrl!="")
        {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(mContext).load(builtUri).into(holder.recipeImageView);
        }
*/
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
