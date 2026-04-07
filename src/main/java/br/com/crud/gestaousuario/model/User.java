package br.com.crud.gestaousuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_USER")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String nome;
    
    @Column(name = "password", nullable = false)
    private String senha;
    
    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String telefone;
}
