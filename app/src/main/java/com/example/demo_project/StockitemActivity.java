package com.example.demo_project;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockitemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    MaterialEditText Sitem_name, Sopn_qty, Srate, Sstock_group_name;
    TextView Sunit, Samount;
    String item_name, stock_group_name, unit, opn_qty, rate, amount;
    RequestQueue requestQueue;
    RequestQueue rq;
    private ProgressDialog progress;
    StringRequest request;
    StringRequest request2;
    Button submit;
    String username, bloodgroup, bloodgroup2;
    private JSONArray jsonArray=null;

    public static String item_name_token;

    //String[] blood_group;

    ArrayList<String> itemlist;

    String Gender[] = {"Select", "PCS", "ML", "KG"};
    ArrayAdapter<CharSequence> adapterBG;
    ArrayAdapter<CharSequence> adapterGender;
    //List<String> listsource = new ArrayList<>();
    //List<String> listsource2 = new ArrayList<>();
    //List<String> listsource3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockitem);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Stock Item Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Sitem_name=(MaterialEditText)findViewById(R.id.item_name);
        Sstock_group_name=(MaterialEditText)findViewById(R.id.stock_group_name);
        Sunit=(TextView)findViewById(R.id.unit);
        Sopn_qty=(MaterialEditText)findViewById(R.id.opn_qty);
        Srate=(MaterialEditText)findViewById(R.id.rate);
        Samount=(TextView)findViewById(R.id.amount);
        submit = (Button)findViewById(R.id.submit);

        itemlist = new ArrayList<>();

        //username = getIntent().getStringExtra("username");

        username = LoginActivity.token;

        progress = new ProgressDialog(this);

        jsonwork();


        //generateData();
        //generateData1();

        Spinner spin = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemlist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(this);

        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> bb = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Gender);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(bb);
        spin2.setOnItemSelectedListener(this);

        Samount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a, b;
                a = Integer.parseInt(Sopn_qty.getText().toString());
                b = Integer.parseInt(Srate.getText().toString());
                int c = a*b;
                Samount.setText(String.valueOf(c));
            }
        });

        submit();
    }

    /*private void generateData() {
        for (int i = 0; i <Blood_group.length; i++) {
            listsource3.add(Blood_group[i]);
        }
    }*/

    /*private void generateData1() {
        for (int i = 0; i < 4; i++) {
            listsource2.add(Gender[i]);
        }
    }*/

    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long Id) {


        Spinner spinner = (Spinner) parent;
        Spinner spinner2 = (Spinner) parent;

        if (spinner.getId() == R.id.spinner) {
            bloodgroup = parent.getItemAtPosition(position).toString();
            Sstock_group_name.setText(bloodgroup);
        }

        else if (spinner2.getId() == R.id.spinner2) {
            bloodgroup2 = parent.getItemAtPosition(position).toString();
            Sunit.setText(bloodgroup2);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    void submit() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                item_name=Sitem_name.getText().toString();
                stock_group_name=Sstock_group_name.getText().toString();
                unit=Sunit.getText().toString();
                opn_qty=Sopn_qty.getText().toString();
                rate=Srate.getText().toString();
                amount=Samount.getText().toString();

                item_name_token = item_name;

                progress.setMessage("Please Wait......");
                progress.show();

                //Toast.makeText(BloodRequest.this,name+email+date+phone, Toast.LENGTH_LONG).show();

                RequestQueue requestQueue = Volley.newRequestQueue(StockitemActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.stock_item,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("success")){
                                    progress.dismiss();
                                    Toast.makeText(StockitemActivity.this, "request added successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    progress.dismiss();
                                    Toast.makeText(StockitemActivity.this, "request added successfully", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(StockitemActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("item_name", item_name);
                        params.put("stockgroup_name", stock_group_name);
                        params.put("unit", unit);
                        params.put("opn_qty",opn_qty );
                        params.put("rate", rate);   //email of the user that posted request(logged in user)
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
        request=new StringRequest(Request.Method.POST, UrlClass.stock_group_show, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Toast.makeText(BloodRequestWall.this, "res="+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    jsonArray=jsonObject.getJSONArray("result");
                    if(jsonArray.length()==0)
                    {
                        Toast.makeText(StockitemActivity.this, "no data", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject res=jsonArray.getJSONObject(i);

                            itemlist.add(res.getString("group_name"));
                        }

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
}
