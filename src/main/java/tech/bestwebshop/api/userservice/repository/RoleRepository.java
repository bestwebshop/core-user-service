package tech.bestwebshop.api.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bestwebshop.api.userservice.model.dataobject.RoleDO;

@Repository
public interface RoleRepository extends JpaRepository<RoleDO, Integer> {
}
