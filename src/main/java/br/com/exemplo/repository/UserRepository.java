package online.suacasa.repository;

import online.suacasa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    Usuario findUserByEmail(String email);
}