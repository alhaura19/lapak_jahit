package cilok.com.lapakjahit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cilok.com.lapakjahit.R;
import cilok.com.lapakjahit.activities.LoginActivity;
import cilok.com.lapakjahit.callback.GetTransactionCallback;
import cilok.com.lapakjahit.controller.UserController;
import cilok.com.lapakjahit.entity.Transaction;
import cilok.com.lapakjahit.log.L;
import cilok.com.lapakjahit.tasks.TaskTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTransaksiPembelian extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    UserController userController;
    ImageView mImageViewBelomLogin;
    TextView mTextViewBelomLogin;
    Button mButtonLogin;

    private static final String STATE_PEMBELIAN = "state_pembelian";
    private SwipeRefreshLayout mSwipeRefreshLayoutTransaksiPembelian;
    //the recylerview containing showing all our message;
    private RecyclerView mRecylerTransactionPembelian;
    //the TextView containing error messages generated by Volley
    private TextView mTextError;


    public FragmentTransaksiPembelian() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userController = new UserController(getActivity());
        final View layout = inflater.inflate(R.layout.fragment_transaksi_pembelian, container, false);
        mButtonLogin = (Button) layout.findViewById(R.id.button_login);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(layout.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        mImageViewBelomLogin = (ImageView) layout.findViewById(R.id.image_view_pic_belom_login);
        mTextViewBelomLogin = (TextView) layout.findViewById(R.id.text_view_belom_login);

        mTextError = (TextView)layout.findViewById(R.id.textErrorTransaksi);
        mSwipeRefreshLayoutTransaksiPembelian = (SwipeRefreshLayout)layout.findViewById(R.id.swipeTransaksiPembelian);
        mSwipeRefreshLayoutTransaksiPembelian.setOnRefreshListener(this);
        mRecylerTransactionPembelian = (RecyclerView)layout.findViewById(R.id.listPembelian);


        if (authenticate() == true) {
            mImageViewBelomLogin.setVisibility(View.GONE);
            mTextViewBelomLogin.setVisibility(View.GONE);
            mButtonLogin.setVisibility(View.GONE);
            mSwipeRefreshLayoutTransaksiPembelian.setRefreshing(true);
            TaskTransaction taskTransaction;
            taskTransaction = new TaskTransaction(getActivity());
            taskTransaction.getTransactionDataInBackground(new GetTransactionCallback() {
                @Override
                public void onGetTransactionLoadedListener(ArrayList<Transaction> listTransaction) {
//                    L.m("list Pembelian: "+listTransaction.size());
//                    Toast.makeText(getActivity(), ""+listTransaction.size(), Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayoutTransaksiPembelian.setRefreshing(false);
                }
            },"pembelian");

        }
        return layout;
    }

    private boolean authenticate() {
        return userController.getUserLoggedIn();
    }

    @Override
    public void onRefresh() {TaskTransaction taskTransaction;
        taskTransaction = new TaskTransaction(getActivity());
        taskTransaction.getTransactionDataInBackground(new GetTransactionCallback() {
            @Override
            public void onGetTransactionLoadedListener(ArrayList<Transaction> listTransaction) {
//                    L.m("list Pembelian: "+listTransaction.size());
//                    Toast.makeText(getActivity(), ""+listTransaction.size(), Toast.LENGTH_SHORT).show();
                if (mSwipeRefreshLayoutTransaksiPembelian.isRefreshing())
                    mSwipeRefreshLayoutTransaksiPembelian.setRefreshing(false);
            }
        },"pembelian");

    }
}
