package gtfo.einfachkochen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by sandra on 30/04/2015.
 */
public class Favourites extends ActionBarActivity implements View.OnClickListener {

    // define Variables for Views
    private Button back;

    private String favouriteJsonString;                        
    private JSONArray favouriteJsonArray;
                                                         
    private ListView favourite_list_view;
    private ArrayList favourite_array_list;
    private ArrayList favourite_name_array_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        // connects the Back-View to the variable
        back = (Button) findViewById(R.id.fav_back);

        // binding an OnClickListener to the Back Button
        back.setOnClickListener(this);

        favourite_list_view = (ListView) findViewById(R.id.favourite_list_view);

        favouriteJsonString = readRecipeJson();


        try {                                                                       
            favouriteJsonArray = new JSONArray(favouriteJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            favouriteJsonArray = null;
        }


        favourite_array_list = new ArrayList();
        favourite_name_array_list = new ArrayList();

        for(int i = 0; i < favouriteJsonArray.length(); i++){
            try {
                favourite_array_list.add(favouriteJsonArray.getJSONObject(i));
                favourite_name_array_list.add(favouriteJsonArray.getJSONObject(i).optString("Name", "NOT FOUND!"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, favourite_name_array_list);
        favourite_list_view.setAdapter(adapter);




        favourite_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {


                Intent to_Favourite_Recipe = new Intent(Favourites.this, Recipe.class);
                String selectedRecipe = favourite_array_list.get(position).toString();  

                to_Favourite_Recipe.putExtra("recipeInfo", selectedRecipe);
                startActivity(to_Favourite_Recipe);

             }
        });

    }


    @Override
    public void onClick(View v) {

        if(v == back){
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourites, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_shoppinglist) {

            Intent to_Shopping_List = new Intent(this, Shoppinglist.class);

            startActivity(to_Shopping_List);
        }

        return super.onOptionsItemSelected(item);
    }






    public String readRecipeJson(){                                          
        String tempRecipeJson;

        try {
            InputStream iS = getResources().openRawResource(R.raw.recipe_list_json);


            int size = iS.available();
            byte[] buffer = new byte[size];

            iS.read(buffer);
            iS.close();

            tempRecipeJson = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            tempRecipeJson = "Oh snap!";
        }

        return tempRecipeJson;

    }


}
