package br.com.crud.gestaousuario.service;

import br.com.crud.gestaousuario.model.User;
import br.com.crud.gestaousuario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Collection<User> list() {
        return userRepository.findAll();
    }

    public User get(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(String id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
