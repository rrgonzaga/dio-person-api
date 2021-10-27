package one.digitalinnovation.personapi.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonDTO {


    private Long id;

    @EqualsAndHashCode.Include
    @NotEmpty
    @Size(min=2,max = 100)
    private String firstName;

    @NotEmpty
    @Size(min=2, max = 100)
    private String lastName;

    @EqualsAndHashCode.Include
    @NotEmpty
    @CPF
    private String cpf;
        
    private String birthDate;

    @Valid
    @NotEmpty
    private List<PhoneDTO> phones;
}
