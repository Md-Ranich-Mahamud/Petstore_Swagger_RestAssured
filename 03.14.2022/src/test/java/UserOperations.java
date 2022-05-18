import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.json.simple.JSONObject;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;

public class UserOperations {
    public static String baseUrl = "https://petstore.swagger.io/v2";
    static JSONObject requestBody = new JSONObject();
    static Response response = null;
    static JsonPath jsonPathEvaluator = null;
    public static String additionCriterionModificationDataRangeEndPoint = "/acl/financialInstitution/detail";

    public static void main(String[] agrs) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 2; i++) {
            System.out.println("Enter id : ");
            String userId = scanner.nextLine();

            int id = Integer.parseInt(userId);

            System.out.println("Enter username : ");
            String username = scanner.nextLine();

            System.out.println("Enter firstName : ");
            String firstName = scanner.nextLine();

            System.out.println("Enter lastName : ");
            String lastName = scanner.nextLine();

            System.out.println("Enter email : ");
            String email = scanner.nextLine();

            System.out.println("Enter password : ");
            String password = scanner.nextLine();

            System.out.println("Enter phone : ");
            String phone = scanner.nextLine();

            System.out.println("Enter userStatus : ");
            int userStatus = scanner.nextInt();

            createUser(id, username, firstName, lastName, email, password, phone, userStatus);
        }
    }

    @Test(priority = 1, enabled = true)
    public static void createUser(int id, String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        requestBody.put("id", id);
        requestBody.put("username", username);
        requestBody.put("firstName", firstName);
        requestBody.put("lastName", lastName);
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("phone", phone);
        requestBody.put("userStatus", userStatus);

        response = given().
                body(requestBody.toJSONString()).
                contentType("application/json").
                when().
                post(baseUrl + "/user").
                then().
                statusCode(200).log().body().extract().response();

        jsonPathEvaluator = response.jsonPath();
        String messageValue = jsonPathEvaluator.get("message");
        System.out.println("The value of message attribute is - " + messageValue);

        assertEquals(messageValue, "100");
    }

    @Test(priority = 2)
    public void getUserByUsername() {
        response = given().
                pathParam("username", "user123").
                when().
                get(baseUrl + "/user/{username}").
                then().
                assertThat().
                statusCode(200).
                log().body().
                extract().response();
        jsonPathEvaluator = response.jsonPath();
        int valueOfId = jsonPathEvaluator.get("id");
        String valueOfUsername = jsonPathEvaluator.get("username");
        String valueOfFirstname = jsonPathEvaluator.get("firstName");
        String valueOfLastname = jsonPathEvaluator.get("lastName");
        String valueOfEmail = jsonPathEvaluator.get("email");
        String valueOfPassword = jsonPathEvaluator.get("password");
        String valueOfPhone = jsonPathEvaluator.get("phone");
        int valueOfUserStatus = jsonPathEvaluator.get("userStatus");

        System.out.println("The value of id attribute is - " + valueOfId);
        System.out.println("The value of username attribute is - " + valueOfUsername);
        System.out.println("The value of firstName attribute is - " + valueOfFirstname);
        System.out.println("The value of lastName attribute is - " + valueOfLastname);
        System.out.println("The value of email attribute is - " + valueOfEmail);
        System.out.println("The value of password attribute is - " + valueOfPassword);
        System.out.println("The value of phone attribute is - " + valueOfPhone);
        System.out.println("The value of userStatus attribute is - " + valueOfUserStatus);

        assertEquals(valueOfId, 100);
        assertEquals(valueOfUsername, "user123");
        assertEquals(valueOfFirstname, "Md.");
        assertEquals(valueOfLastname, "Rahim");
        assertEquals(valueOfEmail, "rahim@gmail.com");
        assertEquals(valueOfPassword, "123@3");
        assertEquals(valueOfPhone, "+19999999999");
        assertEquals(valueOfUserStatus, 1);
    }

    @Test(priority = 3)
    public void updatedUser() {
        requestBody.put("id", 100);
        requestBody.put("username", "user123");
        requestBody.put("firstName", "Mohammad");
        requestBody.put("lastName", "Rahim");
        requestBody.put("email", "rahim02@gmail.com");
        requestBody.put("password", "123@#");
        requestBody.put("phone", "+19999999988");
        requestBody.put("userStatus", 1);

        response = given().
                pathParam("username", "user123").
                body(requestBody.toJSONString()).
                contentType("application/json").
                when().
                put(baseUrl + "/user/{username}").
                then().
                assertThat().
                statusCode(200).
                log().body().extract().response();

        int valueOfCodeAttribue = response.jsonPath().get("code");
        String valueOfTypeAttribue = response.jsonPath().get("type");

        jsonPathEvaluator = response.jsonPath();
        String valueOfMessageAttribue = jsonPathEvaluator.get("message");

        assertEquals(valueOfCodeAttribue, 200);
        assertEquals(valueOfTypeAttribue, "unknown");
        assertEquals(valueOfMessageAttribue, "100");
    }

    @Test(priority = 4)
    public void createListOfUserWithArray() {
        Map<String, Object> user1 = new HashMap<String, Object>();
        user1.put("id", 101);
        user1.put("username", "user456");
        user1.put("firstName", "Mohammad");
        user1.put("lastName", "Bahar");
        user1.put("email", "bahar@gmail.com");
        user1.put("password", "235689");
        user1.put("phone", "+19999999999");
        user1.put("userStatus", 1);

        Map<String, Object> user2 = new HashMap<String, Object>();
        user2.put("id", 102);
        user2.put("username", "user789");
        user2.put("firstName", "Mohammad");
        user2.put("lastName", "Ranich");
        user2.put("email", "ranich@gmail.com");
        user2.put("password", "gh@#");
        user2.put("phone", "+19999999998");
        user2.put("userStatus", 1);

        Map<String, Object> user3 = new HashMap<String, Object>();
        user3.put("id", 103);
        user3.put("username", "user000");
        user3.put("firstName", "Mohammad");
        user3.put("lastName", "Karim");
        user3.put("email", "karim@gmail.com");
        user3.put("password", "295689");
        user3.put("phone", "+19996999999");
        user3.put("userStatus", 1);

        List<Map<String, Object>> jsonArrayPayload = new ArrayList<>();
        jsonArrayPayload.add(user1);
        jsonArrayPayload.add(user2);
        jsonArrayPayload.add(user3);

        Gson gson = new Gson();
        String listOfUserPayload = gson.toJson(jsonArrayPayload);

        response = given().
                body(listOfUserPayload).
                contentType("application/json").
                when().
                post(baseUrl + "/user/createWithArray").
                then().
                assertThat().
                statusCode(200).
                log().body().extract().response();

        System.out.println(response);
        jsonPathEvaluator = response.jsonPath();
        int valueOfCode = jsonPathEvaluator.get("code");
        String valueOfType = jsonPathEvaluator.get("type");
        String valueOfMessage = jsonPathEvaluator.get("message");

        System.out.println("The value of code attribute is - " + valueOfCode);
        System.out.println("The value of type attribute is - " + valueOfType);
        System.out.println("The value of message attribute is - " + valueOfMessage);

        assertEquals(valueOfCode, 200);
        assertEquals(valueOfType, "unknown");
        assertEquals(valueOfMessage, "ok");
    }

    @Test(priority = 5)
    public void createListOfUserWithList() {
        Map<String, Object> user1 = new HashMap<String, Object>();
        user1.put("id", 104);
        user1.put("username", "user102");
        user1.put("firstName", "Mohammad");
        user1.put("lastName", "Sabbir");
        user1.put("email", "sabbir@gmail.com");
        user1.put("password", "2355589");
        user1.put("phone", "+19999991499");
        user1.put("userStatus", 1);

        Map<String, Object> user2 = new HashMap<String, Object>();
        user2.put("id", 105);
        user2.put("username", "user103");
        user2.put("firstName", "Mohammad");
        user2.put("lastName", "Asif");
        user2.put("email", "asif@gmail.com");
        user2.put("password", "g65h@#");
        user2.put("phone", "+19925999998");
        user2.put("userStatus", 1);

        Map<String, Object> user3 = new HashMap<String, Object>();
        user3.put("id", 106);
        user3.put("username", "user104");
        user3.put("firstName", "Mohammad");
        user3.put("lastName", "Sakib");
        user3.put("email", "sakib@gmail.com");
        user3.put("password", "8995689");
        user3.put("phone", "+19996669999");
        user3.put("userStatus", 1);

        List<Map<String, Object>> jsonArrayPayload = new ArrayList<>();
        jsonArrayPayload.add(user1);
        jsonArrayPayload.add(user2);
        jsonArrayPayload.add(user3);

        Gson gson = new Gson();
        String listOfUserPayload = gson.toJson(jsonArrayPayload);

        response = given().
                body(listOfUserPayload).
                contentType("application/json").
                when().
                post(baseUrl + "/user/createWithList").
                then().
                assertThat().
                statusCode(200).
                log().body().extract().response();

        System.out.println(response);
        jsonPathEvaluator = response.jsonPath();
        int valueOfCode = jsonPathEvaluator.get("code");
        String valueOfType = jsonPathEvaluator.get("type");
        String valueOfMessage = jsonPathEvaluator.get("message");

        System.out.println("The value of code attribute is - " + valueOfCode);
        System.out.println("The value of type attribute is - " + valueOfType);
        System.out.println("The value of message attribute is - " + valueOfMessage);

        assertEquals(valueOfCode, 200);
        assertEquals(valueOfType, "unknown");
        assertEquals(valueOfMessage, "ok");
    }

    @Test(priority = 6)
    public void deleteUser() {
        response = given().
                pathParam("username", "user104").
                when().
                delete(baseUrl + "/user/{username}").
                then().
                assertThat().
                statusCode(200).
                extract().response();

        System.out.println(response);
        jsonPathEvaluator = response.jsonPath();
        int valueOfCode = jsonPathEvaluator.get("code");
        String valueOfType = jsonPathEvaluator.get("type");
        String valueOfMessage = jsonPathEvaluator.get("message");

        System.out.println("The value of code attribute is - " + valueOfCode);
        System.out.println("The value of type attribute is - " + valueOfType);
        System.out.println("The value of message attribute is - " + valueOfMessage);

        assertEquals(valueOfCode, 200);
        assertEquals(valueOfType, "unknown");
        assertEquals(valueOfMessage, "user104");
    }


    //Valid_AISVMW-80
