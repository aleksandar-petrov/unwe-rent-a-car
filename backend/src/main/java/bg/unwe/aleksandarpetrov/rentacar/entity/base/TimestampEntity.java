package bg.unwe.aleksandarpetrov.rentacar.entity.base;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public abstract class TimestampEntity {

  @Column(name = "created_on_18118010", nullable = false)
  @CreationTimestamp
  private LocalDateTime createdOn;

  @Column(name = "updated_on_18118010", nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedOn;
}
