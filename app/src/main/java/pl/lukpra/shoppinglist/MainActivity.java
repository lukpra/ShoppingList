package pl.lukpra.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    public static final String SLIST_ID_EXTRA = "pl.lukpra.shoppinglist.ShopList.Identifier";
    public static final String SLIST_NAME_EXTRA = "pl.lukpra.shoppinglist.ShopList.Name";
    public static final String SLIST_INFO_EXTRA = "pl.lukpra.shoppinglist.ShopList.Info";
    public static final String SLIST_CATEGORY_EXTRA ="pl.lukpra.shoppinglist.ShopList.Category";
    public static final String SLIST_FRAGMENT_TO_LOAD_EXTRA = "pl.lukpra.shoppinglist.Fragment_to_Load";

    public enum FragmentToLaunch{ VIEW, EDIT, ADD}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        // as you specify pumpkin parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_add_item){
            Intent intent = new Intent(this, ShopListDetailActivity.class);
            intent.putExtra(MainActivity.SLIST_FRAGMENT_TO_LOAD_EXTRA, FragmentToLaunch.ADD);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