//    @Test
//    public void vaildDeleteExistingSetOfUsersOfUserService(){
//        String payload = "{\"userIds\":[\"abc123\",\"cdef345\"";
//        String responseBody = given().
//                pathParam("partnerId", "50040000").
//                pathParam("homeID", "50040005").
//                body(payload).
//                contentType(ContentType.JSON).
//        when().
//                delete(baseUrl + "/user/partner/{partnerId}/home/{homeID}").
//        then().assertThat().statusCode(204).extract().body().asString();
//
//        assertEquals(responseBody, null);
//    }
//
//    @Test
//    public void vaildateUserIsDeletedFromHeritageDB(){
//        Response res = given().
//                pathParam("homeId", "50040005").
//                pathParam("LoginName", "").
//                when().
//                get(baseUrl + "/endpoint").
//                then().assertThat().statusCode(404).extract().response();
//    }
//
//    //AISVMW-17
//    @Test
//    public void validateUserIsCreatedInHeritageSystem(){
//        Map<String, Object> user1Addresses1 = new HashMap<String, Object>();
//        user1Addresses1.put("addressType", "Work");
//        user1Addresses1.put("postalCode", "11532");
//        user1Addresses1.put("country", "USA");
//        user1Addresses1.put("addressLine1", "450 Jamaica Ave");
//        user1Addresses1.put("addressLine2", "USA");
//        user1Addresses1.put("city", "Jamaica");
//        user1Addresses1.put("stateProv", "New York");

