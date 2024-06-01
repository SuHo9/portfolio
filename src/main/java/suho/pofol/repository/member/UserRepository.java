package suho.pofol.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import suho.pofol.domain.member.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

}
