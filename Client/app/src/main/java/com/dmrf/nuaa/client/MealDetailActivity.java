package com.dmrf.nuaa.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MealDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mealDetailImg;

    TextView mealTitle;
    ImageButton deleteBtn;
    ImageButton timeBtn;
    ImageButton editBtn;

    EditText mealDesc;

    ImageButton facebookBtn;
    ImageButton instagramBtn;

    Switch publicSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        Intent intent = getIntent();
        MealHelper meal = (MealHelper)intent.getSerializableExtra("meal");

        initTheUi();
        showData(meal);

    }

    public void initTheUi(){

        mealDetailImg = findViewById(R.id.mealdetailimage);
        mealTitle = findViewById(R.id.detailMealtitle);
        mealDesc = findViewById(R.id.mealDetailDes);

        deleteBtn = findViewById(R.id.deleteMealBtn);
        timeBtn = findViewById(R.id.editTimeBtn);
        editBtn = findViewById(R.id.editNoteBtn);
        facebookBtn = findViewById(R.id.facebookBtn);
        instagramBtn = findViewById(R.id.instagramBtn);

        publicSwitch = findViewById(R.id.publicStates);

        deleteBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);
        facebookBtn.setOnClickListener(this);
        instagramBtn.setOnClickListener(this);

    }


    public void showData(MealHelper meal){

        mealDetailImg.setBackgroundResource(R.drawable.food2);
        mealTitle.setText(meal.getTitle());
        mealDesc.setText(meal.getDescription());


        deleteBtn.setBackgroundResource(R.drawable.delete);
        timeBtn.setBackgroundResource(R.drawable.time);
        editBtn.setBackgroundResource(R.drawable.edit);
        facebookBtn.setBackgroundResource(R.drawable.facebook);
        instagramBtn.setBackgroundResource(R.drawable.instagram);

    }




    @Override
    public void onClick(View v){

        int id = v.getId();
        if(id == R.id.deleteMealBtn){

            showMessage("delete meal");

        }else if (id == R.id.editTimeBtn){

            showMessage("edit time");

        }else if (id == R.id.editNoteBtn){

            mealDesc.setEnabled(true);
            showMessage("Now you can edit the Meal description");

        }else if (id == R.id.facebookBtn){

            showMessage("share to facebook");

        }else if (id == R.id.instagramBtn){

            showMessage("share to instagram");

        }

    }



    public void showMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }




}