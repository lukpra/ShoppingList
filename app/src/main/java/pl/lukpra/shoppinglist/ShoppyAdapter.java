package pl.lukpra.shoppinglist;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Derezady on 2016-06-13.
 */
public class ShoppyAdapter extends ArrayAdapter<ShopList>{

    public static class ViewHolder {
        TextView name;
        TextView info;
        ImageView img;
    }

    public ShoppyAdapter(Context context, ArrayList<ShopList> lists){
        super(context, 0, lists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ShopList slist = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null){

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.listShoppingTitle);
            viewHolder.info = (TextView) convertView.findViewById(R.id.listShoppingNote);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.listShoppingImg);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.name.setText(slist.getName());
        viewHolder.info.setText(slist.getInfo());
        viewHolder.img.setImageResource(slist.getRightDrawable());

        return convertView;
    }
}
