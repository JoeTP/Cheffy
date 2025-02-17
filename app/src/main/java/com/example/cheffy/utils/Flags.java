package com.example.cheffy.utils;


public enum Flags {
    AMERICAN("American", "US"),
    BRITISH("British", "GB"),
    CANADIAN("Canadian", "CA"),
    CHINESE("Chinese", "CN"),
    CROATIAN("Croatian", "HR"),
    DUTCH("Dutch", "NL"),
    EGYPTIAN("Egyptian", "EG"),
    FILIPINO("Filipino", "PH"),
    FRENCH("French", "FR"),
    GREEK("Greek", "GR"),
    INDIAN("Indian", "IN"),
    IRISH("Irish", "IE"),
    ITALIAN("Italian", "IT"),
    JAMAICAN("Jamaican", "JM"),
    JAPANESE("Japanese", "JP"),
    KENYAN("Kenyan", "KE"),
    MALAYSIAN("Malaysian", "MY"),
    MEXICAN("Mexican", "MX"),
    MOROCCAN("Moroccan", "MA"),
    POLISH("Polish", "PL"),
    PORTUGUESE("Portuguese", "PT"),
    RUSSIAN("Russian", "RU"),
    SPANISH("Spanish", "ES"),
    THAI("Thai", "TH"),
    TUNISIAN("Tunisian", "TN"),
    TURKISH("Turkish", "TR"),
    UKRAINIAN("Ukrainian", "UA"),
    URUGUAYAN("Uruguayan", "UY"),
    VIETNAMESE("Vietnamese", "VN"),
    UNKNOWN("Unknown", null);
    private final String areaName;
    private final String countryCode;
    Flags(String areaName, String countryCode) {
        this.areaName = areaName;
        this.countryCode = countryCode;
    }
    public static String getFlagURL(String name) {
        for (Flags country : values()) {
            if (country.areaName.equalsIgnoreCase(name)) {
                return country.countryCode != null
                        ? "https://www.themealdb.com/images/icons/flags/big/64/" + country.countryCode + ".png"
                        : null;
            }
        }
        return null;
    }
}