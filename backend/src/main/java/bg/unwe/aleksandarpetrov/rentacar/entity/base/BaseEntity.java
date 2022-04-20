package bg.unwe.aleksandarpetrov.rentacar.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

  @Id
  @GeneratedValue(generator = "uuid-string")
  @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", nullable = false, unique = true, updatable = false)
  private String id;
}
