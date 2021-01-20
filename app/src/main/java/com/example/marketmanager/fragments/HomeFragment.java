package com.example.marketmanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.marketmanager.Adapter.ItemAdapter;
import com.example.marketmanager.R;
import com.example.marketmanager.models.CartItems;
import com.example.marketmanager.models.Order;
import com.example.marketmanager.models.Product;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment {

    Button btnScan, btnInvoice;
    private DatabaseReference dbReference;
    private ListView lvProductResult;
    public ArrayList<CartItems> dataList;
    private ItemAdapter adapter;
    private TextView Total;
    Product product = new Product();
    CartItems cartItems = new CartItems();





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity();
        FirebaseApp.initializeApp(getContext());
        dbReference = FirebaseDatabase.getInstance().getReference();
        btnScan = view.findViewById(R.id.btn_scan);
        btnInvoice = view.findViewById(R.id.btn_billprint);
        lvProductResult = view.findViewById(R.id.lv_result);
        Total = view.findViewById(R.id.txtTotal);
        dataList = new ArrayList<>();




        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan a barcode");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(true);
                integrator.setOrientationLocked(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.forSupportFragment(HomeFragment.this).initiateScan();
            }
        });

        btnInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size() < 0) {
                    Toast.makeText(getContext(), "The list is empty", Toast.LENGTH_SHORT).show();

                }
                else {
                    Date now = new Date();
                    long timestamp = now.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    String dateStr = sdf.format(timestamp);


                    String orderKey = dbReference.push().getKey();
                    Order order= new Order(dataList,orderKey,dateStr,Float.parseFloat(Total.getText().toString()));
                    // Ghi ngay cho phieu moi nhap
                    for(CartItems cartItems : dataList)
                    {
                        String itemkey = dbReference.push().getKey();
                        dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(orderKey).child("CartItems").child(itemkey).setValue(cartItems);

                    }
                    dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(orderKey).child("OrderId").setValue(order.getOrderId());
                    dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(orderKey).child("Time").setValue(order.getTime());
                    dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(orderKey).child("Total").setValue(order.getTotal());

                    Toast.makeText(getContext(), "Paid", Toast.LENGTH_SHORT).show();
                    dataList.clear();
                }
            }
        });

        lvProductResult.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dataList.remove(position);
                adapter.notifyDataSetChanged();
                TotalListview();
                return false;
            }
        });
    }




    public void TotalListview()
    {
        float sum = 0;
        int size = dataList.size();
        for(int i = 0; i < size; ++i){
            sum += dataList.get(i).getPrice();
        }
        Total.setText(String.valueOf(sum));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
            } else {

                //Luu Product vua quet vao Mang

                dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products").orderByChild("Barcode").equalTo(result.getContents())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                    //Khoi tao Cart

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean Checkhang = true;

                        for (DataSnapshot ds :snapshot.getChildren())
                        {
                            product = ds.getValue(Product.class);
                           final float giahientai = product.getPrice();
                           // Toast.makeText(getContext(), product.getName() + "\n" + ds.getValue(Product.class).getProductid(), Toast.LENGTH_SHORT).show();
                            if (dataList.size() >  0)
                            {


                                for(int i = 0;i<dataList.size();i++)
                                {



                                    if(dataList.get(i).getProductid().equals(ds.getValue(Product.class).getProductid()) == true)
                                    {
                                        dataList.get(i).setQuantity(dataList.get(i).getQuantity()+1);
                                        dataList.get(i).setPrice((giahientai * dataList.get(i).getQuantity()));
                                        Checkhang = false;
                                        break;

                                    }
                                }
                                if (Checkhang == true)
                                {
                                    cartItems = new CartItems(product.getName(), product.getPrice(), product.getProductid(),1);
                                    dataList.add(cartItems);
                                    break;
                                }
                            }
                            else
                            {
                                cartItems = new CartItems(product.getName(), product.getPrice(), product.getProductid(), 1);
                        //        Toast.makeText(getContext(),"Chay else ben ngoai"+ cartItems.getQuantity(), Toast.LENGTH_SHORT).show();
                                dataList.add(cartItems);
                            }

                        }
                        adapter = new ItemAdapter(dataList,R.layout.item_scan, getContext());
                        lvProductResult.setAdapter(adapter);
                        TotalListview();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }




}