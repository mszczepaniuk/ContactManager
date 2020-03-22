package pl.npc.contactmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.npc.contactmanager.dto.bindingmodels.ContactBindingModel;
import pl.npc.contactmanager.dto.viewmodels.BasicContactViewModel;
import pl.npc.contactmanager.dto.viewmodels.BusinessSubcategoriesViewModel;
import pl.npc.contactmanager.dto.viewmodels.ContactViewModel;
import pl.npc.contactmanager.entities.BusinessSubcategory;
import pl.npc.contactmanager.entities.Contact;
import pl.npc.contactmanager.interfaces.repositories.IBusinessSubcategoryRepository;
import pl.npc.contactmanager.interfaces.repositories.IContactRepository;
import pl.npc.contactmanager.interfaces.services.IContactService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService implements IContactService {

    private final IContactRepository contactRepo;
    private final IBusinessSubcategoryRepository subcategoryRepository;
    @Value("${contactmanager.contact.resultsperpage}")
    private int resultsPerPage;

    @Autowired
    public ContactService(IContactRepository contactRepo,
                          IBusinessSubcategoryRepository subcategoryRepository) {
        this.contactRepo = contactRepo;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public ContactViewModel getFullContactById(long id) {
        ContactViewModel vm = new ContactViewModel();
        Contact contact = contactRepo.findById(id).get();
        vm.setId(id);
        vm.setFirstName(contact.getFirstName());
        vm.setLastName(contact.getLastName());
        vm.setEmail(contact.getEmail());
        vm.setPassword(contact.getPassword());
        vm.setCategory(contact.getCategory());
        vm.setSubcategory(contact.getSubcategory());
        vm.setTelephoneNumber(contact.getTelephoneNumber());
        vm.setDateOfBirth(contact.getDateOfBirth().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        vm.setCreationDate(contact.getCreationDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if (contact.getUpdateDate() != null) {
            vm.setUpdateDate(contact.getUpdateDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        return vm;
    }

    @Override
    public List<BasicContactViewModel> getPageOfBasicsContacts(int page) {
        Page<Contact> contacts = contactRepo.findAll(PageRequest.of(page - 1, resultsPerPage));
        return PageOfFullContactsToListOfBasicContacts(contacts);
    }

    @Override
    public Contact addContact(ContactBindingModel bindingModel) {
        Contact contact = new Contact();
        ContactBindingModelToContact(contact, bindingModel);
        contact.setCreationDate(new Date());
        contactRepo.save(contact);
        return contact;
    }

    @Override
    public Contact editContact(long id, ContactBindingModel bindingModel) {
        Contact contact = contactRepo.findById(id).get();
        ContactBindingModelToContact(contact, bindingModel);
        contact.setUpdateDate(new Date());
        contactRepo.save(contact);
        return contact;
    }

    @Override
    public void deleteContact(long id) {
        Contact contact = contactRepo.findById(id).get();
        contactRepo.delete(contact);
    }

    @Override
    public boolean doesContactExist(long id) {
        Optional<Contact> contact = contactRepo.findById(id);
        return contact.isPresent();
    }

    @Override
    public BusinessSubcategoriesViewModel getBusinessSubcategories() {
        BusinessSubcategoriesViewModel vm = new BusinessSubcategoriesViewModel();
        List<String> subcategories = new ArrayList<>();
        for (BusinessSubcategory subcategoryObject : subcategoryRepository.findAll()) {
            subcategories.add(subcategoryObject.getSubcategory());
        }
        vm.setSubcategories(subcategories);
        return vm;
    }

    @Override
    public long getAmountOfPages() {
        long contacts = contactRepo.count();
        if (contacts == 0) {
            contacts = 1;
        }
        return ((contacts - 1) / resultsPerPage) + 1;
    }

    private List<BasicContactViewModel> PageOfFullContactsToListOfBasicContacts(Page<Contact> fullContacts) {
        List<BasicContactViewModel> vm = new ArrayList();
        for (Contact contact : fullContacts) {
            BasicContactViewModel basicContact = new BasicContactViewModel();
            basicContact.setId(contact.getId());
            basicContact.setFirstName(contact.getFirstName());
            basicContact.setLastName(contact.getLastName());
            vm.add(basicContact);
        }
        return vm;
    }

    // Zmienia pola przekazanego kontaktu na te które są w modelu.
    private void ContactBindingModelToContact(Contact contact, ContactBindingModel bindingModel) {
        contact.setFirstName(bindingModel.getFirstName());
        contact.setLastName(bindingModel.getLastName());
        contact.setEmail(bindingModel.getEmail());
        contact.setPassword(bindingModel.getPassword());
        contact.setCategory(bindingModel.getCategory());
        contact.setSubcategory(bindingModel.getSubcategory());
        contact.setTelephoneNumber(bindingModel.getTelephoneNumber());

        LocalDate localDate = LocalDate.parse(bindingModel.getDateOfBirth());
        Date dateOfBirth = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        contact.setDateOfBirth(dateOfBirth);
    }
}
