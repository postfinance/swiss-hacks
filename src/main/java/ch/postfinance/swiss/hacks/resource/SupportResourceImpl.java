package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.domain.SupportContactInfo;
import ch.postfinance.swiss.hacks.resource.beans.SupportContact;
import ch.postfinance.swiss.hacks.service.SupportService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class SupportResourceImpl implements SupportResource {

  @Inject
  SupportService supportService;

  @Override
  public Response contactCustomerSupport() {
    List<SupportContact> supportData = supportService.getAllSupportInformations();
    return Response.ok(supportData).build();
  }
}
