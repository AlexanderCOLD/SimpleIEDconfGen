package simpleIedGen;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Александр Холодов
 * @created 11.2020
 * @project SimpleIEDconfGen
 * @description
 */
public class ConfigUtils {


    public static String inputBlockTemplate =
                    "<GSE ldInst=\"lDevice1\" cbName=\"gcbBrake\">\n" +
                    "          <Address>\n" +
                    "            <P type=\"VLAN-ID\">001</P>\n" +
                    "            <P type=\"VLAN-PRIORITY\">4</P>\n" +
                    "            <P type=\"MAC-Address\">MAC-DST-BRSTATE-HERE</P>\n" +
                    "            <P type=\"APPID\">0001</P>\n" +
                    "            <P type=\"IFACE\">ens3</P>\n" +
                    "            <P type=\"GoCbRef\">ied1lDevice1/LLN0.gcbBreakerState</P>\n" +
                    "            <P type=\"GoID\">breakerStateID</P>\n" +
                    "            <P type=\"DatSet\">BreakerStateDS</P>\n" +
                    "          </Address>\n" +
                    "          <MinTime unit=\"s\" multiplier=\"m\">4</MinTime>\n" +
                    "          <MaxTime unit=\"s\" multiplier=\"m\">2000</MaxTime>\n" +
                    "\t\t</GSE>";

    public static String inputTemplate =
                    "<Inputs>\n" +
                    "              <ExtRef intAddr=\"ied1/lDevice1/CSWI1/Pos/ctlVal\" iedName=\"IED0\" ldInst=\"LD0\" lnClass=\"GGIO\" lnInst=\"1\" doName=\"do\" daName=\"da\" serviceType=\"GOOSE\" srcCBName=\"gcbBrake\" />\n" +
                    "\t\t\t</Inputs>\n";


    public static List<ConfigState> loadConfigs() throws Exception {
        List<ConfigState> configs = new ArrayList<>();

        File conf = new File("Конфиги.csv");
        List<String> confData = FileUtils.readLines(conf, Charset.forName("CP1251")).subList(11, 91);
        confData.forEach(s -> {
            String[] line = s.split(";");

            ConfigState state = new ConfigState();
            state.setName(line[3].trim());
            state.setVoltage(line[4].trim());
            state.setCurrent(line[5].trim());
            state.setCos(line[6].trim());
            state.setTripPoint(line[7].trim());

            state.setIp(line[8].trim());
            state.setMacSource(line[9].trim());
            state.setMacDstBreaker(line[10].trim());
            state.setMacDstTrip(line[11].trim());
            state.setMacInput(line[12].trim());
            state.setTag(line[13].trim());

            if(state.getMacInput().equals("-")) state.setMacInput(null);

            configs.add(state);
        });

        List<ConfigState> filled = configs.subList(0, 8);
        List<ConfigState> nonFilled = configs.subList(8, 80);

        int num = 0;
        for(ConfigState cs: nonFilled){
            ConfigState f = filled.get(num);
            cs.setName(f.getName());
            cs.setVoltage(f.getVoltage());
            cs.setCurrent(f.getCurrent());
            cs.setCos(f.getCos());
            cs.setTripPoint(f.getTripPoint());
            if(++num == 8) num = 0;
        }

        return configs;
    }


}
