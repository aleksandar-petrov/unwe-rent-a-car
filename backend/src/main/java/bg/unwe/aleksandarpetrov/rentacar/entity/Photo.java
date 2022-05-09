package bg.unwe.aleksandarpetrov.rentacar.entity;

import bg.unwe.aleksandarpetrov.rentacar.entity.base.TimestampEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "photos")
public class Photo extends TimestampEntity {

  @Id
  @Column(nullable = false, unique = true, updatable = false)
  private String id;

  @Column(nullable = false)
  private String url;

  @Column(nullable = false)
  private Boolean isAssigned;

  @Column(nullable = false)
  private Boolean isUploaded;
}
