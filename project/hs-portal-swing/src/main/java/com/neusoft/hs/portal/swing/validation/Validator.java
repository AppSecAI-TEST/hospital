package com.neusoft.hs.portal.swing.validation;

import java.util.Optional;

public interface Validator <K> {

    Optional<ValidationError> validate(K k);

}
