package cn.hgy;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ss.usermodel.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author guoyu.huang
 * @since 2021-01-03
 */
public class Main {

    public static void main(String[] args) throws IOException {
        // 读取模板
        String path = "D:\\application\\";
        String templateFilename = args[0];

        String targetPath = path + System.currentTimeMillis() + ".doc";
        File file = new File(targetPath);
        FileUtil.copy(new File(path + templateFilename), file, true);

        InputStream is = new FileInputStream(targetPath);
        HWPFDocument doc = new HWPFDocument(is);
        Range range = doc.getRange();

        for(int i = 1; i < args.length; i++){
            range.replaceText("${"+ i +"}", args[i]);
        }
        OutputStream os = new FileOutputStream(targetPath);
        doc.write(os);

        os.close();
        is.close();
    }
}
