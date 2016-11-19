package com.example.desenvolvedor.consumidor;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsumirJsonActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DownloadJsonAsyncTask().execute("http://10.0.2.1:3000/parties.json");
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Festa festa = (Festa) l.getAdapter().getItem(position);

        Intent intent = new Intent(this, InformacoesActivity.class);
        intent.putExtra("festa", festa);
        startActivity(intent);
    }

    class DownloadJsonAsyncTask extends AsyncTask<String, Void, List<Festa>>{
        ProgressDialog dialog;

        protected void onPreExecute(){
            super.onPreExecute();
            dialog = ProgressDialog.show(ConsumirJsonActivity.this, "Aguarde",
                    "Fazendo download do JSON");
        }

        protected List<Festa> doInBackground(String... params){
            String urlString = params[0];
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(urlString);
            try {
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    String json = getStringFromInputStream(instream);
                    instream.close();
                    List<Festa> festas = getFestas(json);
                    return festas;
                }
            } catch (Exception e) {
                Log.e("Erro", "Falha ao acessar Web service", e);
            }
            return null;
        }

        protected void onPostExecute(List<Festa> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.size() > 0) {
                ArrayAdapter<Festa> adapter = new ArrayAdapter<Festa>(
                        ConsumirJsonActivity.this,
                        android.R.layout.simple_list_item_1, result);
                setListAdapter(adapter);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ConsumirJsonActivity.this)
                        .setTitle("Erro")
                        .setMessage("Não foi possível acessar as informações!!")
                        .setPositiveButton("OK", null);
                builder.create().show();
            }
        }

        private List<Festa> getFestas(String jsonString) {
            List<Festa> festas = new ArrayList<Festa>();
            try {
                JSONArray festasJson = new JSONArray(jsonString);
                JSONObject festa;

                for (int i = 0; i < festasJson.length(); i++) {
                    festa = new JSONObject(festasJson.getString(i));
                    Log.i("PESSOA ENCONTRADA: ",
                            "nome=" + festa.getString("nome"));

                    Festa objetoFesta = new Festa();
                    objetoFesta.setNome(festa.getString("nome"));
                    objetoFesta.setLocal(festa.getString("local"));
                    objetoFesta.setData(festa.getString("data"));
                    festas.add(objetoFesta);
                }

            } catch (JSONException e) {
                Log.e("Erro", "Erro no parsing do JSON", e);
            }
            return festas;
        }

        private String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return sb.toString();

        }

    }



}
