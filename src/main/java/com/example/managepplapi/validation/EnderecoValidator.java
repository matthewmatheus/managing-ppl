package com.example.managepplapi.validation;

import com.example.managepplapi.entities.Endereco;
import com.example.managepplapi.entities.Pessoa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnderecoValidator implements ConstraintValidator<ValidEnderecos, Pessoa> {


    @Override
    public boolean isValid(Pessoa pessoa, ConstraintValidatorContext context) {

        if (pessoa.getEnderecos().size() <= 1) {
            for (Endereco endereco : pessoa.getEnderecos()) {
                if (!endereco.getPrincipal().booleanValue() == true) {
                    return false;
                }
            }
            return true;
        } else {
            int count = 0;
            for (Endereco endereco : pessoa.getEnderecos()) {
                if (endereco.getPrincipal().booleanValue() == true) {
                    count++;
                }
            }
            return count == 1;
        }
    }
}
