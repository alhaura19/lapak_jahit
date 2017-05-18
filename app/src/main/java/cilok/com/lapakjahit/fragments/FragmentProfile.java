package cilok.com.lapakjahit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cilok.com.lapakjahit.R;
import cilok.com.lapakjahit.activities.InboxMessageActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment implements View.OnClickListener {

    Button buttonChat;
    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);
        buttonChat = (Button)layout.findViewById(R.id.button_message);
        buttonChat.setOnClickListener(this);
        return  layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_message:
                startActivity(new Intent(getActivity(), InboxMessageActivity.class));
                break;
        }
    }
}
