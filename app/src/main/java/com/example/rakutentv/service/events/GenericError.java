package com.example.rakutentv.service.events;

import com.example.rakutentv.model.ErrorDTO;

public class GenericError {

    private ErrorDTO errorDTO;

    public GenericError(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }
}
