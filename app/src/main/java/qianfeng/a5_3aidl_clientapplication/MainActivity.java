package qianfeng.a5_3aidl_clientapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import qianfeng.a5_3aidl_remoteserviceapplication.MyAIDL;

/**
 * 客户端开发步骤：
 * 1.将服务端的AIDL文件夹拷贝至客户端的main文件夹下
 * 2.rebuild project,检查是否生成对应的Java文件
 * 3.绑定服务
 */

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private TextView tv;
    private MyAIDL myAIDL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = ((Button) findViewById(R.id.btn));
        tv = ((TextView) findViewById(R.id.tv));

        Intent myAddService = new Intent("myAddService");

        // 安卓Studio版本问题的，API 21之后都要明确指定服务端的包名，API 21之前都可以不明确指定
        myAddService.setPackage("qianfeng.a5_3aidl_remoteserviceapplication"); // 服务端的应用的包名

        ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myAIDL = MyAIDL.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        boolean b = bindService(myAddService, conn, BIND_AUTO_CREATE);
        btn.setEnabled(b);

    }

    public void btnclick(View view) {
        int add = 0;
        try {
            add = myAIDL.add(5, 9);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        tv.setText(add+"");
    }
}
