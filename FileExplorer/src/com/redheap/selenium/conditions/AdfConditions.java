package com.redheap.selenium.conditions;


public abstract class AdfConditions {

    private static final ClientSynchedWithServer CLIENT_SYNCHED_WITH_SERVER = new ClientSynchedWithServer();

    private AdfConditions() {
    }

    public static ClientSynchedWithServer clientSynchedWithServer() {
        return CLIENT_SYNCHED_WITH_SERVER;
    }

}
