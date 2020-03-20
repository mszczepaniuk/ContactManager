package pl.npc.contactmanager.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BusinessSubcategory {
    @Id
    private String subcategory;

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
