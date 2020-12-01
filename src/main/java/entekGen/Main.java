package entekGen;

import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Александр Холодов
 * @created 11.2020
 * @project SimpleIEDconfGen
 * @description
 */
public class Main {

    /**
     * IP контроллеров:           192.168.204.12 - 192.168.213.12
     *
     * IP устройств Simple IED:   192.168.204.3 - 192.168.204.10
     *                                  -               -
     *                            192.168.213.3 - 192.168.213.10
     */


    /* Директория в которой необходимо изменить файлы */
    public static final File dir = new File("C:\\Users\\ALEXCOLD\\Desktop\\Entek_final\\allSubstations (gen)");

    /* Название контроллера */
    private static final String kmName = "ControllerName";

    /* IP адрес контроллера (в целом) */
    private static final String kmAddr = "<IPAddress>127.0.0.1</IPAddress>";

    /* IP и PORT каждого УСО (Simple IED) */
    private static final String devAddr = "Id1=\"127.0.0.1\" Id2=\"103\"";

    /* Название каждого УСО */
    private static final String devName = "<Name>SimpleIED";




    public static void main(String[] args) throws Exception {

        File prjFile = Files.walk(dir.toPath())
                .filter(p -> FilenameUtils.getExtension(p.getFileName().toString()).equals("enl"))
                .filter(p -> !p.getParent().getFileName().toString().contains("backup"))
                .filter(p -> !p.getParent().getFileName().toString().contains("Cfg"))
                .map(Path::toFile).findFirst().orElse(null);

        List<File> configs = Files.walk(dir.toPath())
                .filter(p -> FilenameUtils.getExtension(p.getFileName().toString()).equals("xml"))
                .filter(p -> !p.getParent().getFileName().toString().contains("backup"))
                .filter(p -> !p.getParent().getFileName().toString().contains("Cfg"))
                .map(Path::toFile)
                .collect(Collectors.toList());

        int kmNum = 0; // Начинается с 204 адреса

        List<Controller> controllers = new ArrayList<>(); // key - old, value - new

        for(File file: configs){
            System.out.printf("Конфиг %s %n", file.getAbsolutePath());

            Controller controller = new Controller();
            controller.setFileName(FilenameUtils.getBaseName(file.getName()));
            controllers.add(controller);

            List<String> confData = FileUtils.readLines(file, Charset.forName("CP1251")), result = new ArrayList<>();

            int devCount = 0;

            int subnetwork = 204 + kmNum++;
            String kmIp = String.format("192.168.%s.12", subnetwork);
            String rplDevAddr = String.format("Id1=\"192.168.%s.%s\" Id2=\"102\"", subnetwork, (3 + devCount) ); // При обнаружении след. IED - меняется

            /* Перебор файла и замена нужных элементов */
            for(String line: confData) {

                /* Номер контроллера */
                if(line.contains("SystNum")) {
                    line = String.format("      <SystNum>%s</SystNum>", kmNum);
                    controller.setNumber(kmNum);
                }

                /* Замена имени контроллера */
                if (line.contains(kmName)) {
                    String name = "KM_" + kmIp.replace(".", ":");
                    controller.setName(name);
                    System.out.printf("   '-->  Название КМ:    %s %n", name.trim());
                    line = String.format("      <Name>%s</Name>", name);
                }

                /* Замена IP адреса контроллера */
                if (line.contains(kmAddr)) {
                    String addr = String.format("        <IPAddress>%s</IPAddress>", kmIp);
                    System.out.printf("   '-->  Адрес КМ:       %s %n", addr.trim());
                    line = addr;
                }

                /* Создание нового адреса для УСО */
                if(line.contains(devName))
                    rplDevAddr = String.format("Id1=\"192.168.%s.%s\" Id2=\"102\"", subnetwork, ( 3 + devCount++) );

                /* Замена адреса УСО */
                if(line.contains(devAddr)) {
                    System.out.printf("   '-->  Адресс УСО КМ:  %s %n", rplDevAddr);
                    line = line.replace(devAddr, rplDevAddr);
                }

                result.add(line);
            }


            /* Перезапись конфига */
            FileUtils.writeLines(new File(file.getAbsolutePath()), "CP1251" , result);
            System.out.printf("Конфиг изменен:  %s %n%n", file.getAbsolutePath());
        }

        /* Перезапись файла проекта */
        String prjData = ProjectUtils.generateMain(controllers);
        FileUtils.write(prjFile, prjData, Charset.forName("CP1251"));
        System.out.println("Main project file changed");
    }

}
