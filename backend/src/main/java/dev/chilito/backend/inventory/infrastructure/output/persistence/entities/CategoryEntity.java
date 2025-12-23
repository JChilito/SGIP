package dev.chilito.backend.inventory.infrastructure.output.persistence.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
// This allows that the events that happen in the entity are notified to another
// external class, in this case it is used for the entity audit
@EntityListeners(AuditingEntityListener.class)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @CreatedDate // This annotation captures automatically the creation date (audit field)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate // This annotation captures automatically the update date (audit field)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @ToString.Exclude // Thus avoid infinite loop when printing the object
    private List<ProductEntity> products;
}
