package pl.npc.contactmanager.interfaces.services;

import pl.npc.contactmanager.dto.bindingmodels.ContactBindingModel;
import pl.npc.contactmanager.dto.viewmodels.BasicContactViewModel;
import pl.npc.contactmanager.dto.viewmodels.BusinessSubcategoriesViewModel;
import pl.npc.contactmanager.dto.viewmodels.ContactViewModel;
import pl.npc.contactmanager.entities.Contact;

import java.util.List;

public interface IContactService {
    public ContactViewModel getFullContactById(long id);
    public List<BasicContactViewModel> getPageOfBasicsContacts(int page);
    public Contact addContact(ContactBindingModel contact);
    public Contact editContact(long id, ContactBindingModel contact);
    public boolean doesContactExist(long id);
    public void deleteContact(long id);
    public BusinessSubcategoriesViewModel getBusinessSubcategories();
    public long getAmountOfPages();
}
