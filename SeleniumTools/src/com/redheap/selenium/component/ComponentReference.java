package com.redheap.selenium.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.openqa.selenium.WebDriver;

public class ComponentReference {

    private final String clientid;
    private final String componentType;

    public ComponentReference(String clientid, String componentType) {
        this.clientid = clientid;
        this.componentType = componentType;
    }

    public String getClientid() {
        return clientid;
    }

    public String getComponentType() {
        return componentType;
    }

    public <C extends AdfComponent> C findComponent(WebDriver driver) {
        return AdfComponent.forClientId(driver, clientid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("clientid", clientid).append("componentType", componentType).build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(clientid).append(componentType).build();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ComponentReference other = (ComponentReference) obj;
        return new EqualsBuilder().append(this.clientid, other.clientid).append(this.componentType,
                                                                                other.componentType).build();
    }

}
