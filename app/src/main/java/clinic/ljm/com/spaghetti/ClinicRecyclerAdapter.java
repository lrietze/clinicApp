package clinic.ljm.com.spaghetti;

import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ClinicRecyclerAdapter extends RecyclerView.Adapter<ClinicRecyclerAdapter.ClinicViewHolder> {

    private ClinicHandler ch;
    private FragmentManager fm;

    public ClinicRecyclerAdapter(ClinicHandler ch, FragmentManager fm) {
        this.ch = ch;
        this.fm = fm;
    }

    @Override
    public int getItemCount() {
        return ch.getSize();
    }

    @Override
    public void onBindViewHolder(ClinicViewHolder viewHolder, int i) {
        Clinic clinic = ch.getByIndex(i);
        viewHolder.vName.setText(clinic.getName());
        viewHolder.vCount.setText(String.valueOf(clinic.getCount()));
        viewHolder.vCount.setButtonID(clinic.getId());
        viewHolder.vCount.setOnClickListener(new IncrementButtonListener(ch, fm));
    }

    @Override
    public ClinicViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.clinic_list, viewGroup, false);
        return new ClinicViewHolder(itemView);
    }

    public static class ClinicViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected ClinicButton vCount;

        public ClinicViewHolder(View v) {
            super(v);
            vName = (TextView) v.findViewById(R.id.list_name);
            vCount = (ClinicButton) v.findViewById(R.id.list_count);
        }
    }
}
