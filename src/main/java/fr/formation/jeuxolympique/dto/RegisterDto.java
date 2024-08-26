package fr.formation.jeuxolympique.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDto {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private  String lastName;

    @NotEmpty
    @Email
    private String email;

    private String phone;

    private String address;

    @Size(min=6, message = "Minimum Password lenght is 6 characters")
    private String password;

    private String ConfirmPassword;

}
