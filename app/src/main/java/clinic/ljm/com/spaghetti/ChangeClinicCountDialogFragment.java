package clinic.ljm.com.spaghetti;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class ChangeClinicCountDialogFragment extends DialogFragment {

    private int mId;
    private int mCount;
    private int newCount;
    NumberPicker count;

    static ChangeClinicCountDialogFragment newInstance(int id, int oldCount) {
        ChangeClinicCountDialogFragment f = new ChangeClinicCountDialogFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putInt("count", oldCount);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DFragment", "CCCDF - onCreate");
        mCount = getArguments() != null ? getArguments().getInt("count") : 1;
        mId = getArguments() != null ? getArguments().getInt("id") : 1;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Log.d("DFragment", "CCCDF - onCreateDialog");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_count, null);

        builder.setView(dialogView)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        count = (NumberPicker) getDialog().findViewById(R.id.countPicker);
                        newCount = count.getValue();
                        Log.d("DFragment", "newCount is " + String.valueOf(newCount));
                        MainActivity activity = (MainActivity) getActivity();
                        activity.updateClinicCount(mId, newCount);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        count = (NumberPicker) dialogView.findViewById(R.id.countPicker);
        count.setMaxValue(65536);
        count.setMinValue(0);
        count.setValue(mCount);
        return builder.create();
    }

}
