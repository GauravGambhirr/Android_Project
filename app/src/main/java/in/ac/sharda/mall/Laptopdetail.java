package in.ac.sharda.mall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Laptopdetail extends AppCompatActivity {
    TextView name,desc,price;
    Button add;
    AsyncHttpClient client;
    RequestParams params;
    int lprice;
    String lname,ldesc,m;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptopdetail);


        name=(TextView)findViewById(R.id.LaptopName);
        desc=(TextView)findViewById(R.id.LaptopDescription);
        price=(TextView)findViewById(R.id.LaptopPrice);
        add=(Button)findViewById(R.id.btnAddToCart);


        client=new AsyncHttpClient();
        params=new RequestParams();

        Bundle extra=getIntent().getExtras();

        m=extra.getString("key");

        client.get("https://mavenlaptopmarket.herokuapp.com/product/detail/"+m, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String data=new String(responseBody);
                try {
                    JSONObject obj=new JSONObject(data);
                        lprice=obj.getInt("price");
                        lname=obj.getString("name");
                        ldesc=obj.getString("desc");
                        name.setText(lname);
                        desc.setText(ldesc);
                        price.setText(""+lprice);
                }
                catch (JSONException j)
                {

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("FAIL");
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                params.put("id",m);
                params.put("price",lprice);
                params.put("name",lname);
                params.put("desc",ldesc);

                client.post("https://mavenlaptopmarket.herokuapp.com/cart/add/product",params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        if(new String(responseBody).equals("true"))
                        {
                            Toast.makeText(Laptopdetail.this, "Added to the Cart Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        Toast.makeText(Laptopdetail.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent i=new Intent(Laptopdetail.this,Cart.class);
                startActivity(i);

            }
        });

    }
}
