package me.fixca.weather.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import me.fixca.weather.R;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();
        String xml = intent.getExtras().getString("xmlString");

        TextView textView = findViewById(R.id.weather_information);

        StringBuilder sb = new StringBuilder();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            Document doc = documentbuilder.parse(is);
            Element element = doc.getDocumentElement();

            NodeList list = element.getElementsByTagName("data");
            Node node = list.item(list.getLength() - 1).getFirstChild();
            for (int i = 0; i < list.item(list.getLength() - 1).getChildNodes().getLength(); i++) {
                switch(node.getNodeName()) {
                    case "temp" :
                        sb.append("현재 온도 : " + node.getTextContent() + "°C\n");
                        break;
                    case "tmx" :
                        sb.append("오늘 최고 온도 : " + node.getTextContent() + "°C\n");
                        break;
                    case "tmn" :
                        sb.append("오늘 최저 온도 : " + node.getTextContent() + "°C\n");
                        break;
                    case "wfKor" :
                        sb.append("현재 날씨 : " + node.getTextContent() + "\n");
                        break;
                    case "pop" :
                        sb.append("강수 확률 : " + node.getTextContent() + "%\n");
                        break;
                    case "r12" :
                        sb.append("12시간 예상 강수량 : " + node.getTextContent() + "mm\n");
                        break;
                    case "ws" :
                        sb.append("풍속 : " + node.getTextContent() + "m/s, ");
                        break;
                    case "wdKor" :
                        sb.append(node.getTextContent() + "풍");
                        break;
                }
                node = node.getNextSibling();
            }
            textView.setText(sb.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}