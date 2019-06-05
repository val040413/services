package com.example.prubaservicios;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private final String BASEURL = "http://172.20.10.11:8000/api/categoria/";
    private EditText et_id, et_dato;
    private Button btn_crear, btn_selec, btn_actu, btn_elim;
    private Context ctx;
    private StringRequest strq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        requestQueue = Volley.newRequestQueue(ctx);

        et_id = (EditText) findViewById(R.id.et_id);
        et_dato = (EditText) findViewById(R.id.et_dato);


        btn_crear = (Button) findViewById(R.id.btn_crear);
        btn_selec = (Button) findViewById(R.id.btn_seleccionar);
        btn_actu = (Button) findViewById(R.id.btn_actu);
        btn_elim = (Button) findViewById(R.id.btn_eliminar);

        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crear();
            }
        });
        btn_actu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar();
            }
        });
        btn_elim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar();
            }
        });

        btn_selec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });
    }

    private void crear() {

        strq = new StringRequest(Request.Method.POST, BASEURL+"insertar",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rta_servidor", response);
                        Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error_servidor", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("nombre", et_dato.getText().toString());

                return parametros;
            }
        };

        requestQueue.add(strq);

    }

    private void actualizar() {

        strq = new StringRequest(Request.Method.POST, BASEURL+"actualizar/"+et_id.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rta_servidor", response);
                        Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error_servidor", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("nombre", et_dato.getText().toString());

                return parametros;
            }
        };

        requestQueue.add(strq);

    }

    private void eliminar() {

        strq = new StringRequest(Request.Method.DELETE, BASEURL+"eliminar/"+et_id.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rta_servidor", response);
                        Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error_servidor", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("nombre", et_dato.getText().toString());
                return parametros;
            }
        };

        requestQueue.add(strq);
    }

    private void show() {

        strq = new StringRequest(Request.Method.GET, BASEURL+et_id.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rta_servidor", response);
                        Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error_servidor", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("nombre", et_dato.getText().toString());
                return parametros;
            }
        };

        requestQueue.add(strq);
    }


    public void responseHandler(String res){
        try {
            JSONArray response = new JSONArray(res);
            //Toast.makeText(this, res, Toast.LENGTH_LONG).show();
            for(int i = 0, e = response.length(); i < e; i++){
                JSONObject categoria = (JSONObject) response.get(i);

                String nombre = categoria.get("nombre").toString();

                Categoria cat = new Categoria(
                        Integer.parseInt(categoria.get("id").toString()),
                        (String) categoria.get("nombre")
                );


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
