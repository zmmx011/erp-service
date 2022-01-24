package com.invenia.erpservice.config;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.modelmapper.ModelMapper;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.modelmapper.module.jsr310.Jsr310ModuleConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    Jsr310ModuleConfig config = Jsr310ModuleConfig.builder()
        .dateTimeFormatter(DateTimeFormatter.ISO_INSTANT)
        .dateFormatter(DateTimeFormatter.ISO_INSTANT)
        .zoneId(ZoneOffset.UTC)
        .build();
    modelMapper.registerModule(new Jsr310Module(config));
    return modelMapper;
  }
}
