package com.comodif.challenge.configuration.dtoConverter;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DtoConverter implements DtoConverterService{
    private ModelMapper modelMapper;

    @Override
    public <S, T> List<T> dtoConverter(List<S> s, Class<T> targetClass) {
        return s.stream().map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());

    }
    @Override
    public <T> T dtoClassConverter(Object source, Class<T> baseClass) {
        return modelMapper.map(source, baseClass);

    }
}