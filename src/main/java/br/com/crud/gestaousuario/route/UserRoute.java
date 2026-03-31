package br.com.crud.gestaousuario.route;

import br.com.crud.gestaousuario.model.User;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class UserRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json);

        rest("/users")
            .get("/")
                .description("Listar todos os usuários")
                .to("bean:userService?method=list")
            
            .get("/{id}")
                .description("Obter um usuário pelo ID")
                .to("bean:userService?method=get(${header.id})")
            
            .post("/")
                .description("Criar um novo usuário")
                .type(User.class)
                .to("bean:userService?method=create")
            
            .put("/{id}")
                .description("Atualizar um usuário existente")
                .type(User.class)
                .to("bean:userService?method=update(${header.id}, ${body})")
            
            .delete("/{id}")
                .description("Deletar um usuário")
                .to("bean:userService?method=delete(${header.id})");
    }
}
