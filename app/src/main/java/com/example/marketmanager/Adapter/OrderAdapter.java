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

public class OrderAdapter extends BaseAdapter{
    private ArrayList<Order> datasource;
    private int layoutId;
    private Context context;

    public OrderAdapter(ArrayList<Order> datasource, int layoutId, Context context) {
        this.datasource = datasource;
        this.layoutId = layoutId;
        this.context = context;
    }


    @Override
    public int getCount() {
        return datasource.size();
    }


    @Override
    public Object getItem(int position) {
        return datasource.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtHShowId, txtHShowDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId,null);
            viewHolder = new ViewHolder();

            viewHolder.txtHShowId = convertView.findViewById(R.id.txtHistoryShowId);
            viewHolder.txtHShowDate = convertView.findViewById(R.id.txtHistoryShowDate);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Order order = datasource.get(position);

        viewHolder.txtHShowId.setText("Bill Id: "+order.getOrderId());
        viewHolder.txtHShowDate.setText("Bill Date: "+ order.getTime());

        return convertView;
    }

}
