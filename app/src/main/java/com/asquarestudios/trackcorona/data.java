package com.asquarestudios.trackcorona;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.asquarestudios.trackcorona.controller.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class data
{
    private  String url = "https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true";
    dataVariable object_dataVariable = new dataVariable();

    public  dataVariable getTime(final AsynCall callback)
    {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {


                    object_dataVariable.setActiveCases(response.getString("activeCases"));
                    object_dataVariable.setDeaths(response.getString("deaths"));
                    object_dataVariable.setRecovered(response.getString("recovered"));
                    object_dataVariable.setTotalCases(response.getString("totalCases"));
                    object_dataVariable.setSourceUrl(response.getString("sourceUrl"));
                    object_dataVariable.setLatestUpdate(response.getString("lastUpdatedAtApify"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(callback!=null)
                {
                    callback.processFinished(object_dataVariable);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        return  object_dataVariable;
    }
}
