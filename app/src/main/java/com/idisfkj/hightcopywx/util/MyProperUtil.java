package com.idisfkj.hightcopywx.util;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

public class MyProperUtil {
    private static Properties urlProps;
    public static Properties getProperties(Context c){
        Properties props = new Properties();
        try {
            //方法一：通过activity中的context攻取setting.properties的FileInputStream
            InputStream in = c.getAssets().open("settingConfig.properties");
            props.load(in);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        urlProps = props;
        return urlProps;
    }

}

