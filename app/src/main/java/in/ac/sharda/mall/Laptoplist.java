package in.ac.sharda.mall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Laptoplist extends AppCompatActivity {
    AsyncHttpClient client;
    RequestParams params;
    ListView llist;
    ArrayList cdata=new ArrayList();
    List ids=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptoplist);

        llist=(ListView)findViewById(R.id.list);

        client=new AsyncHttpClient();
        params=new RequestParams();

        client.get("https://mavenlaptopmarket.herokuapp.com/product/list", new AsyncHttpResponseHandler() {
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
                        cdata.add(id+"\n"+name+"\n"+desc+"\n"+price);
                        ArrayAdapter adapter=new ArrayAdapter(Laptoplist.this,R.layout.list,cdata);
                        llist.setAdapter(adapter);
                        ids.add(id);
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

        llist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i=new Intent(Laptoplist.this,Laptopdetail.class);


                String idd=(ids.get(position)).toString();
                i.putExtra("key",idd);

                startActivity(i);

            }
        });

   }

}
