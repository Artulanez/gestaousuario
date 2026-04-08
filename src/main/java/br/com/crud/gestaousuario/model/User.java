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
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String nome;
    
    @Column(name = "password", nullable = false)
    private String senha;
    
    @Column(name = "email", nullable = false) // Removido o unique=true aqui
    private String email;

    @Column(name = "phone")
    private String telefone;
}
