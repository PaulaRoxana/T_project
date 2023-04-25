package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.MyRole;
import ro.itschool.entity.RoleName;

public interface RoleRepository extends JpaRepository<MyRole, Integer> {

    MyRole findByName(RoleName name);
}
