package com.andrelopes.transaction_api.services;

import com.andrelopes.transaction_api.dtos.TransactionDTO;
import com.andrelopes.transaction_api.models.UserModel;
import com.andrelopes.transaction_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void saveTransaction(TransactionDTO transactionDTO) throws Exception {

        UserModel payerUser = userRepository.findById(transactionDTO.payer()).orElse(null);

        assert payerUser != null;
        if (transactionDTO.value()<=payerUser.getUserBalance() && getAuthorization()){
            UserModel payeeUser = userRepository.findById(transactionDTO.payee()).orElse(null);

            payerUser.setUserBalance(payerUser.getUserBalance()-transactionDTO.value());

            assert payeeUser != null;
            payeeUser.setUserBalance(payeeUser.getUserBalance()+transactionDTO.value());

            sendNotification(payeeUser);
        }
    }

    private Boolean getAuthorization() throws InterruptedException, IOException, JSONException {

        boolean authorized = false;

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc"))
                .GET()
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());
        String responseStr = String.valueOf(jsonObject.get("message"));

        if(responseStr.equals("Autorizado")) authorized = true;

        return authorized;
    }

    private void sendNotification(UserModel payee) throws Exception {

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6"))
                .POST(HttpRequest.BodyPublishers.ofString(payee.getUserEmail()))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());
        String responseStr = String.valueOf(jsonObject.get("message"));

        if(!responseStr.equalsIgnoreCase("true")){
            throw new Exception("Falha no envio de notificação");
        }
    }
    public Optional<UserModel> findById(Long payer) {

        return userRepository.findById(payer);

    }

    public void save(UserModel userModel) {

        userRepository.save(userModel);

    }

    public List<UserModel> findAll() {

        return userRepository.findAll();

    }
}
