package app.remote.com.gremote.CustomClass;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

public class CustomSwitchCompact extends android.support.v7.widget.SwitchCompat {
    private boolean mIgnoreCheckedChange = false;

    public CustomSwitchCompact(Context context) {
        super(context);
    }

    public CustomSwitchCompact(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSwitchCompact(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setOnCheckedChangeListener(final OnCheckedChangeListener listener) {
        super.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mIgnoreCheckedChange) {
                    return;
                }
                listener.onCheckedChanged(buttonView, isChecked);
            }
        });
    }

    public void setChecked(boolean checked, boolean notify) {
        mIgnoreCheckedChange = !notify;
        setChecked(checked);
        mIgnoreCheckedChange = false;
    }

}