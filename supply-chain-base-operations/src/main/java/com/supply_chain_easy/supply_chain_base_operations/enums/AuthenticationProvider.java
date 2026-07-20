package com.supply_chain_easy.supply_chain_base_operations.enums;

public enum AuthenticationProvider {
    LOCAL,          // Email + Password

    GOOGLE,         // Google OAuth

    MICROSOFT,       // Microsoft / Azure AD

    APPLE,           // Apple Sign-In

    OKTA,            // Okta SSO

    SAML,            // Enterprise SAML SSO

    LDAP,            // LDAP / Active Directory

    AZURE_AD,        // Microsoft Azure Active Directory

    OIDC             // OpenID Connect
}
