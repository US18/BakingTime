package com.example.uplabdhisingh.bakingtime.Details;

/**
 * Created by uplabdhisingh on 11/02/18.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeDetail implements Parcelable
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private ArrayList<Ingredient> ingredients;
    @SerializedName("steps")
    @Expose
    private ArrayList<Step> steps;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     * No args constructor for use in serialization
     *
     */
    public RecipeDetail() {}

    /**
     *
     * @param ingredients
     * @param id
     * @param servings
     * @param name
     * @param image
     * @param steps
     */
    public RecipeDetail(Integer id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, Integer servings, String image) {
        super();
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

   private RecipeDetail(Parcel parcel)
    {
        id=parcel.readInt();
        name=parcel.readString();
        ingredients=new ArrayList<Ingredient>();
        ingredients=parcel.readArrayList(Ingredient.class.getClassLoader());
        steps=new ArrayList<Step>();
        steps=parcel.readArrayList(Step.class.getClassLoader());
        servings=parcel.readInt();
        image=parcel.readString();
    }

    public static final Parcelable.Creator<RecipeDetail> CREATOR = new Parcelable.Creator<RecipeDetail>()
    {
        @Override
        public RecipeDetail createFromParcel(Parcel parcel)
        {
            return new RecipeDetail(parcel);
        }

        @Override
        public RecipeDetail[] newArray(int i)
        {
            return new RecipeDetail[0];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }

}