/*        //Array of Objects
        "addesses": [
            {
                "attribute1": "value",
                "attribute2": "value",
                "attribute3": "value"
            },
            {
                "attribute1": "value",
                "attribute2": "value",
                "attribute3": "value"
            }
        ]

        //Array of Strings
        "addesses": [
            "attribute1": "value",
            "attribute2": "value",
            "attribute3": "value"
       ]
  */

//////////////////////////////////////////////////////
    //Single Object

    //data --- Starting point
    //JsonPath jsonPathEvaluator = null;
    //jsonPathEvaluator = new JsonPath(response.asString());
    //jsonPathEvaluator.getInt("data.size()");  --- 5 (There are 5 attributes)
//        {
//            "attribute1": "value1",
//            "attribute2": "value2",
//            "attribute3": "value3",
//            "attribute4": "value4",
//            "attribute5": [  //jsonPathEvaluator.getInt("attribute5.size()");  --- 2 (There are 2 objects of attribute5 array)
//                //Object_01 (attribute5[0])
//                {
//                    "attribute5_1": "value5_1",
//                    "attribute5_2": "value5_2"
//                },
//                //Object_01 (attribute5[0])
//                {
//                    "attribute5_3": "value5_3",
//                    "attribute5_4": "value5_4"
//                }
//            ],
//            "attribute6": null
//        }
    //data --- Ending point

    //After getting the size of the date, we have to assert that the response body attributes are retrieved or not.

    //Here we validate the Attribute Keys of Response Body Payload exists or not
