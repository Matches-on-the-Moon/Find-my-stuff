package com.motm.activities;

import android.app.Activity;
import android.os.Bundle;
import com.motm.R;

public class ViewItemActivity extends Activity {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.item_view);        
    }
}
