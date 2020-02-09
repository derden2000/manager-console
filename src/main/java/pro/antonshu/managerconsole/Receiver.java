package pro.antonshu.managerconsole;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import pro.antonshu.dto.OrderDto;

@Component
public class Receiver {

    public void receiveMessage(String message) {
        Gson gson = new Gson();
        OrderDto order = gson.fromJson(message, OrderDto.class);
        System.out.println("Order received: " + order);
    }
}
