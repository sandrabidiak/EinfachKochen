package gtfo.einfachkochen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sandra on 30/04/2015.
 */
public class Recipe extends ActionBarActivity implements View.OnClickListener {

    private String recipe, recipeName, recipeDetails;
    private TextView recipeNameView, recipeDetailsView;
    private Button back;
    private JSONObject recipeJsonObject;
    private JSONArray recipeDetailsJsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        // connects the Back-View to the variable
        back = (Button) findViewById(R.id.recipe_back);

        // binding an OnClickListener to the Back Button
        back.setOnClickListener(this);

        recipeNameView = (TextView) findViewById(R.id.recipe_name);
        recipeDetailsView = (TextView) findViewById(R.id.recipe_details);

        recipe = getIntent().getExtras().getString("recipeInfo");

        try {
            recipeJsonObject = new JSONObject(recipe);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recipeDetailsJsonArray = recipeJsonObject.optJSONArray("Ingredients");

        recipeName = recipeJsonObject.optString("Name");
        recipeNameView.setText(recipeName);

        recipeDetails = "Zutaten: \n";

        for(int i = 0; i < recipeDetailsJsonArray.length(); i++){
            try {
                recipeDetails = recipeDetails +"\n"+ (i+1)+". "+ recipeDetailsJsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        recipeDetailsView.setText(recipeDetails);


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
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
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

}
