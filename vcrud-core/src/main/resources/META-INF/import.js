var class_UserGroup= "org.ddelizia.vcrud.core.usermanagement.model.UserGroup";
var class_Customer= "org.ddelizia.vcrud.core.usermanagement.model.Customer";
var class_Admin= "org.ddelizia.vcrud.core.usermanagement.model.Admin";
var class_Employee= "org.ddelizia.vcrud.core.usermanagement.model.Employee";

// ------------
// USER GROUP
// ------------
db.UserGroup.update(
    {groupName: "ROLE_AUTHENTICATED"},
    {
        groupName:"ROLE_AUTHENTICATED",
        _class: class_UserGroup
    },
    {upsert: true}
);
var userGroup_authenticated = db.UserGroup.findOne(
    {'groupName' : "ROLE_AUTHENTICATED"}
);

db.UserGroup.update(
    {groupName: "ROLE_REST"},
    {
        groupName:"ROLE_REST",
        father:{"$ref" : "UserGroup","$id" : userGroup_authenticated._id},
        _class: class_UserGroup
    },
    {upsert: true}
);
var userGroup_rest = db.UserGroup.findOne(
    {'groupName' : "ROLE_REST"}
);

db.UserGroup.update(
    {groupName: "ROLE_ADMIN"},
    {
        groupName:"ROLE_ADMIN",
        father:{"$ref" : "UserGroup","$id" : userGroup_authenticated._id},
        _class: class_UserGroup
    },
    {upsert: true}
);
var userGroup_admin = db.UserGroup.findOne(
    {'groupName' : "ROLE_ADMIN"}
);

db.UserGroup.update(
    {groupName: "ROLE_EMPLOYEE"},
    {
        groupName:"ROLE_EMPLOYEE",
        father:{"$ref" : "UserGroup","$id" : userGroup_authenticated._id},
        _class: class_UserGroup
    },
    {upsert: true}
);
var userGroup_employee = db.UserGroup.findOne(
    {'groupName' : "ROLE_EMPLOYEE"}
);

db.UserGroup.update(
    {groupName: "ROLE_AUTHENTICATED"},
    {
        groupName:"ROLE_AUTHENTICATED",
        childs:[
            {"$ref" : "UserGroup","$id" : userGroup_rest._id},
            {"$ref" : "UserGroup","$id" : userGroup_admin._id},
            {"$ref" : "UserGroup","$id" : userGroup_employee._id}
            ],
        _class: class_UserGroup
    },
    {upsert: true}
);

// ------------
// USER
// ------------
db.User.update(
    {email: "customer@example.com"},
    {
        email:"customer@example.com",
        password:"12341234",
        firstName:"customer",
        middleName:"",
        lastName:"customer",
        accountLocked: false,
        enabled: true,
        userGroups: [
            {"$ref" : "UserGroup","$id" : userGroup_rest._id}
        ],
        _class: class_Customer
    },
    {upsert: true}
);
db.User.update(
    {email: "admin@example.com"},
    {
        email:"admin@example.com",
        password:"12341234",
        firstName:"admin",
        middleName:"",
        lastName:"admin",
        accountLocked: false,
        enabled: true,
        userGroups: [
            {"$ref" : "UserGroup","$id" : userGroup_admin._id}
        ],
        _class: class_Admin
    },
    {upsert: true}
);
db.User.update(
    {email: "employee@example.com"},
    {
        email:"employee@example.com",
        password:"12341234",
        firstName:"employee",
        middleName:"",
        lastName:"employee",
        accountLocked: false,
        enabled: true,
        userGroups: [
            {"$ref" : "UserGroup","$id" : userGroup_employee._id}
        ],
        _class: class_Employee
    },
    {upsert: true}
);
db.User.update(
    {email: "anonymous@example.com"},
    {
        email:"anonymous@example.com",
        password:"12341234",
        firstName:"anonymous",
        middleName:"",
        lastName:"anonymous",
        accountLocked: false,
        enabled: true,
        _class: class_Employee
    },
    {upsert: true}
);