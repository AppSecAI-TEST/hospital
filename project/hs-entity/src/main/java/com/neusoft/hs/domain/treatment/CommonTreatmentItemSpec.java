package com.neusoft.hs.domain.treatment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Common")
public class CommonTreatmentItemSpec extends TreatmentItemSpec {

}
