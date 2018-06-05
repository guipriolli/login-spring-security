package online.suacasa.service;

import online.suacasa.model.Usuario;

public interface UserService {

    public Usuario findUserByEmail(String email);

    public void saveUser(Usuario user);

}
