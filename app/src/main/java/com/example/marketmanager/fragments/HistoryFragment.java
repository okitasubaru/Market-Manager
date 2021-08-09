package com.example.marketmanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.marketmanager.Adapter.OrderAdapter;
import com.example.marketmanager.Details;
import com.example.marketmanager.R;
import com.example.marketmanager.models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
    private DatabaseReference dbReference;
    private ListView lv_History;
    private OrderAdapter orderAdapter;
    private ArrayList<Order> orderList;
    private SearchView DataSearch;
    private int vitri = -1;
    private Order order;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbReference = FirebaseDatabase.getInstance().getReference();
        lv_History = view.findViewById(R.id.lv_History);
        DataSearch = view.findViewById(R.id.sr_search);


        LoadDataFromFirebase();
     //   SearchDataList();
        DisplayListview();


    }

    private void DisplayListview() {
        lv_History.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren())
                        {
                            order = ds.getValue(Order.class);
                        }
                        Intent intent = new Intent(getActivity(), Details.class);
                        intent.putExtra("order",order);

                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

/*    private void SearchDataList() {
        DataSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    lv_History.clearTextFilter();
                } else {
                    lv_History.setFilterText(newText);
                }
                return true;

            }
        });

    }*/

    private void LoadDataFromFirebase() {
        orderList = new ArrayList<>();

        dbReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren())
                {
                    order = ds.getValue(Order.class);
                    orderList.add(order);
                }
                orderAdapter = new OrderAdapter(orderList,R.layout.order_history,getContext());
                lv_History.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}