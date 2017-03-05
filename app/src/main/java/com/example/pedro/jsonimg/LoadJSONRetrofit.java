package com.example.pedro.jsonimg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pedro.jsonimg.model.Post;
import com.example.pedro.jsonimg.model.RetrofitInterface;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadJSONRetrofit extends AppCompatActivity {
    private TextView tvRetrofit;
    private EditText etIdPost;
    private Button btListar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_jsonretrofit);

        etIdPost = (EditText) findViewById(R.id.etIdPost);
        btListar = (Button) findViewById(R.id.btListar);

        btListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etIdPost.getText().toString().equals("")){
                    getAllPosts();
                }else {
                    getPost(Integer.parseInt(etIdPost.getText().toString()));
                }
            }
        });
    }

    public void getPost(final int id){
        tvRetrofit = (TextView) findViewById(R.id.tvRetrofit);
        Retrofit retrofit = buildRetrofit();

        RetrofitInterface apiPosts = retrofit.create(RetrofitInterface.class);
        Call<Post> call = apiPosts.getPost(id);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post postTeste = response.body();
                if(postTeste != null){
                    tvRetrofit.setText(postTeste.toString());
                }else{
                    tvRetrofit.setText("Post " + Integer.toString(id) + " nao encontrado!");
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvRetrofit.setText("Post " + Integer.toString(id) + " nao encontrado!");
            }
        });

    }

    public void getAllPosts(){
        tvRetrofit = (TextView) findViewById(R.id.tvRetrofit);
        Retrofit retrofit = buildRetrofit();

        RetrofitInterface apiPosts = retrofit.create(RetrofitInterface.class);
        Call<List<Post>> call = apiPosts.getAllPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                StringBuilder lista = new StringBuilder();
                for(Post p : response.body()){
                    lista.append(p);
                    lista.append("\n");
                }

                tvRetrofit.setText(lista);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvRetrofit.setText("Erro ao se conectar");
            }
        });
    }

    public Retrofit buildRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
