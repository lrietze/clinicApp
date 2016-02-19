package clinic.ljm.com.spaghetti;

import org.json.JSONObject;

public interface IJSONAble {
    public void fromJSON(JSONObject o);
    public JSONObject toJSON();
}
