package com.example.clientadmin.Fragment.Store;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.clientadmin.Fragment.Store.product.AllProductFragment;
import com.example.clientadmin.Fragment.Store.product.BubbleTeaFragment;
import com.example.clientadmin.Fragment.Store.product.CoffeeFragment;
import com.example.clientadmin.Fragment.Store.product.ToppingFragment;
import com.example.clientadmin.MainActivity;
import com.example.clientadmin.R;
import com.example.clientadmin.adapter.ViewPagerAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinSanPhamFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View root;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MaterialButton btnAddProduct;

    private String[] menu_product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_thong_tin_san_pham, container, false);
        setControl();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEvent();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        setEvent();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void setEvent() {
        menu_product = getResources().getStringArray(R.array.menu_product_value);
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_tt_sanpham_to_themSanPhamFragment);
            }
        });
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new AllProductFragment(), menu_product[0]);
        adapter.addFragment(new CoffeeFragment(), menu_product[1]);
        adapter.addFragment(new BubbleTeaFragment(), menu_product[2]);
        adapter.addFragment(new ToppingFragment(), menu_product[3]);
        viewPager.setAdapter(adapter);
    }

    private void setControl() {
        viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        btnAddProduct = (MaterialButton) root.findViewById(R.id.btnAddProduct);
    }
}
