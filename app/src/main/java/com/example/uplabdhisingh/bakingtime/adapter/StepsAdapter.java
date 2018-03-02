package com.example.uplabdhisingh.bakingtime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uplabdhisingh.bakingtime.Details.RecipeDetail;
import com.example.uplabdhisingh.bakingtime.Details.Step;
import com.example.uplabdhisingh.bakingtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uplabdhisingh on 27/02/18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder>
{
    ArrayList<Step> stepsList;
    final private StepsClickListener stepsClickListener;


    public interface StepsClickListener
    {
        void onStepClicked(ArrayList<Step> stepClicked,int clickedIndex);
    }

    public StepsAdapter(StepsClickListener listener)
    {
        stepsClickListener=listener;
    }

    @Override
    public StepsAdapter.StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        int layoutId = R.layout.steps_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId,parent,false);
        StepsAdapterViewHolder viewHolder = new StepsAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepsAdapter.StepsAdapterViewHolder holder, int position)
    {
        holder.stepsTextView.setText(stepsList.get(position).getId()
        +". "+stepsList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount()
    {
        if(stepsList==null)
        {
            return 0;
        }
        return stepsList.size();
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        TextView stepsTextView;

        public StepsAdapterViewHolder(View itemView) {
            super(itemView);
            stepsTextView = (TextView) itemView.findViewById(R.id.tv_steps_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            stepsClickListener.onStepClicked(stepsList,clickedPosition);
        }
    }

    public void setStepsData(RecipeDetail recipeDetails,Context context){
        stepsList=recipeDetails.getSteps();
        notifyDataSetChanged();
    }
}
