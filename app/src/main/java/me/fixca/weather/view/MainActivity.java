package me.fixca.weather.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import me.fixca.weather.R;
import me.fixca.weather.request.Request;
import me.fixca.weather.request.URLRequest;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.main_loading_textview);
        Request request = new Request(() -> {
            URLRequest urlRequest = new URLRequest("http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt");
            String string = urlRequest.sendRequest();
            if(string != null) {
                Intent lvIntent = new Intent(getApplicationContext(), CityActivity.class);
                lvIntent.putExtra("jsonString", string);
                startActivity(lvIntent);
                finish();
            }
            else {
                textView.setText("오류가 발생했습니다! 인터넷 상태를 재확인 해주신 다음, 앱을 다시 실행해주세요!");
            }
        });
        request.execute();
    }
}