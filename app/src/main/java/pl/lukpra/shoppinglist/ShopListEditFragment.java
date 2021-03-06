package pl.lukpra.shoppinglist;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopListEditFragment extends Fragment {


    private ImageButton img;
    private ShopList.Category savedButtonCattegory;
    private EditText name, info;
    private boolean newShopListElement = false;
    private static String Modified_Category = "Modifired Category";
    private AlertDialog categoryDialogObject, confirmDialogObject;
    private long slistId = 0 ;
    public ShopListEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if(bundle != null)
        {
            newShopListElement = bundle.getBoolean(ShopListDetailActivity.NEW_SHOPLIST_EXTRA, false); // get if we create a new shoplist element
        }
        if(savedInstanceState != null){
            savedButtonCattegory = (ShopList.Category) savedInstanceState.get(Modified_Category);
        }
        View fragmentLayout = inflater.inflate(R.layout.fragment_shop_list_edit, container, false);

        name = (EditText) fragmentLayout.findViewById(R.id.editListName);
        info = (EditText) fragmentLayout.findViewById(R.id.editListinfo);
        img = (ImageButton) fragmentLayout.findViewById(R.id.editListIcon);
        Button saveButton = (Button) fragmentLayout.findViewById(R.id.SaveInfo);



        Intent intent = getActivity().getIntent();

        slistId = intent.getExtras().getLong(MainActivity.SLIST_ID_EXTRA, 0);

        name.setText(intent.getExtras().getString(MainActivity.SLIST_NAME_EXTRA, "")); // overload to return an empty string if not found
        info.setText(intent.getExtras().getString(MainActivity.SLIST_INFO_EXTRA, ""));

        if(savedButtonCattegory != null){
            img.setImageResource(ShopList.getDrawable(savedButtonCattegory)); // grab old category
        }else if(!newShopListElement){
            ShopList.Category imgCat = (ShopList.Category) intent.getSerializableExtra(MainActivity.SLIST_CATEGORY_EXTRA); // set new
            savedButtonCattegory = imgCat;
            img.setImageResource(ShopList.getDrawable(imgCat));
        }
        buildCategoryDialog();
        buildConfirmDialog();

        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                categoryDialogObject.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                confirmDialogObject.show();
            }
        });

        return fragmentLayout;
        // Inflate the layout for this fragment
    }

    // on orientation change save instance
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(Modified_Category, savedButtonCattegory);
    }

    private void buildCategoryDialog(){

        final String[] categories = new String[]{ "PUMPKIN", "TOMATO", "CARROT", "FISH", "BANANA"};
        AlertDialog.Builder categoryBuilder = new AlertDialog.Builder(getActivity());
        categoryBuilder.setTitle("Choose product type");

        categoryBuilder.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                categoryDialogObject.cancel();

                switch(item){
                    case 0:
                        savedButtonCattegory = ShopList.Category.PUMPKIN;
                        img.setImageResource(R.drawable.pumpkin);
                        break;
                    case 1:
                        savedButtonCattegory = ShopList.Category.TOMATO;
                        img.setImageResource(R.drawable.tomato);
                        break;
                    case 2:
                        savedButtonCattegory = ShopList.Category.CARROT;
                        img.setImageResource(R.drawable.carrot);
                        break;
                    case 3:
                        savedButtonCattegory = ShopList.Category.FISH;
                        img.setImageResource(R.drawable.fish);
                        break;
                    case 4:
                        savedButtonCattegory = ShopList.Category.BANANA;
                        img.setImageResource(R.drawable.banana);
                        break;
                }
            }
        });

        categoryDialogObject = categoryBuilder.create();
    }

    private void buildConfirmDialog(){
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());
        confirmBuilder.setTitle("Are you sure?");
        confirmBuilder.setMessage("Would you like to save this note?");

        confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int item){
                Log.d("Saving data: ", "Name: " + name.getText() + " info: " + info.getText() + " category: " + savedButtonCattegory);

                ShopListDbAdapter dbAdapter = new ShopListDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();

                if(newShopListElement)
                {
                    dbAdapter.createElement(name.getText() + " ", info.getText() + " ", (savedButtonCattegory == null)? ShopList.Category.PUMPKIN : savedButtonCattegory);
                } else {
                    dbAdapter.updateElement(name.getText() + " ", info.getText() + " ", (savedButtonCattegory == null)? ShopList.Category.PUMPKIN : savedButtonCattegory, slistId);
                }

                dbAdapter.close();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item){
                Log.d("Menu action: ", "canceled");
            }
        });

        confirmDialogObject = confirmBuilder.create();
    }

}
