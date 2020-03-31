package pro.antonshu.managerconsole.cli;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pro.antonshu.managerconsole.ManagerConsoleApplication;
import pro.antonshu.dto.OrderDto;

@ShellComponent
public class OrderHandler {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @ShellMethod("Сhanging the status of an order that came from the store")
    public String changeOrderStatus(
            @ShellOption({"-ID", "--orderId"}) Long orderId,
            @ShellOption({"-STATUS", "--completeStatus"}) String completeStatus
    ) {
        Boolean status = Boolean.parseBoolean(completeStatus);
        System.out.println("CompleteStatus: " + status);
        OrderDto orderDto = new OrderDto(orderId, Boolean.parseBoolean(completeStatus));
        Gson gson = new Gson();
        String json = gson.toJson(orderDto);

        rabbitTemplate.convertAndSend(ManagerConsoleApplication.TOPIC_EXCHANGER_NAME, "processed", json);
        System.out.println("Sended String JSON: " + json);
        return "Status changed";
    }
}
