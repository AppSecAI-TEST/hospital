package com.neusoft.hs.portal.swing.validation;

import java.util.Optional;

interface Validator <K> {

    Optional<ValidationError> validate(K k);

}
