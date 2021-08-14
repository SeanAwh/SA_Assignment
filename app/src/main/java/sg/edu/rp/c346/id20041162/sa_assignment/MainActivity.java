 package sg.edu.rp.c346.id20041162.sa_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

 public class MainActivity extends AppCompatActivity {
    Button listAddBtn;
    TextView tvLink;
//Testing commit
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listAddBtn = findViewById(R.id.listAddBtn);
        tvLink = findViewById(R.id.tvLink);

        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SeanAwh/SA_Assignment.git"));
                startActivity(intentWeb);
            }
        });


        listAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
                startActivity(intent);
            }
        });
    }
}