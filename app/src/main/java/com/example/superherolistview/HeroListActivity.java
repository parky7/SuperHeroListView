package com.example.superherolistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HeroListActivity extends AppCompatActivity {

    private ListView listView_Hero;
    private TextView textView_rank;
    private TextView textView_name;
    private TextView textView_description;
    private HeroAdapter heroAdapter;
    private List<Hero> heroesList;

    public static final String TAG = "HeroListActivity";
    public static final String EXTRA_POSITION = "position";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        InputStream questionsInputStream = getResources().openRawResource(R.raw.heroes);
        String jsonString = readTextFile(questionsInputStream);
        // create a gson object
        Gson gson = new Gson();
        // read your json file into an array of questions
        Hero[] heroes =  gson.fromJson(jsonString, Hero[].class);
        // convert your array to a list using the Arrays utility class
        heroesList = Arrays.asList(heroes);
        // verify that it read everything properly
        Log.d(TAG, "onCreate: " + heroesList.toString());
        wireWidgets();
        setListeners();
        heroAdapter = new HeroAdapter(heroesList);
        listView_Hero.setAdapter(heroAdapter);

    }

    private void setListeners() {
        listView_Hero.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Hero heroClicked = heroesList.get(position);
                Intent listViewClicked = new Intent(HeroListActivity.this, HeroDetailActivity.class);
                listViewClicked.putExtra(EXTRA_POSITION, heroClicked);
                startActivity(listViewClicked);
            }
        });
    }

    private void wireWidgets() {
        listView_Hero = findViewById(R.id.listView_HeroListActivity_HeroList);
        textView_description = findViewById(R.id.textView_item_description);
        textView_name = findViewById(R.id.textView_item_name);
        textView_rank = findViewById(R.id.textView_item_rank);


    }

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    private class HeroAdapter extends ArrayAdapter<Hero> {
        private List<Hero> heroesList;
        public HeroAdapter(List<Hero> heroesList) {
            //since we're in the HeroListActivity class, we already have the context
            // we're hardcoding in a particular layout, so don't need to put it in the
            // constructer either
            // we'll send a place holder resource to the superclass of -1
            super(HeroListActivity.this, -1, heroesList);
            this.heroesList = heroesList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            // 1. inflate layout
            LayoutInflater inflater = getLayoutInflater();
            if(convertView == null){
                convertView = inflater.inflate(R.layout.item_hero, parent, false);
            }
            // 2. wire widgets & link the hero to those widgets
            textView_name = convertView.findViewById(R.id.textView_item_name);
            textView_rank = convertView.findViewById(R.id.textView_item_rank);
            textView_description = convertView.findViewById(R.id.textView_item_description);

            // set values for each widget. use the position parameter variable
            // to get the hero that you need out of the list
            // and set the values for the widgets
            textView_name.setText(heroesList.get(position).getName());
            textView_rank.setText(String.valueOf(heroesList.get(position).getRanking()));
            textView_description.setText(heroesList.get(position).getDescription());

            //3. return inflated view
            return convertView;

        }
    }
    //  OPTIONS MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_heroeslist_sorting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_heroeslist_sort_by_name:
                sortByName();
                return true;
            case R.id.action_heroeslist_sort_by_rank:
                sortByRank();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortByRank() {
        // extract the list from the adapter
        // heroAdapter.heroesList instead of thingList
        Collections.sort(heroesList, new Comparator<Hero>() {
            @Override
            public int compare(Hero hero, Hero h1) {
                // negative number if thing comes before t1
                // 0 if thing and t1 are the same
                // postive number if thing comes after t1
                return hero.getRanking() - h1.getRanking();
            }
        });
        // the data in the adapter has changed, but it isn't aware
        // call the method notifyDataSetChanged on the adapter.
        Toast.makeText(this, "Sort by rank clicked", Toast.LENGTH_SHORT).show();
    }

    private void sortByName() {
        // extract the list from the adapter
        // heroAdapter.heroesList instead of thingList
        Collections.sort(heroesList, new Comparator<Hero>() {
            @Override
            public int compare(Hero hero, Hero h1) {
                return hero.getName().toLowerCase()
                        .compareTo(h1.getName().toLowerCase());
            }
        });
        // the data in the adapter has changed, but it isn't aware
        // call the method notifyDataSetChanged on the adapter.
        Toast.makeText(this, "Sort by name clicked", Toast.LENGTH_SHORT).show();

    }

}