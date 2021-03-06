package cilok.com.lapakjahit.activities;

import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import cilok.com.lapakjahit.R;
import cilok.com.lapakjahit.adapters.AdapterInboxMessages;
import cilok.com.lapakjahit.application.MyApplication;
import cilok.com.lapakjahit.callback.InboxMessageLoadedListener;
import cilok.com.lapakjahit.database.DBInboxMessage;
import cilok.com.lapakjahit.entity.InboxMessage;
import cilok.com.lapakjahit.extras.MessageSorter;
import cilok.com.lapakjahit.log.L;
import cilok.com.lapakjahit.tasks.TaskMessage;

public class InboxMessageActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    //The key used to store arraylist of inbox messsage objects to and from parcelable
    private static final String STATE_INBOX = "state_inbox";
    // the array list containing out list inbox
    private ArrayList<InboxMessage> mListInbox = new ArrayList<>();
    //the adapter responsible for displaying our inbox within a RecyclerView
    private AdapterInboxMessages mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    //the recylerview containing showing all our message;
    private RecyclerView mRecylerinboxMessage;
    //the TextView containing error messages generated by Volley
    private TextView mTextError;
    private MessageSorter messageSorter = new MessageSorter();

    Toolbar toolbar;
    TextView mTextViewToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_message);

        toolbar = (Toolbar) findViewById(R.id.app_bar_2);
        mTextViewToolbar = (TextView)findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTextViewToolbar.setText("Chat");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextError = (TextView)findViewById(R.id.textVolleyError);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeInboxMessage);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecylerinboxMessage = (RecyclerView)findViewById(R.id.listMessageInbox);
        //set the layout manager before trying to display data
        mRecylerinboxMessage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new AdapterInboxMessages(getApplicationContext());
        mRecylerinboxMessage.setAdapter(mAdapter);


        if (savedInstanceState != null){
            //if this activity starts after a rotation or configuration change, load the existing inbox from a parcelable
            mListInbox = savedInstanceState.getParcelableArrayList(STATE_INBOX);

        }else{
            //if this activity starts for the first time, load the list of inbox from a database
            mListInbox = MyApplication.getWritableInboxDatabase().readInboxMessage(DBInboxMessage.INBOX_MESSAGE);
            //if the database is empty, trigger an AsycnTask to download inbox list from the web
            if (mListInbox.isEmpty()){
                L.m("InboxMessageActivity: executing task from activity");
                mSwipeRefreshLayout.setRefreshing(true);
                TaskMessage taskMessage = new TaskMessage(this);
                taskMessage.getInboxMessage(new InboxMessageLoadedListener() {
                    @Override
                    public void onInboxMessageLoadedListener(ArrayList<InboxMessage> listInboxMessage) {
                        mListInbox = listInboxMessage;
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        mAdapter.setInboxMessage(mListInbox);
                    }
                });
            }
        }
        messageSorter.sortInboxMessageByDate(mListInbox);
        mAdapter.setInboxMessage(mListInbox);
//        new TaskGetnboxMessage(this).execute();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //save the movie list to a parcelable prior to rotation or configuration change
        outState.putParcelableArrayList(STATE_INBOX, mListInbox);
    }

    private void handleVolleyError(VolleyError error) {
        //if any error occurs in the network operations, show the TextView that contains the error message
        mTextError.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            mTextError.setText(R.string.error_timeout);

        } else if (error instanceof AuthFailureError) {
            mTextError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof ServerError) {
            mTextError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof NetworkError) {
            mTextError.setText(R.string.error_network);
            //TODO
        } else if (error instanceof ParseError) {
            mTextError.setText(R.string.error_parser);
            //TODO
        }
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return false;
////        return super.onSupportNavigateUp();
//    }

    /**
     * Called when the AsyncTask finishes load the list of inbox from the web
     */
//    @Override
//    public void onInboxMessageLoadedListener(ArrayList<InboxMessage> listInbox){
//        L.m("InboxMessageActivity: onInboxMessageLoadedListener Activity");
//        //update the Adapter to contain the new movies downloaded from the web
//        if (mSwipeRefreshLayout.isRefreshing()) {
//            mSwipeRefreshLayout.setRefreshing(false);
//        }
//        mAdapter.setInboxMessage(listInbox);
//    }

    @Override
    public void onRefresh() {
        L.t(getApplicationContext(), "onRefresh");
        //load the whole feed again on refresh, dont try this at home :)
//        new TaskLoadInboxMessage(this,getApplicationContext()).execute();
        TaskMessage taskMessage = new TaskMessage(this);
        taskMessage.getInboxMessage(new InboxMessageLoadedListener() {
            @Override
            public void onInboxMessageLoadedListener(ArrayList<InboxMessage> listInboxMessage) {
                mListInbox = listInboxMessage;
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                mAdapter.setInboxMessage(mListInbox);
            }
        });
    }
}
