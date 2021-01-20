package com.example.marketmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marketmanager.R;
import com.example.marketmanager.models.CartItems;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {
    private ArrayList<CartItems> dataSource;
    private int layoutId;
    private Context context;

    public ItemAdapter(ArrayList<CartItems> dataSource, int layoutId, Context context) {
        this.dataSource = dataSource;
        this.layoutId = layoutId;
        this.context = context;
    }



    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtSName, txtSPrice, txtSQuantity;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId,null);
            viewHolder = new ViewHolder();

            viewHolder.txtSName = convertView.findViewById(R.id.txtProductScanName);
            viewHolder.txtSPrice = convertView.findViewById(R.id.txtProductScanPrice);
            viewHolder.txtSQuantity = convertView.findViewById(R.id.txtScanQuatity);


            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CartItems cartItems = dataSource.get(position);

        viewHolder.txtSName.setText("Name: "+cartItems.getName());
        viewHolder.txtSPrice.setText("Price: "+ cartItems.getPrice()+"$");
        viewHolder.txtSQuantity.setText("Quantity: "+cartItems.getQuantity());

        return convertView;
    }
}
