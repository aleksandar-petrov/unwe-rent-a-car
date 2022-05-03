package bg.unwe.aleksandarpetrov.rentacar.entity;

import bg.unwe.aleksandarpetrov.rentacar.entity.base.BaseEntity;
import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.RentalStatus;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
public class Rental extends BaseEntity {

  @Column(nullable = false)
  private LocalDate rentedFrom;

  @Column(nullable = false)
  private LocalDate rentedTo;

  @ManyToOne private User renter;

  @Enumerated(EnumType.STRING)
  private RentalStatus status;
}
