package clinic.ljm.com.spaghetti;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class AddClinicDialog extends DialogFragment {

    private String name;
    private int cid;
    private Location loc;
    private Button submitButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Log.d("GPS", "Test");

        // Need to pass context to the GPS fnctn as per the Geocoder's requirements
        GPSCoordinates gps = new GPSCoordinates(this);

        loc = gps.getCurrentLocation();
        Log.d("GPS", ">" + loc);

        View dialogView = inflater.inflate(R.layout.dialog_add_clinic, null);
        builder.setView(dialogView)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        LatLng point = new LatLng(loc.getLatitude(),
                                loc.getLongitude());

                        Clinic c = new Clinic(name, cid, 0, point);
                        MainActivity activity = (MainActivity) getActivity();
                        activity.addToClinicHandler(c);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        name = null;
        cid = -1;
        ((EditText) dialogView.findViewById(R.id.clinic_name)).addTextChangedListener(nameWatch);
        ((EditText) dialogView.findViewById(R.id.clinic_id)).addTextChangedListener(idWatch);

        AlertDialog dialog = builder.create();
        dialog.show();
        
        submitButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setEnabled(false);

        if (loc == null) {
            dialogView.findViewById(R.id.gpsSpinner).setVisibility(View.VISIBLE);
            dialogView.findViewById(R.id.gpsWaitMessage).setVisibility(View.VISIBLE);
            dialogView.findViewById(R.id.addressDisplay).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) dialogView.findViewById(R.id.addressDisplay)).setText(gps.getAddress(loc));
        }
        return dialog;
    }

    public void updateAddress(String address) {
        Dialog dialog = getDialog();
        dialog.findViewById(R.id.gpsSpinner).setVisibility(View.INVISIBLE);
        dialog.findViewById(R.id.gpsWaitMessage).setVisibility(View.INVISIBLE);


        TextView addrDisplay = (TextView) dialog.findViewById(R.id.addressDisplay);
        addrDisplay.setVisibility(View.VISIBLE);
        addrDisplay.setText(address);
        if (name != null && cid != -1)
            submitButton.setEnabled(true);
    }

    private TextWatcher nameWatch = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                name = null;
                submitButton.setEnabled(false);
            } else {
                name = s.toString().trim();
                if (cid != -1 && loc != null)
                    submitButton.setEnabled(true);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    private TextWatcher idWatch = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                cid = -1;
                submitButton.setEnabled(false);
            } else {
                try {
                    cid = Integer.parseInt(s.toString());
                    if (name != null && loc != null)
                        submitButton.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };
}
