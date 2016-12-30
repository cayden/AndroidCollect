package com.cayden.collect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cayden.collect.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SendPhoneActivity extends AppCompatActivity implements View.OnClickListener{

    Button send_btn;
    TextView tv_result;
    private List<String> phones=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_phone);

        send_btn=(Button)findViewById(R.id.send_btn);
        tv_result=(TextView)findViewById(R.id.tv_result);
        send_btn.setOnClickListener(this);

        initData("1.txt");
    }

    private void initData(String fileName){
        /**
         * 读取配置文件存取手机号
         */

        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null){

                phones.add(line.trim());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        showResult();
    }
    StringBuffer sb=new StringBuffer();
    private void showResult(){

        sb.append("读取的手机记录数为:\n");
        sb.append(phones.size()+"条");

        tv_result.setText(sb.toString());
    }

    @Override
    public void onClick(View view) {
        sendPhones();
    }
    private void sendPhones(){
        sb.append("开始发送....");
        StringBuffer mobiles=new StringBuffer();
        for(int i=0;i<phones.size();i++){
            mobiles.append(phones.get(i)+";");
        }
        sb.append(mobiles.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("address", mobiles.toString());
        intent.putExtra("sms_body", "[百洋欧典业主]诉讼报名已经结束,收到短信后麻烦各位添加QQ群:523485101,肖律师会在群里告知具体流程,进群后务必修改群昵称为报名时的姓名,后续会进行核实身份,群号码不要告知他人谢谢!");
        intent.setType("vnd.android-dir/mms-sms");
        startActivity(intent);

        tv_result.setText(sb.toString());
    }
}
