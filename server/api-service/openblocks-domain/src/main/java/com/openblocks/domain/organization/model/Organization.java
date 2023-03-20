package com.openblocks.domain.organization.model;

import static com.openblocks.infra.util.AssetUtils.toAssetPath;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.openblocks.domain.mongodb.AfterMongodbRead;
import com.openblocks.domain.mongodb.BeforeMongodbWrite;
import com.openblocks.domain.mongodb.MongodbInterceptorContext;
import com.openblocks.sdk.auth.AbstractAuthConfig;
import com.openblocks.sdk.models.HasIdAndAuditing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Document
public class Organization extends HasIdAndAuditing implements BeforeMongodbWrite, AfterMongodbRead {

    private static final OrganizationCommonSettings EMPTY_SETTINGS = new OrganizationCommonSettings();

    private String name;

    private Boolean isAutoGeneratedOrganization;

    private String contactName;

    private String contactEmail;

    private String contactPhoneNumber;

    @JsonIgnore
    private String logoAssetId;

    public String getLogoUrl() {
        return toAssetPath(logoAssetId);
    }

    private String source; // if it created from third party login

    private String thirdPartyCompanyId;

    private OrganizationState state;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OrganizationDomain organizationDomain;

    private OrganizationCommonSettings commonSettings;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public void afterMongodbRead(MongodbInterceptorContext context) {
        ofNullable(getOrganizationDomain())
                .ifPresent(domain -> domain.afterMongodbRead(context));
    }

    @Override
    public void beforeMongodbWrite(MongodbInterceptorContext context) {
        ofNullable(getOrganizationDomain())
                .ifPresent(domain -> domain.beforeMongodbWrite(context));
    }

    public OrganizationCommonSettings getCommonSettings() {
        return firstNonNull(commonSettings, EMPTY_SETTINGS);
    }

    public static class OrganizationCommonSettings extends HashMap<String, Object> {
        public static final String USER_EXTRA_TRANSFORMER = "userExtraTransformer";
        public static final String USER_EXTRA_TRANSFORMER_UPDATE_TIME = "userExtraTransformer_updateTime";

        // custom branding configs
        public static final String CUSTOM_BRANDING_KEY = "branding";
    }

    public long getCreateTime() {
        return createdAt != null ? createdAt.toEpochMilli() : 0;
    }

    public List<AbstractAuthConfig> getAuthConfigs() {
        return Optional.ofNullable(organizationDomain)
                .map(OrganizationDomain::getConfigs)
                .orElse(Collections.emptyList());
    }
}
