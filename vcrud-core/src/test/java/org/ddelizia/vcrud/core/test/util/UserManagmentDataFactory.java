package org.ddelizia.vcrud.core.test.util;

import com.google.common.collect.Sets;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.usermanagement.model.Customer;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.repository.UserGroupRepository;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 24/02/14
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
public class UserManagmentDataFactory extends DataFactory{

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserGroupRepository userGroupRepository;

    public static final String CUSTOMER_VERIFIED_RESTGROUP_NAME = "customer_verified_restgroup_name";
    public static final String CUSTOMER_VERIFIED_RESTGROUP_EMAIL = "customer_verified_restgroup@example.com";

	public static final String CUSTOMER_SIMPLE_NAME = "customer_simple_name";
	public static final String CUSTOMER_SIMPLE_EMAIL = "customer_simple@example.com";

    public static final String USERGROUP_LAST_NAME = "ROLE_LAST";

	private UserGroup userGroupAuthenticated;
	private UserGroup userGroupRest;
	private UserGroup userGroupChildOfRest;

	private Customer customerVerifiedRestGroup;
	private Customer customerSimple;

    public void createData(){
	    userGroupAuthenticated = new UserGroup();
        userGroupAuthenticated.setGroupName(getAppConfig().getProperty(AppConfig.USER_USERGROUP_AUTHENTICATED, String.class, null));
        userGroupAuthenticated.setName(getAppConfig().getProperty(AppConfig.USER_USERGROUP_AUTHENTICATED, String.class, null));
        userGroupRepository.save(userGroupAuthenticated);

        userGroupRest = new UserGroup();
        userGroupRest.setGroupName(getAppConfig().getProperty(AppConfig.USER_USERGROUP_REST, String.class, null));
        userGroupRest.setName(getAppConfig().getProperty(AppConfig.USER_USERGROUP_REST, String.class, null));
        userGroupRest.setFather(userGroupAuthenticated);
        userGroupRepository.save(userGroupRest);

	    userGroupChildOfRest = new UserGroup();
	    userGroupChildOfRest.setGroupName(USERGROUP_LAST_NAME);
	    userGroupChildOfRest.setName(USERGROUP_LAST_NAME);
	    userGroupChildOfRest.setFather(userGroupRest);
        Set<UserGroup> set = new HashSet<>(userGroupRest.getAnchestors());
        set.add(userGroupRest.getFather());
	    userGroupChildOfRest.setAnchestors(set);
        userGroupRepository.save(userGroupChildOfRest);

        customerVerifiedRestGroup = new Customer();
        customerVerifiedRestGroup.setAccountLocked(false);
        customerVerifiedRestGroup.setCredentialsExpireTime(null);
        customerVerifiedRestGroup.setEmail(CUSTOMER_VERIFIED_RESTGROUP_EMAIL);
        customerVerifiedRestGroup.setEnabled(true);
        customerVerifiedRestGroup.setExpireDate(null);
        customerVerifiedRestGroup.setFirstName("firstNameCustomer");
        customerVerifiedRestGroup.setLastName("lastNameCustomer");
        customerVerifiedRestGroup.setMiddleName("middleNameCustomer");
        customerVerifiedRestGroup.setPassword("password");
        customerVerifiedRestGroup.setName(CUSTOMER_VERIFIED_RESTGROUP_NAME);
        customerVerifiedRestGroup.setUserGroups(Sets.newHashSet(userGroupRest));
        userRepository.save(customerVerifiedRestGroup);

	    customerSimple = new Customer();
	    customerSimple.setName(CUSTOMER_SIMPLE_NAME);
	    customerSimple.setEmail(CUSTOMER_SIMPLE_EMAIL);
	    userRepository.save(customerSimple);


    }

	public Customer createBasicCustomerNotSaved(String username, String password, UserGroup userGroup){
		Customer customer = new Customer();
		customer.setName(username);
		customer.setPassword(password);
		customer.setUserGroups(Sets.newHashSet(userGroup));
		return customer;
	}

    public void removeData(){
        getMongoHelper().removeAllDataFromCollection(User.class);
        getMongoHelper().removeAllDataFromCollection(UserGroup.class);
    }

	public UserGroup getUserGroupAuthenticated() {
		return userGroupAuthenticated;
	}

	public UserGroup getUserGroupRest() {
		return userGroupRest;
	}

	public UserGroup getUserGroupChildOfRest() {
		return userGroupChildOfRest;
	}

	public Customer getCustomerVerifiedRestGroup() {
		return customerVerifiedRestGroup;
	}

	public Customer getCustomerSimple() {
		return customerSimple;
	}
}
