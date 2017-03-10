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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadJSONRetrofit extends AppCompatActivity {

    @BindView(R.id.tvRetrofit)
    TextView tvRetrofit;

    @BindView(R.id.etIdPost)
    EditText etIdPost;

    @BindView(R.id.btListar)
    Button btListar;

    private String uri = "https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_jsonretrofit);
        ButterKnife.bind(this);

        btListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etIdPost.getText().toString().equals("")){
                    getAllPosts();
                }else {
                    int postid = -1;
                    try{
                        postid = Integer.parseInt(etIdPost.getText().toString());
                    }catch (NumberFormatException e){
                        tvRetrofit.setText("Buscar apenas por números inteiros =}");
                        etIdPost.setText("");
                    }

                    if(postid != -1)
                        getPost(postid);
                }
            }
        });
    }

    public void getPost(final int id){
        Retrofit retrofit = buildRetrofit(uri);

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
        Retrofit retrofit = buildRetrofit(uri);

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

    public Retrofit buildRetrofit(String uri){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(uri)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
