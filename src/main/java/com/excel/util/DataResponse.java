package com.excel.util;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse {
    private List<String> errors;
    private String statusCode = "success";
    private Object data;

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error){
        this.statusCode = "error";
        if(errors == null)
            errors = new ArrayList<String>();
        errors.add(error);
    }

    public void setErrors(List<String> errors) {
        this.statusCode = "error";
        this.errors = errors;
    }

    public void succ(){
        this.statusCode = "success";
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
