package com.i2s.worfklow_api_final.util;


import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtil {

    public static List<String> getParameterTypes(Parameter[] parameters) {
        return Arrays.stream(parameters).map(parameter -> parameter.getType().getSimpleName()).collect(Collectors.toList());
    }
}
