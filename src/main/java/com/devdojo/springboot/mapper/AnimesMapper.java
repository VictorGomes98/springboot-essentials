package com.devdojo.springboot.mapper;

import com.devdojo.springboot.domain.Animes;
import com.devdojo.springboot.dto.AnimePostRequestBody;
import com.devdojo.springboot.dto.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimesMapper {
    public static final AnimesMapper INSTACE = Mappers.getMapper(AnimesMapper.class);
    public abstract Animes toAnimes(AnimePostRequestBody animePostRequestBody);
    public abstract Animes toAnimes(AnimePutRequestBody animePutRequestBody);
}