//        ValidatableResponse validatableRes = response.then();
//        validatableRes.body("$", hasKey("attribute1"));
//        validatableRes.body("$", hasKey("attribute2"));
//        validatableRes.body("$", hasKey("attribute3"));
//        validatableRes.body("$", hasKey("attribute4"));
//        validatableRes.body("$", hasKey("attribute5"));
//        validatableRes.body("$", not(hasKey("attribute10")));
//
//        //Here we validate the corresponding Attribute Values for the Attribute Keys are null or not null
//        validatableRes.body("attribute1", is(notNullValue()));
//        validatableRes.body("attribute2", is(notNullValue()));
//        validatableRes.body("attribute3", is(notNullValue()));
//        validatableRes.body("attribute4", is(notNullValue()));
//        validatableRes.body("attribute5", is(notNullValue()));
//        validatableRes.body("attribute6", is(nullValue()));
//
//        //Here we validate the corresponding Attribute Values for the Attribute Keys
//        validatableRes.body("id", hasValue("348d9..."));
//        validatableRes.body("attribute2", hasValue("value2"));
//        validatableRes.body("attribute3", hasValue("value3"));
//        validatableRes.body("attribute4", hasValue("value4"));
//        validatableRes.body("attribute5[0]", hasKey("attribute5_1"));
//        validatableRes.body("attribute5[0]", hasKey("attribute5_2"));
//        validatableRes.body("attribute5[1]", hasKey("attribute5_3"));
//        validatableRes.body("attribute5[1]", hasKey("attribute5_4"));
//        validatableRes.body("attribute5[0].attribute5_1", hasValue("value5_1"));
//        validatableRes.body("attribute5[0].attribute5_2", hasValue("value5_2"));
//        validatableRes.body("attribute5[1].attribute5_3", hasValue("value5_3"));
//        validatableRes.body("attribute5[1].attribute5_4", hasValue("value5_4"));
//        validatableRes.body("attribute6", hasValue(null));

////////////////////////////////////////////////////
    //Array of Objects

    //data --- Starting point
    //JsonPath jsonPathEvaluator = null;
    //jsonPathEvaluator = new JsonPath(response.asString());
    //jsonPathEvaluator.getInt("data.size()");  --- 2 (There are 2 objects of data array)
//        [
//            {
//                "attribute1": "value",
//                "attribute2": "value",
//                "attribute3": "value",
//                "attribute4": "value",
//                "attribute5": [
//                    {
//                        "attribute2": "value",
//                        "attribute3": "value"
//                    },
//                    {
//                        "attribute2": "value",
//                        "attribute3": "value"
//                    }
//               ]
//            },
//            {
//                "attribute1": "value",
//                "attribute2": "value",
//                "attribute3": "value",
//                "attribute4": "value",
//                "attribute5": [
//                    {
//                        "attribute2": "value",
//                         "attribute3": "value"
//                    },
//                    {
//                        "attribute2": "value",
//                            "attribute3": "value"
//                    }
//                ]
//            }
//        ]
    //data --- Ending point

