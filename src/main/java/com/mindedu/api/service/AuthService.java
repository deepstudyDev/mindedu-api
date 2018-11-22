package com.mindedu.api.service;

import com.mindedu.api.define.datasource.AuthKeyDataSource;
import com.mindedu.api.util.ApiUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService extends ApiUtils {

    static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public boolean isCompanyApiAuth(String authKey) {
        logger.info(">>>>>>>>>>>>>>>>>>" + authKey);
        if ("".equals(authKey)) {
            return false;
        }
        return AuthKeyDataSource.getAuthKey(authKey);
    }
}
