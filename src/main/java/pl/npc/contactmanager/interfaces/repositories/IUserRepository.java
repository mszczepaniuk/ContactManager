package pl.npc.contactmanager.interfaces.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.npc.contactmanager.entities.User;

public interface IUserRepository extends JpaRepository<User, Long> {

}
