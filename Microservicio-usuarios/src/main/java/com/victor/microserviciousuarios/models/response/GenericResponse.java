package com.victor.microserviciousuarios.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;


public class GenericResponse<T> extends AbstractResponse<T> {

    private static final long serialVersionUID = 1L;

    public GenericResponse(){}

    public GenericResponse(T data, String msg) {
        super(data, msg);
    }
    public GenericResponse(T data, boolean success, String msg) {
        super(data, success, msg);
    }
    public GenericResponse(boolean success, String msg) {
        super(success, msg);
    }

}
