package pl.lukpra.shoppinglist;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShopListDetailActivity extends AppCompatActivity {

    public static final String NEW_SHOPLIST_EXTRA = "New Shop List Element";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list_detail);
        createAndAddFragment();
    }

    private void createAndAddFragment(){

        Intent intent = getIntent();
        MainActivity.FragmentToLaunch fragmentToLaunch = (MainActivity.FragmentToLaunch) intent.getSerializableExtra(MainActivity.SLIST_FRAGMENT_TO_LOAD_EXTRA);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch(fragmentToLaunch){
            case EDIT:
                ShopListEditFragment shopListEditFragment = new ShopListEditFragment();
                setTitle(R.string.edit_menu);
                fragmentTransaction.add(R.id.product_container, shopListEditFragment, "SHOPLIST_EDIT_FRAGMENT");
                break;
            case VIEW:
                ShopListDetailFragment shopListDetailFragment = new ShopListDetailFragment();
                fragmentTransaction.add(R.id.product_container, shopListDetailFragment, "SHOPLIST_VIEW_FRAGMENT");
                setTitle(R.string.viewShopListDetail);
                break;

            case ADD:
                ShopListEditFragment shopAddFragment = new ShopListEditFragment();
                setTitle(R.string.ftl_add);
                Bundle bundle = new Bundle();
                bundle.putBoolean(NEW_SHOPLIST_EXTRA, true);
                shopAddFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.product_container, shopAddFragment, "SHOPLIST_ADD_FRAGMENT");
                break;
        }

        fragmentTransaction.commit();
    }
}
