package com.firsthib.entity;

import com.firsthib.converter.BirthdayConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@Embeddable
public class PersonalInfo implements Serializable {

    private String firstname;
    private String lastname;
    @Convert(converter =BirthdayConverter.class)
    @Column(name  = "birth_date")
    private Birthday birthDate;
}
