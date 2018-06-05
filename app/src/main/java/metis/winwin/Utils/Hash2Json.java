package metis.winwin.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tambora on 27/03/2017.
 */
public class Hash2Json {

    public static String convert(Map map) {

        String data = null;

        try {

            JSONObject obj = new JSONObject();
            JSONObject main = new JSONObject();
            Set set = map.keySet();

            Iterator iter = set.iterator();

            while (iter.hasNext()) {
                String key = (String) iter.next();
                obj.accumulate(key, map.get(key));
            }
            main.accumulate("data", obj);

            data = main.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static String convertSingle(HashMap<String, String> dataMap) {


        JSONObject obj = new JSONObject(dataMap);


        return obj.toString();
    }

    public static String convertv2(ArrayList<HashMap<String, String>> dataMap) {


        List<JSONObject> jsonObj = new ArrayList<JSONObject>();

        for (HashMap<String, String> data : dataMap) {
            JSONObject obj = new JSONObject(data);
            jsonObj.add(obj);
        }

        JSONArray test = new JSONArray(jsonObj);

        return test.toString();
    }

}
