package clinic.ljm.com.spaghetti;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.view.View;

/**
 * Created by Lucas on 11/21/2014.
 */
public class IncrementButtonListener implements View.OnClickListener {

    private ClinicHandler ch;
    private FragmentManager fm;

    public IncrementButtonListener(ClinicHandler ch, FragmentManager fm) {
        this.ch = ch;
        this.fm = fm;
    }

    @Override
    public void onClick(View v) {
        ClinicButton cb = (ClinicButton) v;
        Clinic c = ch.getClinicById(cb.getButtonID());
        int id = cb.getButtonID();
        int count = c.getCount();
        DialogFragment newFragment = ChangeClinicCountDialogFragment.newInstance(id, count);
        newFragment.show(fm, "this");
    }
}