package com.i2s.worfklow_api_final.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "parameters")
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,name = "parameter_name")
    private String parameterName;

    @Column(nullable = false,name = "parameter_type")
        private String parameterType;

    public Parameter() {
    }


}
