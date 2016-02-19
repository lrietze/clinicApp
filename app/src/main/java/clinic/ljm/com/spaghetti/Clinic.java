package clinic.ljm.com.spaghetti;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

public class Clinic implements IJSONAble, Parcelable {

    private String name;
    private int clinicId;
    private int count;
    private LatLng point;

    public Clinic(String name, int clinicId, int count, LatLng point){
        this.name = name;
        this.clinicId = clinicId;
        this.count = count;
        this.point = point;
    }

    public Clinic(JSONObject o) {
        fromJSON(o);
    }

    public void fromJSON(JSONObject o) {
        try {
            clinicId = o.getInt("_id");
            count = o.getInt("count");
            name = o.getString("name");

            JSONObject location = o.getJSONObject("location");
            point = new LatLng(location.getDouble("latitude"),
                    location.getDouble("longitude"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        try {
            JSONObject o = new JSONObject();
            o.put("_id", clinicId);
            o.put("name", name);
            o.put("count", count);

            JSONObject location = new JSONObject();
            location.put("latitude", point.latitude);
            location.put("longitude", point.longitude);

            o.put("location", location);
            return o;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getId() {
        return clinicId;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public LatLng getPoint() { return point; }

    public void setCount(int num) {
        this.count = num;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeInt(clinicId);
        out.writeInt(count);
        out.writeDouble(point.latitude);
        out.writeDouble(point.longitude);
    }

    public static final Parcelable.Creator<Clinic> CREATOR = new Parcelable.Creator<Clinic>() {
        public Clinic createFromParcel(Parcel in) {
            return new Clinic(in);
        }

        public Clinic[] newArray(int size) {
            return new Clinic[size];
        }
    };

    private Clinic(Parcel in) {
        name = in.readString();
        clinicId = in.readInt();
        count = in.readInt();
        double lat = in.readDouble();
        double lon = in.readDouble();
        point = new LatLng(lat, lon);
    }
}
