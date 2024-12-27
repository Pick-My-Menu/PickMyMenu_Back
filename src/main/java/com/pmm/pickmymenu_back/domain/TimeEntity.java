package com.wandu.momentum.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeEntity {
    @Column(updatable = false, nullable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column
    private LocalDateTime updatedDate;

    @PrePersist
    @PreUpdate
    public void onPersist(){
        createdDate = createdDate.truncatedTo(ChronoUnit.SECONDS);
        updatedDate = updatedDate.truncatedTo(ChronoUnit.SECONDS);
    }

}
