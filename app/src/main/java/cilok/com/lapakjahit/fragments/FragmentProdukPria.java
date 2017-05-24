package cilok.com.lapakjahit.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cilok.com.lapakjahit.R;
import cilok.com.lapakjahit.adapters.AdapterProduct;
import cilok.com.lapakjahit.callback.GetProductCallback;
import cilok.com.lapakjahit.entity.Product;
import cilok.com.lapakjahit.tasks.TaskProduct;

public class FragmentProdukPria extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private TaskProduct taskProduct;

    private SwipeRefreshLayout mSwipeRefreshLayoutProdukPria;
    //the recylerview containing showing all our message;
    private RecyclerView mRecylerProductPria;
    //the TextView containing error messages generated by Volley
    private TextView mTextError;
    private AdapterProduct mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FragmentProdukPria() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_produk, container, false);
        mSwipeRefreshLayoutProdukPria = (SwipeRefreshLayout)layout.findViewById(R.id.swipeProduk);
        mSwipeRefreshLayoutProdukPria.setOnRefreshListener(this);
        mRecylerProductPria = (RecyclerView)layout.findViewById(R.id.listProduk);
        mTextError = (TextView)layout.findViewById(R.id.textError);

        mLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecylerProductPria.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterProduct(getActivity());
        mRecylerProductPria.setAdapter(mAdapter);

        taskProduct = new TaskProduct(getActivity());
        taskProduct.getProductInBackground(new GetProductCallback() {
            @Override
            public void onGetProductLoadedListener(ArrayList<Product> listProduct) {
//                Toast.makeText(getActivity(), ""+listProduct.size(), Toast.LENGTH_SHORT).show();
                mAdapter.setListProduk(listProduct);
            }
        },"fashion_pria");


        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onRefresh() {

    }
}