/////////////////////////////////////////////////////////

//        Map<String, Object> user1Addresses = new HashMap<String, Object>();
//        user1Addresses.put("addressType", "Work");
//        user1Addresses.put("postalCode", "11532");
//        user1Addresses.put("country", "USA");
//        user1Addresses.put("addressLine1", "450 Jamaica Ave");
//        user1Addresses.put("addressLine2", "USA");
//        user1Addresses.put("city", "Jamaica");
//        user1Addresses.put("stateProv", "New York");
//
//        List<Map<String, Object>> user1AddressesList = new ArrayList<>();
//        user1AddressesList.add(user1Addresses);
//
//        Gson gson = new Gson();
//        String addressesPayload = gson.toJson(user1AddressesList);
//
//        Map<String, Object> user1PhoneNumbers = new HashMap<String, Object>();
//        user1Addresses.put("phoneType", "Day");
//        user1Addresses.put("phoneNumber", "646-987-8830");
//
//        List<Map<String, Object>> user1PhoneNumbersList = new ArrayList<>();
//        user1PhoneNumbersList.add(user1PhoneNumbers);
//
//        String phoneNumbersPayload = gson.toJson(user1PhoneNumbersList);
//
//        Map<String, Object> createUserPayload = new HashMap<String, Object>();
//        createUserPayload.put("firstName", "John");
//        createUserPayload.put("middleName", "J.");
//        createUserPayload.put("lastName", "Wick");
//        createUserPayload.put("email", "johnwick@gmail.com");
//        createUserPayload.put("addresses", addressesPayload);
//        createUserPayload.put("phoneNumbers", phoneNumbersPayload);
//        createUserPayload.put("partnerHomeId", "50040005");
//        createUserPayload.put("partnerId", "50040000");
//        createUserPayload.put("role", "Customer");
//        createUserPayload.put("username", "johnwick5021");
//        createUserPayload.put("state", "Active");
//        createUserPayload.put("creationDate", "2022-03-24T16:17:41.920466");
//        createUserPayload.put("lastModifiedDate", "2022-03-24T16:17:41.920466");
//        createUserPayload.put("createdBy", null);
//        createUserPayload.put("lastModifiedBy", null);
//        createUserPayload.put("heritagePersonId", "2092111");
//
//        response = given().
//                body(createUserPayload).
//                contentType("application/json").
//                when().
//                post(baseUrl + "/user/partnerId/{partnerId}/home/{homeId}").
//                then().
//                assertThat().
//                statusCode(200).
//                log().body().extract().response();
//
//        System.out.println(response);
//        jsonPathEvaluator = new JsonPath(response.asString());
//
//        int responsePayloadDataSize = jsonPathEvaluator.getInt("data.size()");
//        assertEquals(responsePayloadDataSize, 17);
//
//        //After getting the size of the date, we have to assert that the response body attributes are retrieved or not.
//
//        //Here we validate the Attribute Keys of Response Body Payload exists or not
//        ValidatableResponse validatableRes = response.then();
//        validatableRes.body("$", hasKey("id"));
//        validatableRes.body("$", hasKey("firstName"));
//        validatableRes.body("$", hasKey("middleName"));
//        validatableRes.body("$", hasKey("lastName"));
//        validatableRes.body("$", hasKey("email"));
//        validatableRes.body("$", hasKey("addresses"));
//        validatableRes.body("$", hasKey("phoneNumbers"));
//        validatableRes.body("$", hasKey("partnerHomeId"));
//        validatableRes.body("$", hasKey("partnerId"));
//        validatableRes.body("$", hasKey("role"));
//        validatableRes.body("$", hasKey("username"));
//        validatableRes.body("$", hasKey("state"));
//        validatableRes.body("$", hasKey("creationDate"));
//        validatableRes.body("$", hasKey("lastModifiedDate"));
//        validatableRes.body("$", hasKey("createdBy"));
//        validatableRes.body("$", hasKey("lastModifiedDate"));
//        validatableRes.body("$", hasKey("heritagePersonId"));
//
//        //Invalidity Check for some Attributes that the server/API should not show in Response Body Payload
//        validatableRes.body("$", not(hasKey("homeId")));
//        validatableRes.body("$", not(hasKey("userId")));
//
//        //Here we validate the corresponding Attribute Values for the Attribute Keys are null or not null
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(notNullValue()));
//        validatableRes.body("$", is(nullValue()));
//        validatableRes.body("$", is(nullValue()));
//        validatableRes.body("$", is(notNullValue()));
//
//        //Here we validate the corresponding Attribute Values for the Attribute Keys
//        validatableRes.body("id", hasValue("348d9...."));
//        validatableRes.body("firstName", hasValue("John"));
//        validatableRes.body("middleName", hasValue("J."));
//        validatableRes.body("lastName", hasValue("Wick"));
//        validatableRes.body("email", hasValue("johnwick@gmail.com"));
//
//        validatableRes.body("addresses[0]", hasKey("addressType"));
//        validatableRes.body("addresses[0]", hasKey("postalCode"));
//        validatableRes.body("addresses[0]", hasKey("country"));
//        validatableRes.body("addresses[0]", hasKey("addressLine1"));
//        validatableRes.body("addresses[0]", hasKey("addressLine2"));
//        validatableRes.body("addresses[0]", hasKey("city"));
//        validatableRes.body("addresses[0]", hasKey("stateProv"));
//        validatableRes.body("addresses[0].addressType", hasValue("Work"));
//        validatableRes.body("addresses[0].postalCode", hasValue("11532"));
//        validatableRes.body("addresses[0].country", hasValue("USA"));
//        validatableRes.body("addresses[0].addressLine1", hasValue("450 Jamaica Ave"));
//        validatableRes.body("addresses[0].addressLine2", hasValue("USA"));
//        validatableRes.body("addresses[0].city", hasValue("Jamaica"));
//        validatableRes.body("addresses[0].stateProv", hasValue("New York"));
//
//        validatableRes.body("phoneNumbers[0]", hasKey("phoneType"));
//        validatableRes.body("phoneNumbers[0]", hasKey("phoneNumber"));
//        validatableRes.body("phoneNumbers[0].phoneType", hasValue("Day"));
//        validatableRes.body("phoneNumbers[0].phoneNumber", hasValue("646-987-8830"));
//
//        validatableRes.body("partnerHomeId", hasValue("50040005"));
//        validatableRes.body("partnerId", hasValue("50040000"));
//        validatableRes.body("role", hasValue("Customer"));
//        validatableRes.body("username", hasValue("johnwick5021"));
//        validatableRes.body("state", hasValue("Active"));
//        validatableRes.body("creationDate", hasValue("2022-03-24T16:17:41.920466"));
//        validatableRes.body("lastModifiedDate", hasValue("2022-03-24T16:20:50.920466"));
//        validatableRes.body("createdBy", hasValue(null));
//        validatableRes.body("lastModifiedBy", hasValue(null));
//        validatableRes.body("heritagePersonId", hasValue("2092111"));
//    }


