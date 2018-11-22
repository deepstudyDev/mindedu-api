package com.mindedu.api.define.datasource;

public enum AuthKeyDataSource {
    FROEBEL("9kfla4mo73iq561r0u6oqba98n");


    String authKeyValue;

    AuthKeyDataSource(String authKeyValue) {
        this.authKeyValue = authKeyValue;
    }

    public static boolean getAuthKey(String authKeyValue) {
        for (AuthKeyDataSource dataSource : AuthKeyDataSource.values()) {
            if (authKeyValue.equals(dataSource.authKeyValue)) {
                return true;
            }
        }
        return false;
    }
}
