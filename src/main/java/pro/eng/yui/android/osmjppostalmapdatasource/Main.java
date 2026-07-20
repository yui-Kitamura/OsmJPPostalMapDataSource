package pro.eng.yui.android.osmjppostalmapdatasource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        System.out.println("定期実行");
        try {
            Path pagesPath = Paths.get("pages", "index.html");
            if (Files.exists(pagesPath)) {
                String content = "<!DOCTYPE html>\n" +
                        "<html lang=\"ja\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>OsmJpPostaMapDataSource</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <h1>Last Updated: " + LocalDateTime.now() + "</h1>\n" +
                        "</body>\n" +
                        "</html>";
                Files.write(pagesPath, Collections.singletonList(content));
                System.out.println("pages/index.html を更新しました。");
            } else {
                System.err.println("pages/index.html が見つかりません。");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
