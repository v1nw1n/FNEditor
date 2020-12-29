package test;

import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vincent
 */
public class ReadFileAttr {
    public static void main(String[] args) throws IOException {

       Path path = Paths.get("D:\\filetest\\dir2\\bar");
       BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class,LinkOption.NOFOLLOW_LINKS);
        System.out.println("size:"+attributes.size());
        System.out.println("lmt:"+attributes.lastModifiedTime());
        System.out.println("ct:"+attributes.creationTime());
        System.out.println( Pattern.matches("^[-\\+]?[\\d]*$","5ss"));
    }
}
