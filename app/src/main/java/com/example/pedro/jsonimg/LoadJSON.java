package com.example.pedro.jsonimg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedro.jsonimg.model.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadJSON extends AppCompatActivity {
    private OkHttpClient client = new OkHttpClient();
    private ArrayList<Post> posts = new ArrayList<>();
    private TextView tvLista;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_json);

        tvLista = (TextView)findViewById(R.id.tvLista);
        getJSON();
    }

    public void getJSON(){
        try{
            Request req = new Request.Builder().url("https://jsonplaceholder.typicode.com/posts").build();
            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson = new Gson();
                    Type postListType = new TypeToken<ArrayList<Post>>(){}.getType();

                    posts = gson.fromJson(response.body().string(), postListType);

                    final StringBuilder lista = new StringBuilder();
                    for(Post post: posts){
                        lista.append(post);
                        lista.append("\n");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvLista.setText(lista);
                        }
                    });


                    if (!response.isSuccessful()) {
                        throw new IOException("Codigo inesperado" + response);
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


}
//https://jsonplaceholder.typicode.com/posts