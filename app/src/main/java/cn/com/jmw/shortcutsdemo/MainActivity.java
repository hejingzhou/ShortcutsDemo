package cn.com.jmw.shortcutsdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //要确保API Level 大于等于 25才可以创建动态shortcut，否则会报异常。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            initDynamicShortcuts();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void initDynamicShortcuts() {
        //①、创建动态快捷方式的第一步，创建ShortcutManager
        ShortcutManager scManager = getSystemService(ShortcutManager.class);
        //②、构建动态快捷方式的详细信息
        ShortcutInfo scInfoOne;
        scInfoOne = new ShortcutInfo.Builder(this, "dynamic_one")
                .setShortLabel("动态手套短标题")
                .setLongLabel("动态手套长标题")
                .setIcon(Icon.createWithResource(this, R.mipmap.shoutao))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com")))
                .build();

        ShortcutInfo scInfoTwo;
        scInfoTwo = new ShortcutInfo.Builder(this, "dynamic_two")
                .setShortLabel("动态圣诞树短标题")
                .setLongLabel("动态圣诞树长标题")
                .setIcon(Icon.createWithResource(this, R.mipmap.shengdanshu))
                .setIntents(new Intent[]{
                        new Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK),
                        //加该FLAG的目的是让MainActivity作为根activity，清空已有的任务
                })
                .build();
        //③、为ShortcutManager设置动态快捷方式集合
        if (scManager != null) {
            scManager.setDynamicShortcuts(Arrays.asList(scInfoOne, scInfoTwo));
        }

        //如果想为两个动态快捷方式进行排序，可执行下面的代码
        ShortcutInfo dynamicWebShortcut = new ShortcutInfo.Builder(this, "dynamic_one")
                .setRank(0)
                .build();
        ShortcutInfo dynamicActivityShortcut = new ShortcutInfo.Builder(this, "dynamic_two")
                .setRank(1)
                .build();

        //④、更新快捷方式集合
        if (scManager != null) {
            scManager.updateShortcuts(Arrays.asList(dynamicWebShortcut, dynamicActivityShortcut));
        }
    }
}
