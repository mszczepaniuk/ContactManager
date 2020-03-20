package pl.npc.contactmanager.interfaces.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.npc.contactmanager.entities.BusinessSubcategory;

public interface IBusinessSubcategoryRepository extends JpaRepository<BusinessSubcategory, String> {
}
