package org.ddelizia.vcrud.core.usermanagement.service.converter;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.commons.client.CreateUserRequest;
import org.ddelizia.vcrud.core.usermanagement.model.Customer;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author ddelizia
 * @since 28/02/14 14:09
 */

@Component
public class ConverterCreateUserRequest2User implements Converter<CreateUserRequest, User> {

	private static final Logger LOGGER = Logger.getLogger(ConverterCreateUserRequest2User.class);


	/**
	 * Convert the source of type S to target type T.
	 *
	 * @param source the source object to convert, which must be an instance of S
	 * @return the converted object, which must be an instance of T
	 * @throws IllegalArgumentException if the source could not be converted to the desired target type
	 */
	@Override
	public User convert(CreateUserRequest source) {
		Assert.notNull(source);
		User user = new Customer();
		user.setName(source.getUser().getName());
		user.setFirstName(source.getUser().getFirstName());
		user.setLastName(source.getUser().getLastName());
		user.setEmail(source.getUser().getEmailAddress());
		return user;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
