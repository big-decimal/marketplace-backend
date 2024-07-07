package com.marketplace.api.auth;

import org.mapstruct.Mapper;

import com.marketplace.api.CommonDataMapper;
import com.marketplace.domain.auth.AuthResult;

@Mapper(componentModel = "spring")
public interface AuthDataMappter extends CommonDataMapper {

	AuthResultDTO map(AuthResult source);
	
}
