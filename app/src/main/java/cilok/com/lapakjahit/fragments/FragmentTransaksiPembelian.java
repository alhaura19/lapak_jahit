package cilok.com.lapakjahit.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cilok.com.lapakjahit.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTransaksiPembelian extends Fragment {


    public FragmentTransaksiPembelian() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaksi_pembelian, container, false);
    }

}
