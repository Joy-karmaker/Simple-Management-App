package com.example.demo_project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demo_project.Adapter.RecyclerAdapter;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesorderActivity extends AppCompatActivity {

    MaterialEditText Sorder_no, Sorder_date, Sstock_group_name, Sitem_name, Sqty, Srate, Samount;
    RequestQueue requestQueue;
    private ProgressDialog progress;
    StringRequest request;
    StringRequest request2;
    Button update, add;
    String username;
    private JSONArray jsonArray=null;
    private JSONArray jsonArray2=null;
    private List<Custom_CardView> listitems;

    RequestQueue rq;
    RequestQueue rq2;

    int year,month, day;
    int year1,month1,day1;
    static final int DIALOG_ID=0;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesorder);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sales Order Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Sorder_no=(MaterialEditText)findViewById(R.id.order_no);
        Sorder_date=(MaterialEditText)findViewById(R.id.order_date);
        Sstock_group_name=(MaterialEditText)findViewById(R.id.stock_group_name);
        Sitem_name=(MaterialEditText)findViewById(R.id.item_name);
        Sqty=(MaterialEditText)findViewById(R.id.qty);
        Srate=(MaterialEditText)findViewById(R.id.rate);
        Samount=(MaterialEditText)findViewById(R.id.amount);
        add = (Button) findViewById(R.id.add);
        update = (Button) findViewById(R.id.update);

        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();
        //submit = (Button)findViewById(R.id.submit);
        progress = new ProgressDialog(this);

        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listitems = new ArrayList<>();

        show_item_table();

        jsonwork();

        add();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listitems.clear();
                jsonwork();
            }
        });

    }

    void add() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progress.setMessage("Please Wait......");
               // progress.show();

                final String order_no=Sorder_no.getText().toString();
                final String order_date=Sorder_date.getText().toString();
                final String stock_group_name=Sstock_group_name.getText().toString();
                final String item_name=Sitem_name.getText().toString();
                final String qty=Sqty.getText().toString();
                final String rate=Srate.getText().toString();
                final String amount=Samount.getText().toString();

                username = LoginActivity.token;

                //Toast.makeText(BloodRequest.this,name+email+date+phone, Toast.LENGTH_LONG).show();

                RequestQueue requestQueue = Volley.newRequestQueue(SalesorderActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.sales_order,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("success")){
                                    //progress.dismiss();
                                    Toast.makeText(SalesorderActivity.this, "request added successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    //progress.dismiss();
                                    Toast.makeText(SalesorderActivity.this, "request added successfully", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SalesorderActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("orderno", order_no);
                        params.put("order_date", order_date);
                        params.put("stockgroup_name", stock_group_name);
                        params.put("item_name",item_name );
                        params.put("qty", qty);   //email of the user that posted request(logged in user)
                        params.put("rate", rate);
                        params.put("amount", amount);
                        params.put("username", username);

                        return params;
                    }

                };
                requestQueue.add(stringRequest);

            }
        });
    }

    public void jsonwork() {
        //String url= "http://192.168.43.223/blood/show.php";
        request=new StringRequest(Request.Method.POST, UrlClass.sales_order_cardview, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Toast.makeText(BloodRequestWall.this, "res="+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray=jsonObject.getJSONArray("result");
                    if(jsonArray.length()==0)
                    {
                        Toast.makeText(SalesorderActivity.this, "no data", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject res=jsonArray.getJSONObject(i);
                            /*String splitter[]=res.getString("Rdate").split("/");
                            int year=Integer.parseInt(splitter[0]);
                            int month=Integer.parseInt(splitter[1]);
                            int day=Integer.parseInt(splitter[2]);
                            if(year1<=year && month1<=month &&day1<=day)

                            {*/
                                //  Toast.makeText(BloodRequestWall.this, "reached inside jsonobject", Toast.LENGTH_SHORT).show();
                                // Toast.makeText(BloodRequestWall.this, "email is ="+res.getString("Remail"), Toast.LENGTH_SHORT).show();
                                Custom_CardView listItem =new Custom_CardView(res.getString("stockgroup_name"),
                                        res.getString("item_name"),res.getString("qty"),res.getString("rate"),
                                        res.getString("amount")

                                );
                                listitems.add(listItem);
                                //Toast.makeText(BloodRequestWall.this, "email is ="+res.getString("Remail"), Toast.LENGTH_SHORT).show();
                                //email.add(res.getString("Remail"));
                            //}
                        }
                        adapter = new RecyclerAdapter(listitems, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;


        rq= Volley.newRequestQueue(this);
        rq.add(request);
    }

    public void show_item_table() {

        //String url= "http://192.168.43.223/blood/show.php";
        request2 = new StringRequest(Request.Method.POST, UrlClass.item_table_show, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Toast.makeText(SalesorderActivity.this, "res="+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray2=jsonObject.getJSONArray("result");
                    if(jsonArray2.length()==0)
                    {
                        Toast.makeText(SalesorderActivity.this, "no data", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    } else {
                        for(int i=0;i<jsonArray2.length();i++)
                        {
                            JSONObject res=jsonArray2.getJSONObject(i);

                            Sstock_group_name.setText(res.getString("stockgroup_name"));
                            Sitem_name.setText(res.getString("item_name"));
                            Sqty.setText(res.getString("opn_qty"));
                            Srate.setText(res.getString("rate"));
                            Samount.setText(res.getString("amount"));

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("item_name", StockitemActivity.item_name_token);
                return params;
            }

        };

        rq2= Volley.newRequestQueue(this);
        rq2.add(request2);
    }

    public void showDialogOnButtonClick() {
        Button date = (Button) findViewById(R.id.date);

        date.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year, month, day);
        return null;

    }

    private DatePickerDialog.OnDateSetListener dpickerListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int newYear, int monthOfYear, int dayOfMonth) {
                    year = newYear;
                    month = monthOfYear + 1;
                    day = dayOfMonth;
                    String d;
                    String m;
                    String y= String.valueOf(year);
                    if(day<10)
                    {
                        d="0"+day;
                    }
                    else{
                        d=String.valueOf(day);
                    }
                    if(month<10){
                        m="0"+month;
                    }
                    else {
                        m=String.valueOf(month);
                    }

                    Sorder_date.setText(d+"/"+m+"/"+y);
                    Toast.makeText(SalesorderActivity.this,day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

                }

            };
}
