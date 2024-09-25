package com.Platform.ApiEndpoints.Controller;

import com.Platform.ApiEndpoints.DTO.RequestDTO.SaveRequest;
import com.Platform.ApiEndpoints.Model.UserLogin;
import com.Platform.ApiEndpoints.Repository.LoginRepo;
import com.Platform.ApiEndpoints.Service.Impl.KafkaProducerServiceImpl;
import com.Platform.ApiEndpoints.Service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/flink")
public class MainController {

    @Autowired
    KafkaProducerServiceImpl serv;

    @Autowired
    LoginRepo repo;

    @PostMapping("/save")
    public ResponseEntity messageListener(@RequestBody SaveRequest reqBody, @RequestParam("key") String key){
        String response=serv.addMessage("user-logins", reqBody, key);
        System.out.println(reqBody.getUser_id()+" "+reqBody.getUser_name()+" "+reqBody.getLogin_type());// Replace with your topic name
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/toPostgres")
    public ResponseEntity postgres(@RequestParam("name") String name,@RequestParam("id") String id,@RequestParam("login_type") String login_type){
        System.out.println("----------In postgres Controller----------");
        try {
            UserLogin model = com.Platform.ApiEndpoints.Transformer.RequestToEntity.UserLogin.transform(id, name, login_type);
            repo.save(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity("Entity Added", HttpStatus.OK);
    }


    @PostMapping("/invoke")
    public CompletableFuture<Void> invokeFlinkFunction(@RequestBody SaveRequest reqBody, @RequestParam("key") String key) {
        // Create a message to send to the Flink Stateful Function
        try {
            UserLogin model = com.Platform.ApiEndpoints.Transformer.RequestToEntity.UserLogin.transform(reqBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(flinkFunctionUrl);
            httpPost.setEntity(new StringEntity(jsonPayload, ContentType.APPLICATION_JSON));

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // Check the response status
                if (response.getStatusLine().getStatusCode() == 200) {
                    System.out.println("Flink function invoked successfully.");
                } else {
                    System.out.println("Failed to invoke Flink function: " + response.getStatusLine());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while invoking Flink function: " + e.getMessage(), e);
        }


        // Create a context to call your Flink function
        Context context = functionFactory.createContext();
        return context.apply(message);
    }
}
