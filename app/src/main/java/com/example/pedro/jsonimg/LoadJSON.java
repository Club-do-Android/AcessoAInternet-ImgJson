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

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadJSON extends AppCompatActivity {

    // Primeiramente, inicializamos o OkHttpClient, e um array de posts,
    // que será utilizado quando a requisição for retornada
    private OkHttpClient client = new OkHttpClient();
    private ArrayList<Post> posts = new ArrayList<>();

    // Com ajuda do ButterKnife, é feito um data-binding, ligando o
    // TextView tvLista com o que foi declarado no XML da view.
    // Evitando o uso do findViewById + o tipo de view
    @BindView(R.id.tvLista)
    TextView tvLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_json);

        // Único requisito para que o Butternife funcione
        ButterKnife.bind(this);

        getJSON();
    }

    public void getJSON(){
        try{
            // É criada uma Request que recebe o requestbuilder com a URL que vai ser utilizada.
            Request req = new Request.Builder().url("https://jsonplaceholder.typicode.com/posts").build();

            // Utilizando o cliente do OkHttp, fazemos uma nova call passando o Request,
            // e o enviamos. Lembrando que isso acontece de forma assincrona.
            client.newCall(req).enqueue(new Callback() {

                // Tratamento de erros.
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                // Funcão que recebe a resposta da requisição.
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // Gson é uma ferramenta que receberá a resposta e pode converter ela para JSON.
                    Gson gson = new Gson();

                    // Auxiliar que vai identificar o tipo de informação (uma lista de Posts).
                    Type postListType = new TypeToken<ArrayList<Post>>(){}.getType();

                    // Gson sendo utilizado para passar a resposta obtida para JSON,
                    // e já preenchendo o array de Posts.
                    posts = gson.fromJson(response.body().string(), postListType);

                    // A view contém apenas um textview, o Stringbuilder ajudará a organizar
                    // os resultados em uma grande string.
                    final StringBuilder lista = new StringBuilder();
                    for(Post post: posts){
                        lista.append(post);
                        lista.append("\n");
                    }

                    // Como o OkHttp funciona de forma assincrona, precisamos passar o runOnUiThread,
                    // que possibilitará a manipulação do conteúdo da view dentro do método onResponse
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvLista.setText(lista);
                        }
                    });


                    if (!response.isSuccessful()) {
                        throw new IOException("Erro inesperado" + response);
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


}
//https://jsonplaceholder.typicode.com/posts