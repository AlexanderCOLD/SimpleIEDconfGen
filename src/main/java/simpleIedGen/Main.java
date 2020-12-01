package simpleIedGen;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

/**
 * @author Александр Холодов
 * @created 11.2020
 * @project SimpleIEDconfGen
 * @description
 */
public class Main {


    private static final File outputDir = new File("Configs");
    private static final File archivesDir = new File("Archives");
    private static final File jarFile = new File("C:\\Users\\ALEXCOLD\\IdeaProjects\\VIED\\target\\VIED-1.0-SNAPSHOT-shaded.jar");


    public static void main(String[] args) throws Exception {
        FileUtils.deleteDirectory(outputDir); if(!outputDir.exists()) outputDir.mkdirs();
        FileUtils.deleteDirectory(archivesDir); if(!archivesDir.exists()) archivesDir.mkdirs();

        List<ConfigState> conf = ConfigUtils.loadConfigs();
        String template = FileUtils.readFileToString(new File("Template.cid"), Charset.defaultCharset());

        for(ConfigState state: conf){

            /* Редактирование конфига */
            String config = template;
            config = config.replace("Name-HERE", state.getName());
            config = config.replace("Voltage-HERE", state.getVoltage().replace(",", "."));
            config = config.replace("Current-HERE", state.getCurrent().replace(",", "."));
            config = config.replace("Cos-Here", state.getCos().replace(",", "."));
            config = config.replace("TripPoint-HERE", state.getTripPoint().replace(",", "."));

            config = config.replace("IPADDR-HERE", state.getIp());
            config = config.replace("MAC-SRC-HERE", state.getMacSource());
            config = config.replace("MAC-DST-BRSTATE-HERE", state.getMacDstBreaker());
            config = config.replace("MAC-DST-TRIP-HERE", state.getMacDstTrip());

            String block = "", input = "";
            if(state.getMacInput() != null) {
                block = ConfigUtils.inputBlockTemplate.replace("MAC-DST-BRSTATE-HERE", state.getMacInput());
                input = ConfigUtils.inputTemplate;
            }
            config = config.replace("INPUTS-HERE", input);
            config = config.replace("INPUT-BLOCK-HERE", block);


            /* Запись конфиг файлов */

            /* Создание папки */
            File outDir = new File(outputDir.getAbsolutePath() + "\\" + state.getIp()); outDir.mkdirs(); // Папка с нужным Ip

            /* Запись конфига */
            File outFile = new File(outDir.getAbsolutePath() + "\\config.cid");
            FileUtils.writeStringToFile(outFile, config, Charset.defaultCharset());
            System.out.println(outFile.getAbsolutePath() + "    - generated");

            /* Копирование Jar файла */
            File outJarFile = new File(outDir.getAbsolutePath() + "\\SIED-2.3.jar");
            FileUtils.copyFile(jarFile, outJarFile);
            System.out.println(outJarFile.getAbsolutePath() + "    - copied");

            /* Архивация */
            compress(outDir, state.getTag());

            System.out.println(" ");
        }

        System.out.print("DONE");
    }

    /**
     * Архивация в xz
     * @param dir - папка которую надо архивировать
     * @param tag - Выходной архив
     */
    private static void compress(File dir, String tag) throws Exception {

        /* Архивация в tar */
        File outTarArchive = new File(dir.getParent() + "\\" + tag + ".tar");
        TarArchiveOutputStream tarArchive = new TarArchiveOutputStream(new FileOutputStream(outTarArchive));

        Files.walk(dir.toPath()).forEach(p -> {
            File file = p.toFile();
            if (!file.isDirectory()) {
                TarArchiveEntry entry = new TarArchiveEntry(file, tag + "\\" + file.getName());
                try (FileInputStream fis = new FileInputStream(file)) {
                    tarArchive.putArchiveEntry(entry);
                    IOUtils.copy(fis, tarArchive);
                    tarArchive.closeArchiveEntry();
                } catch (IOException e) { e.printStackTrace(); }
            }
        });
        tarArchive.finish();

        /* Архивация в xz */
        File outXZArchive = new File(archivesDir.getAbsolutePath() + "\\" + tag + ".tar.xz");
        try (XZCompressorOutputStream gzipOutput = new XZCompressorOutputStream(new FileOutputStream(outXZArchive))) {
            gzipOutput.write(Files.readAllBytes(outTarArchive.toPath()));
            gzipOutput.finish();
            System.out.println(outXZArchive.getAbsolutePath() + "    - compressed");
        } catch (IOException e) { e.printStackTrace(); }
    }
}
