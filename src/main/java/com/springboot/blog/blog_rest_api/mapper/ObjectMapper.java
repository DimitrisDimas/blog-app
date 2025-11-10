package com.springboot.blog.blog_rest_api.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

//Similar with ModelMapper
public class ObjectMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {

        List<D> destinationObjects = new ArrayList<D>();
        for (Object o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}

/*
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

public class MapperUtils {

    private static final ModelMapper mapper = new ModelMapper();

    public static <O, D> D map(O origin, Class<D> destinationType) {
        return mapper.map(origin, destinationType);
    }

    public static <O, D> List<D> mapList(List<O> origin, Class<D> destinationType) {
        return origin.stream()
                     .map(o -> mapper.map(o, destinationType))
                     .collect(Collectors.toList());
    }
}
 */