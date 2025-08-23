package it.marcoberri.fasturl.data.entity;

import java.util.ArrayList;
import java.util.List;

public class UAgent {

    // "versionNumber\" : { \"extension\" : \"_13\" , \"groups\" : [ \"1\" , \"6\" , \"0\"]}} , \"producer\" : \"Sun Microsystems, Inc.\" , \"producerUrl\" : \"http://www.sun.com/\" , \"type\" : \"LIBRARY\" , \"typeName\" : \"Library\" , \"url\" : \"http://www.sun.com/java/\" , \"versionNumber\" : { \"extension\" : \"_13\" , \"groups\" : [ \"1\" , \"6\" , \"0\"]}}"
    public static class OS {

        public String family;
        public String familyName;
        public String icon;
        public String name;
        public String producer;
        public String producerUrl;
        public String url;
        public VN versionNumber;

        /**
         * @return the versionNumber
         */
        public VN getVersionNumber() {
            if (versionNumber == null)
                versionNumber = new VN();
            return versionNumber;
        }

    }

    public static class VN {

        public String extension;
        public List<String> groups;
        public String minor;
        public String major;

        /**
         * @return the groups
         */
        public List<String> getGroups() {
            if (this.groups == null)
                groups = new ArrayList<String>();
            return groups;
        }


    }

    public String family;
    public String icon;
    public String name;
    public OS operatingSystem = new OS();
    public String producer;
    public String producerUrl;

    public String url;

    public String typeName;

    /**
     * @return the operatingSystem
     */
    public OS getOperatingSystem() {
        if (operatingSystem == null)
            operatingSystem = new OS();
        return operatingSystem;
    }

}