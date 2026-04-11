package br.com.crud.gestaousuario.route;

import br.com.crud.gestaousuario.model.User;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserRoute extends RouteBuilder {

    @Value("${activemq.topic}")
    private String activemqTopic;

    @Override
    public void configure() throws Exception {
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "API de Gestão de Usuários")
            .apiProperty("api.version", "1.0.0");

        rest("/users")
            .tag("Usuários")
            
            .get()
                .description("Listar todos os usuários")
                .outType(User[].class)
                .to("direct:listUsers")
            
            .get("/{id}")
                .description("Obter um usuário pelo ID")
                .param().name("id").type(RestParamType.path).description("O ID do usuário").dataType("integer").endParam()
                .outType(User.class)
                .to("direct:getUser")
            
            .post()
                .description("Criar um novo usuário")
                .type(User.class)
                .outType(User.class)
                .to("direct:createUser")
            
            .put("/{id}")
                .description("Atualizar um usuário existente")
                .type(User.class)
                .param().name("id").type(RestParamType.path).description("O ID do usuário").dataType("integer").endParam()
                .outType(User.class)
                .to("direct:updateUser")
            
            .delete("/{id}")
                .description("Deletar um usuário")
                .param().name("id").type(RestParamType.path).description("O ID do usuário").dataType("integer").endParam()
                .to("direct:deleteUser");

        // Implementação das rotas
        
        from("direct:listUsers")
            .to("bean:userService?method=list");

        from("direct:getUser")
            .to("bean:userService?method=get(${header.id})");

        from("direct:createUser")
            .to("bean:userService?method=create")
            .wireTap("direct:sendToActiveMQ")
            .convertBodyTo(User.class);

        from("direct:sendToActiveMQ")
            .setExchangePattern(ExchangePattern.InOnly)
            .marshal().json()
            .to("activemq:topic:" + activemqTopic);

        from("direct:updateUser")
            .to("bean:userService?method=update(${header.id}, ${body})");

        from("direct:deleteUser")
            .to("bean:userService?method=delete(${header.id})");
    }
}