//
//    //Valid_AISVMW_9
//    @Test
//    public void vaildUpdateAclServiceWithAddtionalCriterionOfModificationDataRange(){
//        Map<String, Object> modificationDataRangePayLoad = new HashMap<String, Object>();
//        modificationDataRangePayLoad.put("startDate", "2020-11-29T00:00:00");
//        modificationDataRangePayLoad.put("endDate", "2020-11-31T00:00:00");
//
//        requestBody.put("partnerId", "123");
//        requestBody.put("homeId", "456");
//        requestBody.put("modificationDataRange", modificationDataRangePayLoad);
//
//        response = given().
//                body(requestBody.toJSONString()).
//                contentType("application/json").
//                when().
//                post(baseUrl + additionCriterionModificationDataRangeEndPoint).
//                then().
//                assertThat().
//                statusCode(200).log().body().extract().response();
//
//        jsonPathEvaluator = response.jsonPath();
//        String idValue = jsonPathEvaluator.get("id");
//        String nameValue = jsonPathEvaluator.get("name");
//        String countryValue = jsonPathEvaluator.get("country");
//        String loginUrlValue = jsonPathEvaluator.get("loginUrl");
//        String harvestStatusValue = jsonPathEvaluator.get("harvestStatus");
//        String stateValue = jsonPathEvaluator.get("state");
//        String mfaEnabledValue = jsonPathEvaluator.get("mfaEnabled");
//        String availabilityStatusValue = jsonPathEvaluator.get("availabilityStatus");
//        String extendedServiceDetailsValue = jsonPathEvaluator.get("extendedServiceDetails");
//        String supportedAccountTypesValue = jsonPathEvaluator.get("supportedAccountTypes");
//        String loginParameterInfoValue = jsonPathEvaluator.get("loginParameterInfo");
//        String auditColumnsValue = jsonPathEvaluator.get("auditColumns");
//
//        System.out.println("The value of id attribute is - " + idValue);
//        System.out.println("The value of name attribute is - " + nameValue);
//        System.out.println("The value of country attribute is - " + countryValue);
//        System.out.println("The value of loginUrl attribute is - " + loginUrlValue);
//        System.out.println("The value of harvestStatus attribute is - " + harvestStatusValue);
//        System.out.println("The value of state attribute is - " + stateValue);
//        System.out.println("The value of mfaEnabled attribute is - " + mfaEnabledValue);
//        System.out.println("The value of availabilityStatus attribute is - " + availabilityStatusValue);
//        System.out.println("The value of extendedServiceDetails attribute is - " + extendedServiceDetailsValue);
//        System.out.println("The value of supportedAccountTypes attribute is - " + supportedAccountTypesValue);
//        System.out.println("The value of loginParameterInfo attribute is - " + loginParameterInfoValue);
//        System.out.println("The value of auditColumns attribute is - " + auditColumnsValue);
//
//        assertEquals(idValue, "");
//        assertEquals(nameValue, "");
//        assertEquals(countryValue, "");
//        assertEquals(loginUrlValue, "");
//        assertEquals(harvestStatusValue, "");
//        assertEquals(stateValue, "");
//        assertEquals(mfaEnabledValue, "");
//        assertEquals(availabilityStatusValue, "");
//        assertEquals(extendedServiceDetailsValue, "");
//        assertEquals(supportedAccountTypesValue, "");
//        assertEquals(loginParameterInfoValue, "");
//        assertEquals(auditColumnsValue, "");
//    }
//
//    //Invalid_AISVMW_9
//    @Test
//    public void invalidUpdateAclServiceWithAddtionalCriterionOfModificationDataRange(){
//        Map<String, Object> modificationDataRangePayLoad = new HashMap<String, Object>();
//        modificationDataRangePayLoad.put("startDate", "2020-11-31T00:00:00");
//        modificationDataRangePayLoad.put("endDate", "2020-11-29T00:00:00");
//
//        requestBody.put("partnerId", "123");
//        requestBody.put("homeId", "456");
//        requestBody.put("modificationDataRange", modificationDataRangePayLoad);
//
//        response = given().
//                body(requestBody.toJSONString()).
//                contentType("application/json").
//                when().
//                post(baseUrl + additionCriterionModificationDataRangeEndPoint).
//                then().
//                assertThat().
//                statusCode(400).log().body().extract().response();
//
//        jsonPathEvaluator = response.jsonPath();
//        String codeValue = jsonPathEvaluator.get("code");
//        String detailsValue = jsonPathEvaluator.get("details");
//
//        System.out.println("The value of id attribute is - " + codeValue);
//        System.out.println("The value of name attribute is - " + detailsValue);
//
//        assertEquals(codeValue, "REQUEST_INVALID");
//        assertEquals(detailsValue, null);
//    }
}













