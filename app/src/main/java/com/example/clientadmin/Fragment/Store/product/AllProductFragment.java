package com.example.clientadmin.Fragment.Store.product;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clientadmin.R;
import com.example.clientadmin.adapter.ProductAdapter;
import com.example.clientadmin.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProductFragment extends Fragment {
    RecyclerView mRecyclerView;
    ProductAdapter productAdapter;
    List<Product> data;

    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_all_product, container, false);
        setControl();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEvent();
    }

    private void setControl() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView_AllProduct);
    }

    private void setEvent() {
        data = new ArrayList<>();
        khoiTao();
        productAdapter = new ProductAdapter(data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(productAdapter);
    }

    private void khoiTao() {
        data.add(new Product("https://vignette.wikia.nocookie.net/leagueoflegends/images/f/f5/So_Lame_Emote.png/revision/latest?cb=20180731211732", "Coffee đen", "50000"));
        data.add(new Product("https://vignette.wikia.nocookie.net/leagueoflegends/images/c/c6/BORF_Emote.png/revision/latest?cb=20190305235647", "Coffee", "50000"));
        data.add(new Product("https://vignette.wikia.nocookie.net/leagueoflegends/images/d/de/Me-ow_Emote.png/revision/latest?cb=20190522072833", "Trà sữa", "50000"));
    }
}
