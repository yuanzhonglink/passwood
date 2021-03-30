package com.ldap.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties(prefix="ldap")
@Component
@Validated
public class LdapProperties {

    @NotNull
    private String server;

    @NotNull
    private String root_dn;

    @NotNull
    private String root_user;

    @NotNull
    private String root_password;

    @NotNull
    private String[] pattern;
}
