package uk.co.oaktest.utils;

import uk.co.oaktest.constants.OperatingSystemType;

public class OperatingSystem {
    // Copied from https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/

    private static String operatingSystemName = System.getProperty("os.name").toLowerCase();

    public static OperatingSystemType getOS() {
        return getOS(operatingSystemName);
    }

    public static OperatingSystemType getOS(String possibleOperatingSystemName) {

        if (isWindows(possibleOperatingSystemName)) {
            return OperatingSystemType.WINDOWS;
        } else if (isMac(possibleOperatingSystemName)) {
            return OperatingSystemType.OSX;
        } else if (isUnix(possibleOperatingSystemName)) {
            return OperatingSystemType.LINUX;
        }
        return null;
    }

    public static boolean isWindows(String possibleOperatingSystemName) {

        return (possibleOperatingSystemName.contains("win"));

    }

    public static boolean isMac(String possibleOperatingSystemName) {

        return (possibleOperatingSystemName.contains("mac"));

    }

    public static boolean isUnix(String possibleOperatingSystemName) {

        return (possibleOperatingSystemName.contains("nix") || possibleOperatingSystemName.contains("nux") || possibleOperatingSystemName.contains("aix"));

    }

}
