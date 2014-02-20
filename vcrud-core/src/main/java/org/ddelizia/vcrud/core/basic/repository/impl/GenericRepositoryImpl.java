package org.ddelizia.vcrud.core.basic.repository.impl;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.basic.repository.GenericRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ddelizia
 * @since 18/02/14 13:21
 */

@Repository(GenericRepository.DEFAULT_BEAN_NAME)
public class GenericRepositoryImpl implements GenericRepository{

    private static final Logger LOGGER = Logger.getLogger(GenericRepositoryImpl.class);


}
