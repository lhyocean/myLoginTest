package cn.hello.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.hello.jnutil.R;

public class ShareingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareing);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setImageResource(R.drawable.ic_launcher_square);
    }
}
