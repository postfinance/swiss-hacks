package ch.postfinance.swiss.hacks.domain;

import jakarta.persistence.*;

@Entity
@Table
public class SupportContactInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;
  @Column
  private String email;
  @Column
  private String phone;

  public SupportContactInfo(String email, String phone) {
  }

  public SupportContactInfo() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

}
