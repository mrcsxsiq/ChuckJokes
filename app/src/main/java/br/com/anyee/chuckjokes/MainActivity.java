package br.com.anyee.chuckjokes;

import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String URL = "https://api.chucknorris.io/jokes/random";

    private TextView textViewJoke;
    private ImageView imageViewAuthor;
    private Button buttonUpdateJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewJoke = (TextView) findViewById(R.id.textViewJoke);
        imageViewAuthor = (ImageView) findViewById(R.id.imageViewAuthor);
        buttonUpdateJoke = (Button) findViewById(R.id.buttonUpdateJoke);

        buttonUpdateJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateJoke();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.info:
                showCustomDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateJoke() {

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url =  URL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                        JSONObject obj = new JSONObject(response);

                        textViewJoke.setText("\""+obj.getString("value")+"\"");

                        String icon_url = obj.getString("icon_url");
                        Glide
                            .with(MainActivity.this)
                            .load(icon_url)
                            .into(imageViewAuthor);

                        } catch (Exception e) {};
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

    }

    public void showCustomDialog() {

        final BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);

        View view = getLayoutInflater().inflate(R.layout.layout_info, null);

        Button buttonOk = view.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();

    }
}
