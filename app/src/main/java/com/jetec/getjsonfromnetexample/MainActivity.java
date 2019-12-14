package com.jetec.getjsonfromnetexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName()+"My";
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catchData();
//        testChineseIndex(); 測試中文索引


    }

    private void testChineseIndex() {
        String catchData = "https://api.myjson.com/bins/pfqbc";
        new Thread(()->{
            try {
                URL url = new URL(catchData);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String line = in.readLine();
                StringBuffer json = new StringBuffer();
                while (line != null) {
                    json.append(line);
                    line = in.readLine();
                }
                JSONArray jsonArray= new JSONArray(String.valueOf(json));
                Log.d(TAG, "onCreate: "+jsonArray);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String data = jsonObject.getString("路口");
                    Log.d(TAG, "onCreate: 路口"+data);
                }

            }catch (Exception x){
                Log.d(TAG, "onCreate: "+x);
            }

        }).start();
    }

    private void catchData(){
        String catchData = "https://api.myjson.com/bins/15majc";
        ProgressDialog dialog = ProgressDialog.show(this,"讀取中"
                ,"請稍候",true);
        new Thread(()->{
            try {
                URL url = new URL(catchData);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String line = in.readLine();
                StringBuffer json = new StringBuffer();
                while (line != null) {
                    json.append(line);
                    line = in.readLine();
                }

                JSONArray jsonArray= new JSONArray(String.valueOf(json));
                for (int i =0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String RptNo = jsonObject.getString("RptNo");
                    String RptName = jsonObject.getString("RptName");
                    String StatCourseNo = jsonObject.getString("StatCourseNo");
                    String StatCourseName = jsonObject.getString("StatCourseName");
                    String DataDate = jsonObject.getString("DataDate");
                    String PlaceNo = jsonObject.getString("PlaceNo");
                    String PlaceName = jsonObject.getString("PlaceName");
                    String PeriodNo = jsonObject.getString("PeriodNo");
                    String PeriodName = jsonObject.getString("PeriodName");
                    String Complex1 = jsonObject.getString("Complex1");
                    String ComplexName = jsonObject.getString("ComplexName");
                    String Complex2 = jsonObject.getString("Complex2");
                    String Complex2Name = jsonObject.getString("Complex2Name");
                    String Complex3 = jsonObject.getString("Complex3");
                    String Complex3Name = jsonObject.getString("Complex3Name");
                    String Complex4 = jsonObject.getString("Complex4");
                    String Complex4Name = jsonObject.getString("Complex4Name");
                    String Complex5 = jsonObject.getString("Complex5");
                    String Complex5Name = jsonObject.getString("Complex5Name");
                    String DeriveNo = jsonObject.getString("DeriveNo");
                    String DeriveExplain = jsonObject.getString("DeriveExplain");
                    String FValue = jsonObject.getString("FValue");
                    String SValue = jsonObject.getString("SValue");
                    String RptDeptNo = jsonObject.getString("RptDeptNo");
                    String RptDeptName = jsonObject.getString("RptDeptName");
                    String CreateTime = jsonObject.getString("CreateTime");
                    String ModifyTime = jsonObject.getString("ModifyTime");

                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("PlaceName",PlaceName);
                    hashMap.put("DataDate",DataDate);
                    hashMap.put("Car",ComplexName);
                    hashMap.put("Type",Complex2Name);
                    hashMap.put("Price",Complex3Name);

                    arrayList.add(hashMap);
                }
                Log.d(TAG, "catchData: "+arrayList);

                runOnUiThread(()->{
                    dialog.dismiss();
                    RecyclerView recyclerView;
                    MyAdapter myAdapter;
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
                    myAdapter = new MyAdapter();
                    recyclerView.setAdapter(myAdapter);

                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvPos,tvType,tvPrice,tvCar,tvDateTime;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvPos = itemView.findViewById(R.id.textView_pos);
                tvType = itemView.findViewById(R.id.textView_type);
                tvPrice = itemView.findViewById(R.id.textView_price);
                tvCar = itemView.findViewById(R.id.textView_car);
                tvDateTime = itemView.findViewById(R.id.textView_time);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvPos.setText(arrayList.get(position).get("PlaceName"));
            holder.tvType.setText("類型："+arrayList.get(position).get("Type"));
            holder.tvPrice.setText("收費與否："+arrayList.get(position).get("Price"));
            holder.tvCar.setText("停放種類："+arrayList.get(position).get("Car"));
            holder.tvDateTime.setText("新增資料時間："+arrayList.get(position).get("DataDate"));

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }


    }
}
