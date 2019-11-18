package com.navercorp.navercodelab;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class NaverAppScheme {


    public void startNaverApp (Activity activity) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("naversearchapp://"));
        activity.startActivity(intent);


        //String link = <a href="Intent://#Intent;scheme=naversearchapp;package=com.nhn.anroid.search;end">
    }



}
