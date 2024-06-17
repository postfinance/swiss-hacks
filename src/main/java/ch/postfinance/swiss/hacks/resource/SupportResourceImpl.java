package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.domain.SupportContactInformation;
import ch.postfinance.swiss.hacks.resource.beans.ContactInformation;
import ch.postfinance.swiss.hacks.service.SupportService;
import jakarta.inject.Inject;

import java.util.List;

public class SupportResourceImpl implements SupportResource {

    @Inject
    SupportService supportService;

    private static ContactInformation convertToDTO(SupportContactInformation entity) {
        var supportContact = new ContactInformation();
        supportContact.setEmail(entity.email);
        supportContact.setPhone(entity.phone);
        return supportContact;
    }

    @Override
    public List<ContactInformation> contactCustomerSupport() {
        return supportService.getSupportInformation().stream()
                .map(SupportResourceImpl::convertToDTO)
                .toList();
    }
}
