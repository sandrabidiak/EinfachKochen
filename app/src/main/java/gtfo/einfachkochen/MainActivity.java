package gtfo.einfachkochen;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;




public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    // define Variables for Views
    private Button suchen;
  //  private String recipeJson;
    private EditText inputIngredient;           

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connects the Suchen-View to the variable
        suchen = (Button) findViewById(R.id.suchen);

        inputIngredient = (EditText) findViewById(R.id.inputIngredient);        

        // bindes an OnClickListener to the suchen Button
        suchen.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        // start RecipeList activity on click
        if(v == suchen) {

                              
            //creates intent
            Intent to_Recipe_List = new Intent(this, RecipeList.class);

            to_Recipe_List.putExtra("ingredient", inputIngredient.getText().toString());       

            startActivity(to_Recipe_List);

        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favourites) {

            Intent to_Favourites_List = new Intent(this, Favourites.class);

            startActivity(to_Favourites_List);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_shoppinglist) {

            Intent to_Shopping_List = new Intent(this, Shoppinglist.class);

            startActivity(to_Shopping_List);
        }

        return super.onOptionsItemSelected(item);
    }

}