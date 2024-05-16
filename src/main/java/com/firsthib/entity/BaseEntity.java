package com.firsthib.entity;

import javax.persistence.*;


import java.io.Serializable;

@MappedSuperclass
public interface BaseEntity <T extends Serializable>{
    T getId();
    void setId(T id);

}
