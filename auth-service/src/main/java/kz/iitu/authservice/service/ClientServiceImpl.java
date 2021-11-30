package kz.iitu.authservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import kz.iitu.authservice.model.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl {
    private final RestTemplate restTemplate;

    public ClientServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getUsersFallback",
            threadPoolKey = "getUsers",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="100"),
                    @HystrixProperty(name="maxQueueSize", value="50"),
            }
    )
    public List<ClientDTO> getUsers() {
        List<ClientDTO> list = restTemplate.getForObject("http://CLIENT-SERVICE/client/get-all",  List.class);
        return list;
    }

    public List<ClientDTO> getUsersFallback() {
        List<ClientDTO> list  = new ArrayList<ClientDTO>();
        return list;
    }
}
