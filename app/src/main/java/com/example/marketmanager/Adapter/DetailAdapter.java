package com.example.marketmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marketmanager.R;
import com.example.marketmanager.models.Order;

import java.util.ArrayList;

public class DetailAdapter extends BaseAdapter {
    private ArrayList<Order> dataSource;
    private int layoutId;
    private Context context;

    public DetailAdapter(ArrayList<Order> dataSource, int layoutId, Context context) {
        this.dataSource = dataSource;
        this.layoutId = layoutId;
        this.context = context;
    }

    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtDName, txtDPrice, txtDQuantity;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId,null);
            viewHolder = new ViewHolder();

            viewHolder.txtDName = convertView.findViewById(R.id.txtDetailName);
            viewHolder.txtDPrice = convertView.findViewById(R.id.txtDetailPrice);
            viewHolder.txtDQuantity = convertView.findViewById(R.id.txtDetailQuatity);


            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Order order = dataSource.get(position);

        viewHolder.txtDName.setText("Name: "+order.getItem());
        viewHolder.txtDPrice.setText("Price: "+ order.getItem()+"$");
        viewHolder.txtDQuantity.setText("Quantity: "+order.getQuantity());

        return convertView;
    }
}
