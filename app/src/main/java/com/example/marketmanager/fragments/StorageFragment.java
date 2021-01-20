 package com.example.marketmanager.fragments;

 import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.marketmanager.Adapter.ProductDataAdapter;
import com.example.marketmanager.R;
import com.example.marketmanager.models.Product;
import com.example.marketmanager.models.ProductStorage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


 public class StorageFragment extends Fragment {
    private DatabaseReference dbReference;
    public ProductDataAdapter adapter;
    private ArrayList<Product> dataList;
    private ListView lvProduct;
    private EditText EdtName, EdtPrice,EdtAmount, EdtBarCode;
    private Product product;
    private ProductStorage storage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_storage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton btnAddProduct = view.findViewById(R.id.btnAddProduct);


        lvProduct = view.findViewById(R.id.lvProducts);

        /* initialize Firebase Realtime */
        FirebaseApp.initializeApp(getContext());
        dbReference = FirebaseDatabase.getInstance().getReference();




        btnAddProduct.setOnClickListener( v -> {
            View inflater = getLayoutInflater().inflate(R.layout.storage_adding_layout,null);

            EdtName = inflater.findViewById(R.id.edtName);
            EdtAmount = inflater.findViewById(R.id.edtAmount);
            EdtPrice = inflater.findViewById(R.id.edtPrice);
            EdtBarCode = inflater.findViewById(R.id.edtBarcode);

            AlertDialog dialog = null;
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            Button btnAdd = inflater.findViewById(R.id.btnAdd);
            btnAdd.setOnClickListener(v1 -> {

            String key = dbReference.push().getKey();
                Product p = new Product(
                        key,
                        EdtName.getText().toString(),
                        Integer.parseInt(EdtPrice.getText().toString()),
                        EdtBarCode.getText().toString());

                dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products").child(p.getProductid()).child("Productid").setValue(key);
                dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products").child(p.getProductid()).child("Name").setValue(p.getName());
                dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products").child(p.getProductid()).child("Price").setValue(p.getPrice());
                dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products").child(p.getProductid()).child("Barcode").setValue(p.getBarcode());


                ProductStorage productStorage = new ProductStorage(p,Integer.parseInt(EdtAmount.getText().toString()));
                dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Storage").child(p.getProductid()).setValue(productStorage);
                initListView(dbReference);


            });

            builder.setView(inflater);
            dialog = builder.create();
            dialog.show();
        });
        initListView(dbReference);

    }

   


     private void initListView(DatabaseReference dbReference) {
        dataList = new ArrayList<>();

        dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               for (DataSnapshot ds : snapshot.getChildren()) {
                   dataList.add(ds.getValue(Product.class));
                }

                adapter = new ProductDataAdapter(getContext(),R.layout.listview_item_layout, dataList);
                lvProduct.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}