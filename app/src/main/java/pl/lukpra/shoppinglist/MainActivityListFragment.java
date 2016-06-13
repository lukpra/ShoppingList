package pl.lukpra.shoppinglist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
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

        sList = new ArrayList<ShopList>();
        sList.add(new ShopList("title","desc",
                ShopList.Category.BANANA, 1 , 1));
        sList.add(new ShopList("title","desc",
                ShopList.Category.BANANA, 1 , 1));
        sList.add(new ShopList("title","desc",
                ShopList.Category.BANANA, 1 , 1));
        sList.add(new ShopList("title","desc",
                ShopList.Category.BANANA, 1 , 1));
        sList.add(new ShopList("title","desc",
                ShopList.Category.BANANA, 1 , 1));
        sList.add(new ShopList("title","desc",
                ShopList.Category.BANANA, 1 , 1));
        sList.add(new ShopList("title","desc",
                ShopList.Category.FISH, 1 , 1));
        sList.add(new ShopList("title","desc",
                ShopList.Category.FISH, 1 , 1));
        sList.add(new ShopList("title","desc",
                ShopList.Category.FISH, 1 , 1));
        sList.add(new ShopList("title","desc",
                ShopList.Category.FISH, 1 , 1));
        sList.add(new ShopList("title","desc",
                ShopList.Category.FISH, 1 , 1));
        sList.add(new ShopList("title","desc",
                ShopList.Category.FISH, 1 , 1));

        shoppyAdapter = new ShoppyAdapter(getActivity(), sList);

        setListAdapter(shoppyAdapter);

        getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.black));
        getListView().setDividerHeight(1);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        getProductDetailActivity(position);
    }

    public void getProductDetailActivity(int position){
        ShopList sList = (ShopList) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(),ShopListDetailActivity.class);
        intent.putExtra(MainActivity.SLIST_ID_EXTRA, sList.getName());
        intent.putExtra(MainActivity.SLIST_NAME_EXTRA, sList.getName());
        intent.putExtra(MainActivity.SLIST_INFO_EXTRA, sList.getName());
        intent.putExtra(MainActivity.SLIST_CATEGORY_EXTRA, sList.getName());

        startActivity(intent);

    }

}
