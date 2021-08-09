package com.example.marketmanager;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.marketmanager.Adapter.DetailAdapter;
import com.example.marketmanager.models.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Details extends AppCompatActivity {
    private ListView lv_detail;
    private TextView txtDetailId;
    private TextView txtDetailDate;
    private TextView txtTotal;
    private ArrayList<Order> orderArrayList;
    private DetailAdapter detailAdapter;
    private DatabaseReference dbReference;
    private Order order;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        dbReference = FirebaseDatabase.getInstance().getReference();

        Anhxa();
        LoadDataFromFireBase();
    }

    private void LoadDataFromFireBase() {


         order = (Order) getIntent().getSerializableExtra("order");

         txtDetailId.setText(order.getOrderId());
         txtDetailDate.setText(order.getTime());
         txtTotal.setText(String.valueOf(order.getTotal()));



        orderArrayList = new ArrayList<>();
        orderArrayList.add(order);
        detailAdapter  = new DetailAdapter(orderArrayList,R.layout.item_detail_history,Details.this);
        lv_detail.setAdapter(detailAdapter);




    }

    private void Anhxa() {
        lv_detail = findViewById(R.id.lv_detail);
        txtDetailDate = findViewById(R.id.txt_Detail_Date);
        txtDetailId = findViewById(R.id.txt_Detail_Id);
        txtTotal = findViewById(R.id.detail_total);

    }


}