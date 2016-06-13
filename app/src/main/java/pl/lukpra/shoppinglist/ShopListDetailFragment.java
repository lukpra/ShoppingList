package pl.lukpra.shoppinglist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopListDetailFragment extends Fragment {


    public ShopListDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop_list_detail, container, false);

        TextView name = (TextView) view.findViewById(R.id.viewsListName);
        TextView info = (TextView) view.findViewById(R.id.viewsListinfo);
        ImageView img = (ImageView) view.findViewById(R.id.viewsListIcon);

        Intent intent = getActivity().getIntent();

        ShopList.Category img_category = (ShopList.Category) intent.getSerializableExtra(MainActivity.SLIST_CATEGORY_EXTRA);

        name.setText(intent.getExtras().getString(MainActivity.SLIST_NAME_EXTRA));
        info.setText(intent.getExtras().getString(MainActivity.SLIST_INFO_EXTRA));
        img.setImageResource(ShopList.getDrawable(img_category));
        // Inflate the layout for this fragment
        return view;
    }

}
