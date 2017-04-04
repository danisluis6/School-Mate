package com.example.enclaveit.schoolmateapp.libraries;

import android.content.Context;

import com.example.enclaveit.schoolmateapp.R;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;

/**
 * Created by enclaveit on 22/03/2017.
 */

public class PubNubClass {
    PubNub pubNub;
    public PubNubClass(Context context){
        PNConfiguration pnConfiguration = new PNConfiguration();
            pnConfiguration.setPublishKey(context.getResources().getString(R.string.publish_key));
            pnConfiguration.setSubscribeKey(context.getResources().getString(R.string.subscribe_key));
            pnConfiguration.setAuthKey(context.getResources().getString(R.string.auth_key));
            pnConfiguration.setSecure(true);
        this.pubNub = new PubNub(pnConfiguration);
    }
    public PubNub getPubNub(){  return this.pubNub; }
}
