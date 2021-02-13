package entekBin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



/**
 * @author Александр Холодов
 * @created 11.2020
 * @project SimpleIEDconfGen
 * @description
 */
public class Main {


    public static void main(String[] args) throws Exception {
        File cfgDir = new File(entekGen.Main.dir.getAbsolutePath() + "\\Cfg");
        File configsDir = new File(entekGen.Main.dir.getAbsolutePath() + "\\Config"); configsDir.mkdirs();

        File cfg = Files.walk(cfgDir.toPath())
                .filter(p -> p.getFileName().toString().equals("cfg.xml"))
                .map(Path::toFile).findFirst().orElse(null);

        List<File> binaryList = Files.walk(cfgDir.toPath())
                .filter(p -> FilenameUtils.getExtension(p.getFileName().toString()).equals("bin"))
                .map(Path::toFile)
                .collect(Collectors.toList());

        HashMap<String, String> configs = parseCfg(cfg);

        for (File f: binaryList){
            String name = FilenameUtils.getBaseName(f.getName());
            String ip = configs.get(name);

            FileUtils.copyFile(f, new File(configsDir.getAbsolutePath() + "\\" + ip + "\\config.bin"));
            System.out.printf("Binary moved: %s %n", configsDir.getAbsolutePath() + "\\" + ip + "\\config.bin");
        }

    }


    private static final Pattern namePattern = Pattern.compile("<Name>(.+?)</Name>", Pattern.DOTALL);
    private static final Pattern idPattern = Pattern.compile("<StaticGUID>(.+?)</StaticGUID>", Pattern.DOTALL);


    private static HashMap<String, String> parseCfg(File cfg) throws IOException {
        String data = FileUtils.readFileToString(cfg, Charset.defaultCharset());

        Matcher nameMatcher = namePattern.matcher(data);
        Matcher idMatcher = idPattern.matcher(data);

        List<String> names = new ArrayList<>(), ips = new ArrayList<>();

        while (nameMatcher.find()) names.add(nameMatcher.group(1));
        while (idMatcher.find()) ips.add(idMatcher.group(1));

        HashMap<String, String> result = new HashMap<>();
        IntStream.range(0, names.size()).forEach(v ->{ result.put(ips.get(v), names.get(v).replace("KM_","").replace(":",".")); });

        return result;
    }

}
