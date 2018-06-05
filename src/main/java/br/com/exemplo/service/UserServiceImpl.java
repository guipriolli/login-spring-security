package online.suacasa.service;

import java.util.Arrays;
import java.util.HashSet;
import online.suacasa.model.Role;
import online.suacasa.model.Usuario;
import online.suacasa.repository.RoleRepository;
import online.suacasa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Usuario findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void saveUser(Usuario user) {
        user.setSenha(bCryptPasswordEncoder.encode(user.getSenha()));
        user.setAtivo(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

}
