package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.domain.SupportContactInformation;
import ch.postfinance.swiss.hacks.resource.beans.SupportContact;
import ch.postfinance.swiss.hacks.service.SupportService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class SupportResourceImpl implements SupportResource {

    @Inject
    SupportService supportService;

    public static SupportContact convertToDTO(SupportContactInformation entity) {
        SupportContact dto = new SupportContact();
        dto.setEmail(entity.email);
        dto.setPhone(entity.phone);
        return dto;
    }

    @Override
    public Response contactCustomerSupport() {
        List<SupportContact> supportData = supportService.getSupportInformation().stream()
                .map(SupportResourceImpl::convertToDTO)
                .toList();
        return Response.ok(supportData).build();
    }
}
