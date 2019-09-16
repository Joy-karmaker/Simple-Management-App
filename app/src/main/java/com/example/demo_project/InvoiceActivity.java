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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demo_project.Adapter.RecyclerAdapter;
import com.example.demo_project.Adapter.RecyclerAdapter2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceActivity extends AppCompatActivity {
    private JSONArray jsonArray=null;
    StringRequest request;
    RequestQueue rq;
    private ProgressDialog progress;
    TextView Iinvoice_no, Iinvoice_date, Iorder_no;
    Button add;
    String username, invoice_no, invoice_date, order_no;
    private List<Custom_CardView2> listitems;

    int year,month, day;
    int year1,month1,day1;
    static final int DIALOG_ID=0;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Invoice Statement");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Iinvoice_no = (TextView)findViewById(R.id.invoice_no);
        Iinvoice_date = (TextView)findViewById(R.id.invoice_date);
        Iorder_no = (TextView)findViewById(R.id.order_no);

        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();

        progress = new ProgressDialog(this);

        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listitems = new ArrayList<>();

        add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
                listitems.clear();
                jsonwork();
            }
        });

        username = LoginActivity.token;

    }

    void add() {

                invoice_no=Iinvoice_no.getText().toString();
                invoice_date=Iinvoice_date.getText().toString();
                order_no=Iorder_no.getText().toString();

                //Toast.makeText(BloodRequest.this,name+email+date+phone, Toast.LENGTH_LONG).show();

                RequestQueue requestQueue = Volley.newRequestQueue(InvoiceActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.sales_invoice,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("success")){
                                    //progress.dismiss();
                                    Toast.makeText(InvoiceActivity.this, "request added successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    //progress.dismiss();
                                    Toast.makeText(InvoiceActivity.this, "request added successfully", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(InvoiceActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("invoice_no", invoice_no);
                        params.put("invoice_date", invoice_date);
                        params.put("orderno", order_no);
                        params.put("username", username);

                        return params;
                    }

                };

                requestQueue.add(stringRequest);

    }

    public void jsonwork() {
        //progress.setMessage("Please Wait......");
        //progress.show();
        //String url= "http://192.168.43.223/blood/show.php";
        request = new StringRequest(Request.Method.POST, UrlClass.sales_invoice_cardview, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(InvoiceActivity.this, "res="+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray=jsonObject.getJSONArray("result");
                    if(jsonArray.length()==0)
                    {
                        Toast.makeText(InvoiceActivity.this, "no data", Toast.LENGTH_SHORT).show();
                        //progress.dismiss();
                    } else {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject res=jsonArray.getJSONObject(i);

                            /*int a=Integer.parseInt(res.getString("amount"));
                            amount = amount+a;
                            order++;
                            //Toast.makeText(InvoiceActivity.this, amount, Toast.LENGTH_SHORT).show();

                            String splitter[]=res.getString("Rdate").split("/");
                            int year=Integer.parseInt(splitter[0]);
                            int month=Integer.parseInt(splitter[1]);
                            int day=Integer.parseInt(splitter[2]);
                            if(year1<=year && month1<=month &&day1<=day)

                            {*/
                                //  Toast.makeText(BloodRequestWall.this, "reached inside jsonobject", Toast.LENGTH_SHORT).show();
                                // Toast.makeText(BloodRequestWall.this, "email is ="+res.getString("Remail"), Toast.LENGTH_SHORT).show();
                                Custom_CardView2 listItem =new Custom_CardView2(res.getString("orderno"),
                                        res.getString("item_name"),res.getString("amount")

                                );
                                listitems.add(listItem);
                                //Toast.makeText(BloodRequestWall.this, "email is ="+res.getString("Remail"), Toast.LENGTH_SHORT).show();
                                //email.add(res.getString("Remail"));
                            //}
                        }

                        adapter = new RecyclerAdapter2(listitems, getApplicationContext());
                        recyclerView.setAdapter(adapter);


                        /*total_amount.setText("Total Amount: "+String.valueOf(amount));
                        total_order.setText("No of order: "+String.valueOf(order));*/

                        //progress.dismiss();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    progress.dismiss();

                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("orderno", Iorder_no.getText().toString());
                return params;
            }

        };

        rq= Volley.newRequestQueue(this);
        rq.add(request);
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

                    Iinvoice_date.setText(d+"/"+m+"/"+y);
                    Toast.makeText(InvoiceActivity.this,day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

                }

            };
}
