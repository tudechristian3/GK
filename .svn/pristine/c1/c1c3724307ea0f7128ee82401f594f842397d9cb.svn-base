package com.goodkredit.myapplication.common;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import ph.com.voyagerinnovation.freenet.applink.FreenetSdk;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionEvent;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionListener;

/**
 * Created by user on 8/22/2016.
 */
public class CreateSession extends AsyncTask<String, Void, String>  {

    CommonFunctions cf;
    CommonVariables cv;
    Context context;

    public CreateSession(Context activity) {

        this.context = activity;

    }

    QueryListener myListener;

    public void setQueryListener(QueryListener myListener) {
        this.myListener = myListener;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //  showDialog(mcontext,"","Loging In. Please wait ...",false);
    }

    @Override
    protected String doInBackground(String... urls) {
        String json = "";
        try {
            String imei = urls[1];
            String userid = urls[2];

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("imei", imei);
            jsonObject.accumulate("userid", userid);

            //convert JSONObject to JSON to String
            json = jsonObject.toString();

        } catch (Exception e) {
            json = null;
        }

        return cf.POST(urls[0], json);

    }

    // 3. onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        try {
            myListener.QuerySuccessFul(result);

        } catch (Exception e) {
        }
    }

    public interface QueryListener {
        void QuerySuccessFul(String data);
    }


}


