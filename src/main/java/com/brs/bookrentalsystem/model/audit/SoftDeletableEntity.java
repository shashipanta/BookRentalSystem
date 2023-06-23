package com.brs.bookrentalsystem.model.audit;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE T SET T.deleted = true WHERE id=?")
@FilterDef(name = "deletedEntityFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedEntityFilter", condition = "deleted = :isDeleted")
public interface SoftDeletableEntity<T>{

    Boolean deleted = Boolean.FALSE;

}
