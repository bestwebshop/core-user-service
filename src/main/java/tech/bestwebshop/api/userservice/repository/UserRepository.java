package tech.bestwebshop.api.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bestwebshop.api.userservice.model.dataobject.UserDO;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDO, Integer> {

    public Optional<UserDO> findByUsername(String username);
}
