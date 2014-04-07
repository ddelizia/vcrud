package org.ddelizia.vcrud.core.test.usermagement.service;

import org.ddelizia.vcrud.core.usermanagement.model.Customer;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.repository.UserGroupRepository;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.usermanagement.service.UserGroupService;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.ddelizia.vcrud.core.usermanagement.service.impl.UserServiceImpl;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 25/02/14
 * Time: 20:56
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceTest {


	private UserGroupService userGroupService;
	private UserGroupRepository userGroupRepository;
	private UserRepository userRepository;

	private UserService userService;

	@Before
	public void setUp() {
		userGroupService = Mockito.mock(UserGroupService.class);
		userGroupRepository = Mockito.mock(UserGroupRepository.class);
		userRepository = Mockito.mock(UserRepository.class);

		userService = new UserServiceImpl();
		((UserServiceImpl) userService).setUserGroupRepository(userGroupRepository);
		((UserServiceImpl) userService).setUserGroupService(userGroupService);
		((UserServiceImpl) userService).setUserRepository(userRepository);
	}

	@Test
	public void isAccountExpired() {
		User user = new Customer();
		user.setExpireDate(DateTime.now().minusDays(5));
		Assert.assertTrue(userService.isAccountExpired(user));

		user.setExpireDate(DateTime.now().plusDays(5));
		Assert.assertFalse(userService.isAccountExpired(user));

		user.setExpireDate(null);
		Assert.assertFalse(userService.isAccountExpired(user));
	}

	@Test
	public void isAccountCredentialsExpired() {
		User user = new Customer();
		user.setCredentialsExpireTime(DateTime.now().minusDays(5));
		Assert.assertTrue(userService.isAccountCredentialsExpired(user));

		user.setCredentialsExpireTime(DateTime.now().plusDays(5));
		Assert.assertFalse(userService.isAccountCredentialsExpired(user));

		user.setCredentialsExpireTime(null);
		Assert.assertFalse(userService.isAccountCredentialsExpired(user));
	}

	@Test
	public void isVerified() {
		User user = createVerifiedUser();
		Assert.assertTrue(userService.isVerified(user));

		user = createVerifiedUser();
		user.setEnabled(false);
		Assert.assertFalse(userService.isVerified(user));

		user = createVerifiedUser();
		user.setAccountLocked(true);
		Assert.assertFalse(userService.isVerified(user));

		user = createVerifiedUser();
		user.setExpireDate(DateTime.now().minusDays(5));
		Assert.assertFalse(userService.isVerified(user));

		user = createVerifiedUser();
		user.setCredentialsExpireTime(DateTime.now().minusDays(5));
		Assert.assertFalse(userService.isVerified(user));

		user = createVerifiedUser();
		user.setUserGroups(null);
		Assert.assertFalse(userService.isVerified(user));

	}

	private User createVerifiedUser(){
		User user = new Customer();
		user.setAccountLocked(false);
		user.setExpireDate(DateTime.now().plusDays(5));
		user.setCredentialsExpireTime(DateTime.now().plusDays(5));
		user.setEnabled(true);
		user.setUserGroups(new HashSet<UserGroup>(Arrays.asList(new UserGroup())));
		return user;
	}

	@Test
	public void createCustomerInGroup() {
		String groupName = "groupName";
		UserGroup userGroup = new UserGroup();
		userGroup.setName(groupName);
		User customer = userService.createCustomerInGroup(userGroup);
		Assert.assertTrue(customer.getUserGroups().size() == 1);
		Assert.assertTrue(customer.getUserGroups().iterator().next().getName().equals(groupName));
	}

	@Test
	public void assignGroupToUser() {
		UserGroup userGroup = new UserGroup();
		userGroup.setGroupName("GroupName");
		Customer customer = new Customer();
		customer.setId("UserId");
		Mockito.when(userGroupRepository.findByGroupName(userGroup.getGroupName())).thenReturn(userGroup);
		Mockito.when(userRepository.findOne(customer.getId())).thenReturn(customer);
		User returnUser = userService.assignGroupToUser(userGroup.getGroupName(), customer.getId());
		Assert.assertTrue(returnUser.getUserGroups().size() == 1);
		Assert.assertTrue(returnUser.getUserGroups().iterator().next().getGroupName().equals(userGroup.getGroupName()));
	}

	@Test
	public void verifyUser() {
		String groupName = "groupName";
		UserGroup userGroup = new UserGroup();
		userGroup.setName(groupName);
		User customer = userService.createCustomerInGroup(userGroup);

		userService.verifyUser(customer);

		Assert.assertTrue(userService.isVerified(customer));
	}

}
