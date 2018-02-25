package com.example.uplabdhisingh.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by uplabdhisingh on 13/02/18.
 */

public class IngredientFragment extends Fragment
{

    public IngredientFragment()
    {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
     View rootView = inflater.inflate(R.layout.fragment_ing,container,false);
        Button buttonIngredients = (Button) rootView.findViewById(R.id.btn_ing);

        buttonIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context thisFragment = getContext();
                Class destinationFragment = IngredientDetailsActivity.class;
                Intent intentToPassOurData = new Intent(thisFragment,destinationFragment);
                startActivity(intentToPassOurData);
            }
        });
        return rootView;
    }
}
