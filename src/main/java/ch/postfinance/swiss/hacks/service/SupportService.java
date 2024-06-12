package ch.postfinance.swiss.hacks.service;

import ch.postfinance.swiss.hacks.domain.SupportContactInfo;
import ch.postfinance.swiss.hacks.resource.SupportRepository;
import ch.postfinance.swiss.hacks.resource.beans.SupportContact;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SupportService {

  @Inject
  SupportRepository supportRepository;

  public static SupportContact convertToDTO(SupportContactInfo entity) {
    SupportContact dto = new SupportContact();
    dto.setEmail(entity.getEmail());
    dto.setPhone(entity.getPhone());
    return dto;
  }
  public List<SupportContact> getAllSupportInformations() {
    PanacheQuery<SupportContactInfo> query = supportRepository.findAll();
    List<SupportContactInfo> supportContactInfos = query.list();
    return supportContactInfos.stream()
                              .map(SupportService::convertToDTO)
                              .collect(Collectors.toList());
  }

}
