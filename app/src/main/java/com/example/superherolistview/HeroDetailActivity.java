package com.example.superherolistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class HeroDetailActivity extends AppCompatActivity {
    private TextView description;
    private TextView name;
    private TextView rank;
    private ImageView picture;
    private TextView superpower;
    private Hero hero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        Intent lastIntent = getIntent();
        hero = lastIntent.getParcelableExtra(HeroListActivity.EXTRA_POSITION);
        wireWidgets();
        description.setText(hero.getDescription());
        name.setText(hero.getName());
        rank.setText(String.valueOf(hero.getRanking()));
        superpower.setText(hero.getSuperPower());
        int resourceImage = getResources().getIdentifier(hero.getImage(), "drawable", getPackageName());
        picture.setImageDrawable(getResources().getDrawable(resourceImage));

    }

    private void wireWidgets() {
        description = findViewById(R.id.textView_heroDetail_descriptionText);
        name = findViewById(R.id.textView_heroDetail_name);
        rank = findViewById(R.id.textView_heroDetail_rank);
        picture = findViewById(R.id.imageView_heroDetail_picture);
        superpower = findViewById(R.id.textView_heroDetail_superpower);

    }
}
