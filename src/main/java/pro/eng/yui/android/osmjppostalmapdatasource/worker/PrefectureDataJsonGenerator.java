package pro.eng.yui.android.osmjppostalmapdatasource.worker;

import pro.eng.yui.android.osmjppostalmapdatasource.Main;
import pro.eng.yui.oss.osm.lib.jppostalcore.JpPostalUtil;
import pro.eng.yui.oss.osm.lib.jppostalcore.types.OsmPoi;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrefectureDataJsonGenerator {

    public PrefectureDataJsonGenerator() {

    }
    
    public static class Result{
        private final int code;
        public int getPrefCode(){ return code; }
        private final LocalDate dataTimestamp;
        public LocalDate getDataTimestamp(){ return dataTimestamp; }
        private final Map<String, Object> data;
        public Map<String, Object> getJsonData(){ return data; }
        
        public Result(int prefCode, LocalDate timestamp, Map<String, Object> jsonData){
            this.code = prefCode;
            this.dataTimestamp = timestamp;
            this.data = jsonData;
        }
    }

    /** 
     * @param prefCode 都道府県コード
     * @param name 都道府県名 */
    public Result generate(int prefCode, String name) throws IOException {
        Map<String, Object> data = new HashMap<>();

        String query = 
                "area[\"boundary\"=\"administrative\"][\"admin_level\"=\"4\"][\"name\"=\""+ name +"\"]->.a;"+
                "(" +
                "  node(area.a)[\"amenity\"=\"post_box\"];" +
                "  nw(area.a)[\"amenity\"=\"post_office\"][!\"operator\"];" +
                "  nw(area.a)[\"amenity\"=\"post_office\"][\"operator\"=\"日本郵便\"];" +
                ");";
        List<OsmPoi> pois = JpPostalUtil.callOverpass(query, 3, 20);
        
        LocalDate timestamp = LocalDate.now(Main.JST);
        data.put("lastModified", timestamp);
        data.put("prefectureCode", prefCode);
        data.put("name", name);
        data.put("data", pois);
        
        return new Result(prefCode, timestamp, data);
    }
}

