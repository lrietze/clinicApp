package clinic.ljm.com.spaghetti;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class ClinicButton extends Button{
    //private String buttonName = "X";
    private int buttonID;

    public ClinicButton(Context context) {
        super(context);
    }

    public ClinicButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setButtonID(int v){
        this.buttonID = v;
    }

    public int getButtonID(){
        return buttonID;
    }
}

