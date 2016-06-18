package pl.lukpra.shoppinglist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {

    private ArrayList<ShopList> sList = new ArrayList<ShopList>();
    private ShoppyAdapter shoppyAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        ShopListDbAdapter shopListDbAdapter = new ShopListDbAdapter(getActivity().getBaseContext());
        shopListDbAdapter.open();

        sList = shopListDbAdapter.getAllShopListElements();

        shopListDbAdapter.close();

        shoppyAdapter = new ShoppyAdapter(getActivity(), sList);

        setListAdapter(shoppyAdapter);

        getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.black));
        getListView().setDividerHeight(1);

        registerForContextMenu(getListView());

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Log.d("Click type:", "short");
        getProductDetailActivity(MainActivity.FragmentToLaunch.VIEW, position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);
    }

    @Override
    public  boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo infos = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition = infos.position;
        switch(item.getItemId()){
            case R.id.edit:
                getProductDetailActivity(MainActivity.FragmentToLaunch.EDIT,rowPosition);
                Log.d("Edit clicked", "Exciting!");
                return true;
        }

        return super.onContextItemSelected(item);
    }

    public void getProductDetailActivity(MainActivity.FragmentToLaunch ftl, int position){
        ShopList sList = (ShopList) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(),ShopListDetailActivity.class);
        intent.putExtra(MainActivity.SLIST_ID_EXTRA, sList.getListId());
        intent.putExtra(MainActivity.SLIST_NAME_EXTRA, sList.getName());
        intent.putExtra(MainActivity.SLIST_INFO_EXTRA, sList.getInfo());
        intent.putExtra(MainActivity.SLIST_CATEGORY_EXTRA, sList.getCategory());

        switch(ftl){
            case VIEW:
                Log.d("Activity: ", "view");
                intent.putExtra(MainActivity.SLIST_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                Log.d("Activity: ", "edit");
                intent.putExtra(MainActivity.SLIST_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.EDIT);
                break;
        }

        startActivity(intent);

    }

}
