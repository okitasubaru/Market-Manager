 package com.example.marketmanager.fragments;

import android.app.AlertDialog;
import android.database.DatabaseUtils;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.marketmanager.R;
import com.example.marketmanager.databinding.FragmentStorageBinding;
import com.example.marketmanager.databinding.ListviewItemLayoutBinding;
import com.example.marketmanager.databinding.StorageAddingLayoutBinding;
import com.example.marketmanager.models.Product;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class StorageFragment extends Fragment {
    private DatabaseReference dbReference;
    private FragmentStorageBinding B;
    private ProductDataAdapter adapter;
    private List<Product> dataList;
    private long maxid = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = DataBindingUtil.inflate(inflater, R.layout.fragment_storage, container, false);
        return B.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* initialize Firebase Realtime */
        FirebaseApp.initializeApp(getContext());
        dbReference = FirebaseDatabase.getInstance().getReference();


        handleAddingProduct();
        initListView(dbReference);
    }


    private void initListView(DatabaseReference dbReference) {
        dataList = new ArrayList<>();
        adapter = new ProductDataAdapter(R.layout.listview_item_layout, dataList);
        B.lvProducts.setAdapter(adapter);


        dbReference.child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();

                if(snapshot.exists())
                {
                    maxid = (snapshot.getChildrenCount());
                }

                for (DataSnapshot ds : snapshot.getChildren()) {
                    dataList.add(ds.getValue(Product.class));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void handleAddingProduct() {
        B.btnAddProduct.setOnClickListener( v -> {
            LayoutInflater inflater = getLayoutInflater();
            StorageAddingLayoutBinding dialogBinder = DataBindingUtil.inflate(inflater, R.layout.storage_adding_layout, null, false);


            AlertDialog dialog = null;
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            dialogBinder.btnAdd.setOnClickListener(btn -> {
                Product p = new Product(
                        String.valueOf(maxid+1),
                        dialogBinder.edtName.getText().toString(),
                        Integer.parseInt(dialogBinder.edtPrice.getText().toString()),
                        Integer.parseInt(dialogBinder.edtAmount.getText().toString()));



                dbReference.child("products").child(p.getProductId()).setValue(p);
                /*dialog.dismiss();*/
            });

            builder.setView(dialogBinder.getRoot());
            dialog = builder.create();
            dialog.show();
        });
    }




    private class ProductDataAdapter extends BaseAdapter {
        private List<Product> dataSource;
        private int layoutId;

        public ProductDataAdapter(int layoutId, List<Product> dataSource) {
            this.dataSource = dataSource;
            this.layoutId = layoutId;
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


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                ListviewItemLayoutBinding B = DataBindingUtil.inflate(inflater, this.layoutId, parent, false);

                B.txtProductName.setText(dataSource.get(position).getName());
                B.txtProductPrice.setText(dataSource.get(position).getPrice() + "");
                B.txtProductAmount.setText("$" + dataSource.get(position).getAmount());

                convertView = B.getRoot();
                convertView.setTag(B.getRoot());
            }
            else {
                convertView.getTag();
            }


            return convertView;
        }

    }
}