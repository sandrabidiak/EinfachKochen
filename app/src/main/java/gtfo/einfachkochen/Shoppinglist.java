package gtfo.einfachkochen;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by sandra on 01/05/2015.
 */
public class Shoppinglist extends ActionBarActivity implements View.OnClickListener {

    private Button back, sListSpeichern, sListDelete;
    private EditText sListEditText;

    private ListView zutatListView;
    private ArrayAdapter zutatArrayAdapter;
    private ArrayList zutatNameList = new ArrayList();

    private String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppinglist);

        if(shopListLoader() != null)                   
            zutatNameList = shopListLoader();

        sListEditText = (EditText) findViewById(R.id.sl_edittext);

        sListSpeichern = (Button) findViewById(R.id.zutat_speichern);
        sListSpeichern.setOnClickListener(this);

        sListDelete = (Button) findViewById(R.id.shoppinglist_delete);
        sListDelete.setOnClickListener(this);


        // Access the ListView
        zutatListView = (ListView) findViewById(R.id.zutat_listview);

        // Create an ArrayAdapter for the ListView
        zutatArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                zutatNameList);

        // Set the ListView to use the ArrayAdapter
        zutatListView.setAdapter(zutatArrayAdapter);

        zutatArrayAdapter.notifyDataSetChanged();   



        // connects the Back-View to the variable
        back = (Button) findViewById(R.id.shoppinglist_back);

        // binding an OnClickListener to the Back Button
        back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v == sListSpeichern ){

            // Also add the value to the list shown in the ListView
            zutatNameList.add(sListEditText.getText().toString());
            zutatArrayAdapter.notifyDataSetChanged();

            shopListSaver(zutatNameList); 

            sListEditText.setText("");
        }


        if(v == sListDelete ){

            zutatNameList.clear();
            zutatArrayAdapter.notifyDataSetChanged();
            shopListSaver(zutatNameList);           

        }


        if (v == back) {
            finish();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop_list, menu);
        return true;
    }


    public void shopListSaver (ArrayList zutatNameList){
        try {
            FileOutputStream fileOut = openFileOutput("shopListArray.ser", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(zutatNameList);

            out.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public ArrayList shopListLoader (){
        ArrayList sl;

        try {
            FileInputStream fileIn = openFileInput("shopListArray.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            sl = (ArrayList) in.readObject();

            in.close();
            fileIn.close();

            return sl;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
