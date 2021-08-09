package com.example.marketmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marketmanager.R;
import com.example.marketmanager.models.Product;

import java.util.ArrayList;

public class ProductDataAdapter extends BaseAdapter {
    private ArrayList<Product> dataSource;
    private int layoutId;
    private Context context;

    public ProductDataAdapter(Context context,int layoutId, ArrayList<Product> dataSource) {
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
        return position;
    }

    private class ViewHolder{
        TextView txtPName, txtPPrice, txtBarcode;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = inflater.inflate(layoutId,null);
           viewHolder = new ViewHolder();

           viewHolder.txtPName = convertView.findViewById(R.id.txtProductName);
           viewHolder.txtPPrice = convertView.findViewById(R.id.txtProductPrice);
           viewHolder.txtBarcode = convertView.findViewById(R.id.txtProductBarcode);

           convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = dataSource.get(position);

        viewHolder.txtPName.setText("Name: "+product.getName());
        viewHolder.txtPPrice.setText("$"+ product.getPrice());
        viewHolder.txtBarcode.setText("Barcode: "+product.getBarcode());

        return convertView;
    }
}
    