package com.brs.bookrentalsystem.model.audit;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    protected U createdBy;

    @CreatedDate
    @Column(name = "created_on", updatable = false)
    protected Date creationDate;

    @LastModifiedBy
    protected U modifiedBy;

    @LastModifiedDate
    protected Date lastModificationDate;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    protected Boolean isActive = true;

//    @PrePersist
//    public void populateIsActive(){
//        if(isActive == null){
//            isActive = true;
//        }
//    }

}
