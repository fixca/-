package me.fixca.weather.request;

import android.os.AsyncTask;

@SuppressWarnings("deprecation")
public class Request extends AsyncTask<Void, Void, Void> {

    IRequest iRequest;

    public Request(IRequest iRequest) {
        this.iRequest = iRequest;
    }


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(Void... voids) {
        iRequest.doInBackground();
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        
    }
}
