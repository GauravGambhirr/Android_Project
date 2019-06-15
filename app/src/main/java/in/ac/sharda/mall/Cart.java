package in.ac.sharda.mall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Cart extends AppCompatActivity {
    TextView count;
    AsyncHttpClient client;
    RequestParams params;
    ArrayList data=new ArrayList();
    Button checkout;
    int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        count=(TextView)findViewById(R.id.tc);
        checkout=(Button)findViewById(R.id.check);

        client=new AsyncHttpClient();
        params=new RequestParams();


        client.get("https://mavenlaptopmarket.herokuapp.com/cart/products/count", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String cdata=new String(responseBody);
                try {
                    JSONArray array=new JSONArray(cdata);
                    for(int i=0;i<array.length();i++)
                    {
                        c++;
                    }

                    count.setText(""+c);

                }
                catch (JSONException j)
                {

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(Cart.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Cart.this,checkOut.class);
                startActivity(i);
            }
        });

    }
}
