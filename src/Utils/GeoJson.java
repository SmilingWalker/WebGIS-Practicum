package Utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.geotools.geojson.GeoJSON;

import java.util.ArrayList;
import java.util.HashMap;

public class GeoJson extends JSONObject {

    private ArrayList featureList;
    public GeoJson(){
        featureList = new ArrayList();
        this.put("type","FeatureCollection");
    }

    //加入要素
    public void AddFeature(String geom,HashMap<String,Object> properties){
        HashMap<Object, Object> feature = new HashMap<>();

        feature.put("geometry",JSONObject.parseObject(geom));
        feature.put("properties",properties);
        feature.put("type","Feature");
        featureList.add(feature);
    }

    @Override
    public String toString() {
        this.put("features",featureList);
        return super.toString();
    }

    public boolean isNull(){
        if(featureList.size()>0)
            return false;
        else
            return true;
    }
}
