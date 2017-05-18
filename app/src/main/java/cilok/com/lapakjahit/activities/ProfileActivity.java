package cilok.com.lapakjahit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cilok.com.lapakjahit.R;
import cilok.com.lapakjahit.controller.UserController;
import cilok.com.lapakjahit.log.L;
import cilok.com.lapakjahit.network.VolleySingleton;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonChat;
    String userId, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonChat = (Button) findViewById(R.id.button_message);
        buttonChat.setOnClickListener(this);

        try {
            FileInputStream fileInputStream = openFileInput("user.txt");
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while((read=fileInputStream.read())!=-1){
                buffer.append((char)read);
            }
            userId = buffer.substring(0,buffer.indexOf(" "));
            token = buffer.substring(buffer.indexOf(" "+1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        VolleySingleton volleySingleton = new VolleySingleton();
        RequestQueue requestQueue = volleySingleton.getRequestQueue();

        String url = "https://api.bukalapak.com/v2/users/info.json";

        requestQueue.add(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                L.m(response);
                Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.m("" + error.networkResponse.statusCode);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    String key = "Authorization";
                    String encodedString = Base64.encodeToString(String.format("%s:%s", userId, token).getBytes(), Base64.NO_WRAP);
                    String value = String.format("Basic %s", encodedString);
                    map.put(key, value);
                    return map;
                } catch (Exception e) {
                    L.m("Authentication Failure");
                }
                return super.getHeaders();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_message:
                startActivity(new Intent(this, InboxMessageActivity.class));
                break;
        }
    }
}