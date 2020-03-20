package pl.npc.contactmanager.interfaces.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.npc.contactmanager.entities.Contact;

public interface IContactRepository extends JpaRepository<Contact, Long> {

}
