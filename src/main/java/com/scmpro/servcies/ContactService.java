package com.scmpro.servcies;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scmpro.entities.Contact;
import com.scmpro.entities.User;

@Service
public interface ContactService {

    // Save Contact
    Contact save(Contact contact);
    // Update Contact
    Contact update(Contact contact);
    // Get All Contacts
    List<Contact> getAllContacts();
    // Get Contact By Id
    Contact getContact(String id);
    // Delete Contact
    void deletecontact(String id);

    // get Contact By user id
    List<Contact> getContactByUserId(String id);
    // get Contact By user
    Page<Contact> getContactsByUser(User user, int page, int size, String sortField, String sortDirection);

    // Search Contact
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
            User user);



    


}
