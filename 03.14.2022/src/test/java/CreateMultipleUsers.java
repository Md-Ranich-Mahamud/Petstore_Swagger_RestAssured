//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
//import junit.framework.Assert;
//import org.json.simple.JSONObject;
//import org.testng.annotations.Test;
//
//import java.util.*;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.junit.Assert.assertThat;
//
//public class CreateMultipleUsers {
//    public static String baseURL = "https://petstore.swagger.io/v2";
//    public static String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//    public static String id;
//    public static String randomUserName;
//
//    private static String generateRandomChars(String candidateChars, int length) {
//        StringBuilder sb = new StringBuilder();
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
//        }
//        return sb.toString();
//    }
//
//    @Test(priority = 1)
//    public static void createUserInUserService(String phoneType, String phoneNumber, String AddressType, String PostalCode, String Country, String AddressLine1, String AddressLine2, String City, String State, String FirstName, String MiddleName, String LastName, String Email, String role, String Password) {
//        Object randomUserName = "username" + generateRandomChars(candidateChars, 4);
//
//        HashMap<String, String> phoneHashMap = new HashMap<>();
//        phoneHashMap.put("phoneType", phoneType);
//        //phoneHashMap.put("phoneType", "Day");
//        phoneHashMap.put("phoneNumber", phoneNumber);
//        //phoneHashMap.put("phoneNumber","6368765678");
//
//        List<HashMap<String, String>> phoneNumberList = new ArrayList<>();
//        phoneNumberList.add(phoneHashMap);
//
//        HashMap<String, String> addressHashMap = new HashMap<>();
//        addressHashMap.put("AddressType", AddressType);
//        // addressHashMap.put("AddressType","Work");
//        addressHashMap.put("PostalCode", PostalCode);
//        //addressHashMap.put("PostalCode","12345");
//        addressHashMap.put("Country", Country);
//        // addressHashMap.put("Country","USA");
//        addressHashMap.put("AddressLine1", AddressLine1);
//        //addressHashMap.put("AddressLine1","123 Queens Ave");
//        addressHashMap.put("AddressLine2", AddressLine2);
//        //addressHashMap.put("AddressLine2","apt #301");
//        addressHashMap.put("City", City);
//        //addressHashMap.put("City","Brooklyn");
//        addressHashMap.put("State", State);
//        //addressHashMap.put("State","NYC");
//
//        List<HashMap<String, String>> addressList = new ArrayList<>();
//        addressList.add(addressHashMap);
//
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("FirstName", FirstName);
//        //data.put("FirstName","John");
//        data.put("MiddleName", MiddleName);
//        //data.put("MiddleName","M");
//        data.put("LastName", LastName);
//        // data.put("LastName","Junior");
//        data.put("Email", Email);
//        //data.put("Email","john@gmail.com");
//        data.put("Address", addressList);
//        data.put("phoneNumber", phoneNumberList);
//        data.put("role", role);
//        // data.put("role","CUSTOMER");
//        data.put("UserName", randomUserName);
//        data.put("Password", Password);
//        // data.put("Password","Cashed123");
//
//        Response response =
//                given().
//                        pathParam("PartnerId", "900000").
//                        pathParam("homeId", "9000001").
//                        contentType("application/json").
//                        body(data).
//                        when().
//                        post(baseURL + "endpoint").
//                        then().statusCode(200).log().
//                        body().extract().response();
//
//        System.out.println(response.getBody().prettyPrint());
//
//        String jsonString = response.asString();
//        Assert.assertEquals(jsonString.contains("patnerHomeId"), true);
//
//        JsonPath jsonPathEvaluator = response.jsonPath();
//        id = jsonPathEvaluator.get("id");
//        System.out.println("id from response is " + id);
//        String state = jsonPathEvaluator.get("state");
//        assertThat(state, equalTo("ACTIVE"));
//    }
//
//    public static void main(String[] args){
//        Scanner scanner = new Scanner(System.in);
//
//        for(int i=0; i<2; i++){
//            System.out.print("Enter phoneType : ");
//            String phoneType = scanner.nextLine();
//
//            System.out.print("Enter phoneNumber : ");
//            String phoneNumber = scanner.nextLine();
//
//            System.out.print("Enter AddressType : ");
//            String AddressType = scanner.nextLine();
//
//            System.out.print("Enter PostalCode : ");
//            String PostalCode = scanner.nextLine();
//
//            System.out.print("Enter Country : ");
//            String Country = scanner.nextLine();
//
//            System.out.print("Enter AddressLine1 : ");
//            String AddressLine1 = scanner.nextLine();
//
//            System.out.print("Enter AddressLine2 : ");
//            String AddressLine2 = scanner.nextLine();
//
//            System.out.print("Enter City : ");
//            String City = scanner.nextLine();
//
//            System.out.print("Enter State : ");
//            String State = scanner.nextLine();
//
//            System.out.print("Enter FirstName : ");
//            String FirstName = scanner.nextLine();
//
//            System.out.print("Enter MiddleName : ");
//            String MiddleName = scanner.nextLine();
//
//            System.out.print("Enter LastName : ");
//            String LastName = scanner.nextLine();
//
//            System.out.print("Enter Email : ");
//            String Email = scanner.nextLine();
//
//            System.out.print("Enter role : ");
//            String role = scanner.nextLine();
//
//            System.out.print("Enter Password : ");
//            String Password = scanner.nextLine();
//
//            createUserInUserService(phoneType, phoneNumber, AddressType, PostalCode, Country, AddressLine1, AddressLine2, City, State, FirstName, MiddleName, LastName, Email, role, Password);
//        }
//    }
//}
