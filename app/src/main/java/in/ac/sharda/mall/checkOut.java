package in.ac.sharda.mall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class checkOut extends AppCompatActivity {
    ListView view;
    AsyncHttpClient client;
    RequestParams params;
    ArrayList list=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);



        view=(ListView)findViewById(R.id.checkitems);


        client=new AsyncHttpClient();
        params=new RequestParams();


        client.get("https://mavenlaptopmarket.herokuapp.com/checkout", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String data=new String(responseBody);
                try {
                    JSONArray array=new JSONArray(data);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        int id=obj.getInt("id");
                        int price=obj.getInt("price");
                        String name=obj.getString("name");
                        String desc=obj.getString("desc");
                        list.add(id+"\n"+name+"\n"+desc+"\n"+price);
                        ArrayAdapter adapter=new ArrayAdapter(checkOut.this,android.R.layout.simple_list_item_1,list);
                        view.setAdapter(adapter);

                    }

                }
                catch (JSONException j)
                {

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
