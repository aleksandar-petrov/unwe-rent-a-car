package bg.unwe.aleksandarpetrov.rentacar.entity;

import bg.unwe.aleksandarpetrov.rentacar.entity.base.IdentifiableTimestampEntity;
import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.CarStatus;
import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.CarTransmission;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends IdentifiableTimestampEntity {

  @Column(nullable = false)
  private Integer year;

  @Column(nullable = false)
  private String make;

  @Column(nullable = false)
  private String model;

  @Column(nullable = false)
  private Long mileage;

  @Enumerated(EnumType.STRING)
  private CarTransmission transmission;

  @OneToMany(cascade = CascadeType.ALL)
  @Exclude
  private List<Photo> photos = new ArrayList<>();

  @ManyToOne private User owner;

  @Column(nullable = false)
  private BigDecimal pricePerDay;

  @Enumerated(EnumType.STRING)
  private CarStatus status;

  @OneToOne(cascade = CascadeType.ALL)
  private Location location;
}
