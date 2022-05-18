import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class PetOperations {
    public final String baseUrl = "https://petstore.swagger.io/v2";
    JSONObject payload = new JSONObject();
    Response response = null;
    JsonPath jsonPathEvaluator = null;

    @Test(priority = 1)
    public void addANewPetInTheStore() {
        Map<String, Object> petTags01 = new HashMap<String, Object>();
        petTags01.put("id", 300);
        petTags01.put("name", "Cute pet");

        Map<String, Object> petTags02 = new HashMap<String, Object>();
        petTags02.put("id", 301);
        petTags02.put("name", "Beautiful pet");

        List<Map<String, Object>> petTagsList = new ArrayList<Map<String, Object>>();
        petTagsList.add(petTags01);
        petTagsList.add(petTags02);

        Map<String, Object> petCategory = new HashMap<String, Object>();
        petCategory.put("id", 200);
        petCategory.put("name", "Indoor Pet");

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("id", 100);
        payload.put("category", petCategory);
        payload.put("name", "Doggie Erik");
        payload.put("photoUrls", Arrays.asList("C:\\Users\\mdran\\Pictures\\Doggie\\doggie1.jpg", "C:\\Users\\mdran\\Pictures\\Doggie\\doggie2.jpg"));
        payload.put("tags", petTagsList);
        payload.put("status", "pending");

        JSONObject requestPayload = new JSONObject(payload);

        Response response =
                given().
                        body(requestPayload.toJSONString()).
                        contentType("application/json").
                        when().
                        post(baseUrl + "/pet").
                        then().
                        assertThat().
                        statusCode(200).log().body().extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        System.out.println(jsonPathEvaluator);

        ValidatableResponse validatableResponse = response.then();
        validatableResponse.body("$", hasKey("id"));
        validatableResponse.body("$", hasKey("category"));
        validatableResponse.body("$", hasKey("name"));
        validatableResponse.body("$", hasKey("photoUrls"));
        validatableResponse.body("$", hasKey("tags"));
        validatableResponse.body("$", hasKey("status"));

        validatableResponse.body("id", is(notNullValue()));
        validatableResponse.body("category", is(notNullValue()));
        validatableResponse.body("name", is(notNullValue()));
        validatableResponse.body("photoUrls", is(notNullValue()));
        validatableResponse.body("tags", is(notNullValue()));
        validatableResponse.body("status", is(notNullValue()));

        int valueOfId = jsonPathEvaluator.get("id");
        assertEquals(100, valueOfId);
        int valueOfCategoryId = jsonPathEvaluator.get("category.id");
        assertEquals(200, valueOfCategoryId);
        String valueOfCategoryName = jsonPathEvaluator.get("category.name");
        assertEquals("Indoor Pet", valueOfCategoryName);
        String valueOfName = jsonPathEvaluator.get("name");
        assertEquals("Doggie Erik", valueOfName);
        String photo1OfPet = jsonPathEvaluator.get("photoUrls[0]");
        assertEquals("C:\\Users\\mdran\\Pictures\\Doggie\\doggie1.jpg", photo1OfPet);
        String photo2OfPet = jsonPathEvaluator.get("photoUrls[1]");
        assertEquals("C:\\Users\\mdran\\Pictures\\Doggie\\doggie2.jpg", photo2OfPet);
        int valueOfTags1Id = jsonPathEvaluator.get("tags[0].id");
        assertEquals(300, valueOfTags1Id);
        String valueOfTags1Name = jsonPathEvaluator.get("tags[0].name");
        assertEquals("Cute pet", valueOfTags1Name);
        int valueOfTags2Id = jsonPathEvaluator.get("tags[1].id");
        assertEquals(301, valueOfTags2Id);
        String valueOfTags2Name = jsonPathEvaluator.get("tags[1].name");
        assertEquals("Beautiful pet", valueOfTags2Name);
        String valueOfStatus = jsonPathEvaluator.get("status");
        assertEquals("pending", valueOfStatus);
    }

    @Test(priority = 2)
    public void updateAnExistingPet() {
        Map<String, Object> petTags01 = new HashMap<String, Object>();
        petTags01.put("id", 300);
        petTags01.put("name", "Cute pet");

        Map<String, Object> petTags02 = new HashMap<String, Object>();
        petTags02.put("id", 301);
        petTags02.put("name", "Beautiful pet");

        List<Map<String, Object>> petTagsList = new ArrayList<Map<String, Object>>();
        petTagsList.add(petTags01);
        petTagsList.add(petTags02);

        Map<String, Object> petCategory = new HashMap<String, Object>();
        petCategory.put("id", 200);
        petCategory.put("name", "Indoor Pet");

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("id", 100);
        payload.put("category", petCategory);
        payload.put("name", "Pet Doggie");
        payload.put("photoUrls", Arrays.asList("C:\\Users\\mdran\\Pictures\\Doggie\\doggie1.jpg", "C:\\Users\\mdran\\Pictures\\Doggie\\doggie2.jpg"));
        payload.put("tags", petTagsList);
        payload.put("status", "available");

        JSONObject requestPayload = new JSONObject(payload);

        Response response =
                given().
                        body(requestPayload.toJSONString()).
                        contentType("application/json").
                        when().
                        put(baseUrl + "/pet").
                        then().
                        assertThat().
                        statusCode(200).log().body().extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        System.out.println(jsonPathEvaluator);

        ValidatableResponse validatableResponse = response.then();
        validatableResponse.body("$", hasKey("id"));
        validatableResponse.body("$", hasKey("category"));
        validatableResponse.body("$", hasKey("name"));
        validatableResponse.body("$", hasKey("photoUrls"));
        validatableResponse.body("$", hasKey("tags"));
        validatableResponse.body("$", hasKey("status"));

        validatableResponse.body("id", is(notNullValue()));
        validatableResponse.body("category", is(notNullValue()));
        validatableResponse.body("name", is(notNullValue()));
        validatableResponse.body("photoUrls", is(notNullValue()));
        validatableResponse.body("tags", is(notNullValue()));
        validatableResponse.body("status", is(notNullValue()));

        int valueOfId = jsonPathEvaluator.get("id");
        assertEquals(100, valueOfId);
        int valueOfCategoryId = jsonPathEvaluator.get("category.id");
        assertEquals(200, valueOfCategoryId);
        String valueOfCategoryName = jsonPathEvaluator.get("category.name");
        assertEquals("Indoor Pet", valueOfCategoryName);
        String valueOfName = jsonPathEvaluator.get("name");
        assertEquals("Pet Doggie", valueOfName);
        String photo1OfPet = jsonPathEvaluator.get("photoUrls[0]");
        assertEquals("C:\\Users\\mdran\\Pictures\\Doggie\\doggie1.jpg", photo1OfPet);
        String photo2OfPet = jsonPathEvaluator.get("photoUrls[1]");
        assertEquals("C:\\Users\\mdran\\Pictures\\Doggie\\doggie2.jpg", photo2OfPet);
        int valueOfTags1Id = jsonPathEvaluator.get("tags[0].id");
        assertEquals(300, valueOfTags1Id);
        String valueOfTags1Name = jsonPathEvaluator.get("tags[0].name");
        assertEquals("Cute pet", valueOfTags1Name);
        int valueOfTags2Id = jsonPathEvaluator.get("tags[1].id");
        assertEquals(301, valueOfTags2Id);
        String valueOfTags2Name = jsonPathEvaluator.get("tags[1].name");
        assertEquals("Beautiful pet", valueOfTags2Name);
        String valueOfStatus = jsonPathEvaluator.get("status");
        assertEquals("available", valueOfStatus);
    }

    @Test(priority = 3)
    public void updateAPetInTheStoreWithFormData() {
        Response res = given().
                config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT))).
                pathParam("petId", 100).
                formParam("name", "Doggie").
                formParam("status", "sold").
                when().
                post(baseUrl + "/pet/{petId}").
                then().
                assertThat().
                statusCode(200).log().all().extract().response();

        JsonPath jsonPathEvalualor = res.jsonPath();

        ValidatableResponse validatableResponse = res.then();
        validatableResponse.body("$", hasKey("code"));
        validatableResponse.body("$", hasKey("type"));
        validatableResponse.body("$", hasKey("message"));

        validatableResponse.body("code", is(notNullValue()));
        validatableResponse.body("type", is(notNullValue()));
        validatableResponse.body("message", is(notNullValue()));

        int valueOfCode = jsonPathEvalualor.get("code");
        assertEquals(200, valueOfCode);
        String valueOfType = jsonPathEvalualor.get("type");
        assertEquals("unknown", valueOfType);
        String valueOfMessage = jsonPathEvalualor.get("message");
        assertEquals("100", valueOfMessage);
    }

    @Test(priority = 4)
    public void uploadAnImageForPet(){
        Response response = given().
            pathParam("petId", 100).
            multiPart("additionalMetadata", "Doggie is cute.").
            multiPart("file", new File("C:/Users/mdran/Pictures/Doggie/doggie2.jpg")).
        when().
            post(baseUrl + "/pet/{petId}/uploadImage").
        then().assertThat().statusCode(200).log().all().extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        System.out.println(jsonPathEvaluator);

        ValidatableResponse validatableResponse = response.then();
        validatableResponse.body("$", hasKey("code"));
        validatableResponse.body("$", hasKey("type"));
        validatableResponse.body("$", hasKey("message"));

        validatableResponse.body("code", is(notNullValue()));
        validatableResponse.body("type", is(notNullValue()));
        validatableResponse.body("message", is(notNullValue()));

        int code = jsonPathEvaluator.get("code");
        assertEquals(200, code);

        String type = jsonPathEvaluator.get("type");
        assertEquals("unknown", type);

        String message = jsonPathEvaluator.get("message");
        assertTrue(message.contains("additionalMetadata: Doggie is cute."));
    }

    @Test(priority = 5)
    public void findsPetsByStatus(){
        Response response = given().
            queryParam("status", "pending").
        when().
            get(baseUrl + "/pet/findByStatus").
        then().
            assertThat().
            statusCode(200).
            extract().response();

        List<Long> ids = new ArrayList<Long>();
        ids = JsonPath.from(response.getBody().asString()).get("id");
        System.out.println(ids);

        List<Long> categoryIds = new ArrayList<Long>();
        categoryIds = JsonPath.from(response.getBody().asString()).get("category.id");
        System.out.println(categoryIds);

        List<Long> categoryNames = new ArrayList<Long>();
        categoryNames = JsonPath.from(response.getBody().asString()).get("category.name");
        System.out.println(categoryNames);

        List<String> names = new ArrayList<String>();
        names = JsonPath.from(response.getBody().asString()).get("name");
        System.out.println(names);

        List<String> photoUrls = new ArrayList<String>();
        photoUrls = JsonPath.from(response.getBody().asString()).get("photoUrls");
        System.out.println(photoUrls);

        List<String> statuses = new ArrayList<String>();
        statuses = JsonPath.from(response.getBody().asString()).get("status");
        System.out.println(statuses);

        for (Object id : ids) {
            if(id == (Object) 100){
                int expectedId = ids.indexOf(id);

                Map<String, Object> categoryPayload = new HashMap<String, Object>();
                categoryPayload.put("id", categoryIds.get(expectedId));
                categoryPayload.put("name", categoryNames.get(expectedId));

                List<Long> tagIds = new ArrayList<Long>();
                tagIds = JsonPath.from(response.getBody().asString()).get("tags[" + expectedId + "].id");
                System.out.println(tagIds);

                List<String> tagNames = new ArrayList<String>();
                tagNames = JsonPath.from(response.getBody().asString()).get("tags[" + expectedId + "].name");
                System.out.println(tagNames);

                Map<String, Object> tagsPayload1 = new HashMap<String, Object>();
                tagsPayload1.put("id", tagIds.get(0));
                tagsPayload1.put("name", tagNames.get(0));

                Map<String, Object> tagsPayload2 = new HashMap<String, Object>();
                tagsPayload2.put("id", tagIds.get(1));
                tagsPayload2.put("name", tagNames.get(1));

                List<Map<String, Object>> tagsPayload = new ArrayList<Map<String, Object>>();
                tagsPayload.add(tagsPayload1);
                tagsPayload.add(tagsPayload2);

                Map<String, Object> payload = new HashMap<String, Object>();
                payload.put("id", id);
                payload.put("category", categoryPayload);
                payload.put("name", names.get(expectedId));
                payload.put("photoUrls", photoUrls.get(expectedId));
                payload.put("tags", tagsPayload);
                payload.put("status", statuses.get(expectedId));

                JSONObject jsonObject = new JSONObject(payload);
                System.out.println(jsonObject.toJSONString());
            }
        }
    }

    @Test(priority = 6)
    public void findPetById(){
        Response res = given().
            pathParam("petId", 100).
        when().
            get(baseUrl + "/pet/{petId}").
        then().
            assertThat().
            statusCode(200).
            extract().response();

        JsonPath jsonPathEvaluator = res.jsonPath();
        System.out.println(jsonPathEvaluator);

        ValidatableResponse validatableResponse = res.then();
        validatableResponse.body("$", hasKey("id"));
        validatableResponse.body("$", hasKey("category"));
        validatableResponse.body("$", hasKey("name"));
        validatableResponse.body("$", hasKey("photoUrls"));
        validatableResponse.body("$", hasKey("tags"));
        validatableResponse.body("$", hasKey("status"));

        validatableResponse.body("id", is(notNullValue()));
        validatableResponse.body("category", is(notNullValue()));
        validatableResponse.body("name", is(notNullValue()));
        validatableResponse.body("photoUrls", is(notNullValue()));
        validatableResponse.body("tags", is(notNullValue()));
        validatableResponse.body("status", is(notNullValue()));

        int valueOfId = jsonPathEvaluator.get("id");
        assertEquals(100, valueOfId);
        int valueOfCategoryId = jsonPathEvaluator.get("category.id");
        assertEquals(200, valueOfCategoryId);
        String valueOfCategoryName = jsonPathEvaluator.get("category.name");
        assertEquals("Indoor Pet", valueOfCategoryName);
        String valueOfName = jsonPathEvaluator.get("name");
        assertEquals("Doggie Erik", valueOfName);
        String photo1OfPet = jsonPathEvaluator.get("photoUrls[0]");
        assertEquals("C:\\Users\\mdran\\Pictures\\Doggie\\doggie1.jpg", photo1OfPet);
        String photo2OfPet = jsonPathEvaluator.get("photoUrls[1]");
        assertEquals("C:\\Users\\mdran\\Pictures\\Doggie\\doggie2.jpg", photo2OfPet);
        int valueOfTags1Id = jsonPathEvaluator.get("tags[0].id");
        assertEquals(300, valueOfTags1Id);
        String valueOfTags1Name = jsonPathEvaluator.get("tags[0].name");
        assertEquals("Cute pet", valueOfTags1Name);
        int valueOfTags2Id = jsonPathEvaluator.get("tags[1].id");
        assertEquals(301, valueOfTags2Id);
        String valueOfTags2Name = jsonPathEvaluator.get("tags[1].name");
        assertEquals("Beautiful pet", valueOfTags2Name);
        String valueOfStatus = jsonPathEvaluator.get("status");
        assertEquals("pending", valueOfStatus);
    }

    @Test(priority = 7)
    public void deletesAPet(){
        Response res = given().
            header("api_key", "special-key").
            pathParam("petId", 101).
        when().
            delete(baseUrl + "/pet/{petId}").
        then().
            assertThat().
            statusCode(400).
            extract().response();

        JsonPath jsonPathEvaluator = res.jsonPath();
        System.out.println(jsonPathEvaluator);

        ValidatableResponse validatableResponse = res.then();
        validatableResponse.body("$", hasKey("code"));
        validatableResponse.body("$", hasKey("type"));
        validatableResponse.body("$", hasKey("message"));

        validatableResponse.body("code", is(notNullValue()));
        validatableResponse.body("type", is(notNullValue()));
        validatableResponse.body("message", is(notNullValue()));

        int code = jsonPathEvaluator.get("code");
        assertEquals(200, code);
        String type = jsonPathEvaluator.get("type");
        assertEquals("unknown", type);
        String message = jsonPathEvaluator.get("message");
        assertTrue(message.contains("100"));
    }
}