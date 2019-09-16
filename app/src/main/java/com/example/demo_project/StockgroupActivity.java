package com.example.demo_project;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class StockgroupActivity extends AppCompatActivity {

    MaterialEditText Sgroup_name;
    RequestQueue requestQueue;
    private ProgressDialog progress;
    StringRequest request;
    Button submit;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockgroup);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Stock Group Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Sgroup_name=(MaterialEditText)findViewById(R.id.group_name);
        submit = (Button)findViewById(R.id.submit);
        progress = new ProgressDialog(this);

        username = LoginActivity.token;
        Toast.makeText(StockgroupActivity.this, username, Toast.LENGTH_SHORT).show();

        submit();
    }

    void submit() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setMessage("Please Wait......");
                progress.show();

                final String group_name=Sgroup_name.getText().toString();

                //Toast.makeText(BloodRequest.this,name+email+date+phone, Toast.LENGTH_LONG).show();

                RequestQueue requestQueue = Volley.newRequestQueue(StockgroupActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.stock_group,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("success")){
                                    progress.dismiss();
                                    Toast.makeText(StockgroupActivity.this, "request added successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    progress.dismiss();
                                    Toast.makeText(StockgroupActivity.this, "request added failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(StockgroupActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("group_name", group_name);
                        params.put("username", username);

                        return params;
                    }

                };
                requestQueue.add(stringRequest);

            }
        });
    }
}
