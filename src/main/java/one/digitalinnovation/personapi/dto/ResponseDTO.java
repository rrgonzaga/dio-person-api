package one.digitalinnovation.personapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResponseDTO {

    private String message;
    private List<String> errors;
    private Object data;

    public List<String> getErros() {
        if(this.errors == null) {
            this.errors = new ArrayList<>();
        }
        return errors;
    }


}
