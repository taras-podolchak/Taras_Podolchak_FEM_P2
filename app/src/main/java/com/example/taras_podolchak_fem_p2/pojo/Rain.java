package com.example.taras_podolchak_fem_p2.pojo;

import java.util.HashMap;
import java.util.Map;

public class Rain {

    private Double _3h;
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Double get3h() {
        return _3h;
    }

    public void set3h(Double _3h) {
        this._3h = _3h;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
