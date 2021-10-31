package me.fixca.weather.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import me.fixca.weather.R;
import me.fixca.weather.request.Request;
import me.fixca.weather.request.URLRequest;

@SuppressWarnings("deprecation")
public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Intent thisIntent = getIntent();
        ListView listView = findViewById(R.id.listview);
        try {
            String jsonString = thisIntent.getExtras().getString("jsonString");
            JSONArray jsonArray = new JSONArray(jsonString);
            List<String> list = new LinkedList<>();
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                list.add((String) jsonObject.get("value"));
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, v, position, id) -> {
                try {
                    String code = (String) ((JSONObject) jsonArray.get(position)).get("code");
                    Request request = new Request(() -> {
                        URLRequest urlRequest = new URLRequest("http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl." + code + ".json.txt");
                        String string = urlRequest.sendRequest();
                        if(string != null) {
                            Intent lvIntent = new Intent(getApplicationContext(), GuActivity.class);
                            lvIntent.putExtra("jsonString", string);
                            startActivity(lvIntent);
                        }
                        else {
                            Toast.makeText(CityActivity.this, "오류가 발생했습니다! 인터넷 상태를 재확인 해주신 다음, 앱을 다시 실행해주세요!", Toast.LENGTH_LONG).show();
                        }
                    });
                    request.execute();
                }
                catch(Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "오류가 발생했습니다! 인터넷 상태를 재확인 해주신 다음, 앱을 다시 실행해주세요!", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "오류가 발생했습니다! 인터넷 상태를 재확인 해주신 다음, 앱을 다시 실행해주세요!", Toast.LENGTH_LONG).show();
        }
    }
}