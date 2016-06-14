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
    private AlertDialog categoryDialogObject, confirmDialogObject;
    public ShopListEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_shop_list_edit, container, false);

        name = (EditText) fragmentLayout.findViewById(R.id.editListName);
        info = (EditText) fragmentLayout.findViewById(R.id.editListinfo);
        img = (ImageButton) fragmentLayout.findViewById(R.id.editListIcon);
        Button saveButton = (Button) fragmentLayout.findViewById(R.id.SaveInfo);


        Intent intent = getActivity().getIntent();

        name.setText(intent.getExtras().getString(MainActivity.SLIST_NAME_EXTRA));
        info.setText(intent.getExtras().getString(MainActivity.SLIST_INFO_EXTRA));

        ShopList.Category imgCat = (ShopList.Category) intent.getSerializableExtra(MainActivity.SLIST_CATEGORY_EXTRA);
        savedButtonCattegory = imgCat;
        img.setImageResource(ShopList.getDrawable(imgCat));

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
