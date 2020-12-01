package simpleIedGen;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Александр Холодов
 * @created 11.2020
 * @project SimpleIEDconfGen
 * @description
 */
@Getter @Setter
public class ConfigState {

    /** Название устройства */
    private String name;

    private String voltage;
    private String current;
    private String cos;
    private String tripPoint;

    private String ip;
    private String macSource;
    private String macDstBreaker;
    private String macDstTrip;
    private String macInput; // Если отсутствует - без приема Goose

    private String tag;

}
