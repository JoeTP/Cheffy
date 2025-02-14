package com.example.cheffy.repository.database;

import androidx.room.TypeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Converters {
    @TypeConverter
    public static List<String> fromString(String value) {
        return Arrays.asList(value.split(","));
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        return list.stream().collect(Collectors.joining(","));
    }
}
