package com.neusoft.hs.domain.organization;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CommonDept")
public class CommonDept extends Dept {

}
