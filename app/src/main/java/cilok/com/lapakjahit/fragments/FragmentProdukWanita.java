package cilok.com.lapakjahit.fragments;

/**
 * Created by Alhaura on 07/05/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cilok.com.lapakjahit.R;
import cilok.com.lapakjahit.adapters.AdapterProduct;
import cilok.com.lapakjahit.callback.GetListProductCallback;
import cilok.com.lapakjahit.entity.Product;
import cilok.com.lapakjahit.tasks.TaskProduct;

public class FragmentProdukWanita extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private TaskProduct taskProduct;

    private SwipeRefreshLayout mSwipeRefreshLayoutProdukWanita;
    //the recylerview containing showing all our message;
    private RecyclerView mRecylerProductWanita;
    //the TextView containing error messages generated by Volley
    private TextView mTextError;
    private AdapterProduct mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_produk_fashion, container, false);

        mSwipeRefreshLayoutProdukWanita = (SwipeRefreshLayout)layout.findViewById(R.id.swipeProduk);
        mSwipeRefreshLayoutProdukWanita.setOnRefreshListener(this);
        mRecylerProductWanita = (RecyclerView)layout.findViewById(R.id.listProduk);
        mTextError = (TextView)layout.findViewById(R.id.textError);

        mLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecylerProductWanita.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterProduct(getActivity());
        mRecylerProductWanita.setAdapter(mAdapter);
        mSwipeRefreshLayoutProdukWanita.setRefreshing(true);
        taskProduct = new TaskProduct(getActivity());
        taskProduct.getProductInBackground(new GetListProductCallback() {
            @Override
            public void onGetListProductLoadedListener(ArrayList<Product> listProduct) {
                mAdapter.setListProduk(listProduct);
                mSwipeRefreshLayoutProdukWanita.setRefreshing(false);
            }
        },"fashion_wanita");


        return layout;
    }

    @Override
    public void onRefresh() {
        taskProduct = new TaskProduct(getActivity());
        taskProduct.getProductInBackground(new GetListProductCallback() {
            @Override
            public void onGetListProductLoadedListener(ArrayList<Product> listProduct) {
                mAdapter.setListProduk(listProduct);
                mSwipeRefreshLayoutProdukWanita.setRefreshing(false);
            }
        },"fashion_wanita");
    }
}
