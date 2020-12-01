package entekGen;

import javafx.util.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Александр Холодов
 * @created 11.2020
 * @project SimpleIEDconfGen
 * @description
 */
public class ProjectUtils {


    /** Сгенерировать главный файл проекта */
    public static String generateMain(List<Controller> controllers){

        StringBuilder kms = new StringBuilder();

        for (Controller c: controllers){
            kms.append(String.format(
                            "  <Controller>\n" +
                            "    <Settings>\n" +
                            "      <Signature>None-target</Signature>\n" +
                            "      <GroupName>None-target</GroupName>\n" +
                            "      <CntrlrCode>655369</CntrlrCode>\n" +
                            "      <Name>%s</Name>\n" +
                            "      <StaticGUID>%s</StaticGUID>\n" +
                            "    </Settings>\n" +
                            "  </Controller>\n",
            c.getName(), c.getFileName()));
        }

        return String.format(
                "<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n" +
                "<KLogicDocument IDE_Ver=\"5.0.0.61\">\n" +
                "  <GlobalScriptSettings/>\n" +
                "  <Alarm>\n" +
                "    <Use>False</Use>\n" +
                "    <Port>1</Port>\n" +
                "    <CheckModemPeriod>30</CheckModemPeriod>\n" +
                "    <TautBit>50</TautBit>\n" +
                "  </Alarm>\n" +
                "  <Info>\n" +
                "    <Use>False</Use>\n" +
                "    <Port>30293</Port>\n" +
                "  </Info>\n" +
                "  <Proxy>\n" +
                "    <Use>False</Use>\n" +
                "    <IP>127.0.0.1</IP>\n" +
                "    <Port>30294</Port>\n" +
                "  </Proxy>\n" +
                "  <EnComSrv>\n" +
                "    <IP>127.0.0.1</IP>\n" +
                "    <Port>28915</Port>\n" +
                "  </EnComSrv>\n" +
                "%s" +
                "</KLogicDocument>\n", kms);
    }

}
