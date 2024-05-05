package com.example.rsapidemo.controller;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;
import org.springframework.web.bind.annotation.*;

import com.example.rsapidemo.model.Employee;

@RestController
@RequestMapping(path = "redisson")
public class EmployeeRedissonController
{
    //@Autowired
    //EmployeeRepository employeeRepository;
    static Config config = new Config();
    static {
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.setCodec(StringCodec.INSTANCE);
    }
    static RedissonClient client = Redisson.create(config);


    @GetMapping(value = "/healthcheck", produces = "application/json; charset=utf-8")
    public String getHealthCheck()
    {
        return "{ \"isWorking\" : true }";
    }

    @GetMapping("/init")
    public void init()
    {
        for (int i = 1; i <= 10; i++) {
            Employee e = new Employee(""+i+"_"+System.currentTimeMillis(),
                "Alex_" + System.currentTimeMillis() % 100,
                "Vasseur_" + i,
                "alex.vasseur@foo.com",
                    List.of(Employee.Colors.BLUE, Employee.Colors.RED)
            );
            // RClusteredMap is only available in Redisson PRO edition...
            // https://github.com/redisson/redisson/wiki/7.-distributed-collections#71-map

            // This will only store the objectname@uuid and not the actual values...
            RBucket<Employee> re0 = client.getBucket("empl_redisson_rbucket_nocodec:"+i);
            re0.set(e);

            // Java serialization is an opaque format
            RBucket<Employee> re1 = client.getBucket("empl_redisson_rbucket_serialization:"+i, new SerializationCodec());
            re1.set(e);

            // Redisson JSON codec does not handle nested arrays nicely
            RBucket<Employee> re2 = client.getBucket("empl_redisson_rbucket_json:"+i, JsonJacksonCodec.INSTANCE);
            re2.set(e);

            // Redisson boilerplate code to store in a Redis hashmap
            RMap<String, Object> emp = client.getMap("empl_redisson:"+i);
            Gson gson = new Gson();
            Object json = gson.toJson(e);
            Map<String, Object> hashmap = gson.fromJson(json.toString(), new TypeToken<Map<String, Object>>() {}.getType());
            emp.putAll(hashmap);
        }
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees()
    {
        //TODO
        List<Employee> employeesList = new ArrayList<Employee>();
        return employeesList;
    }

    @GetMapping("/employee/{id}")
    public Optional<Employee> getEmployee(@PathVariable String id)
    {
        //TODO
        Optional<Employee> emp = null;
        return emp;
    }

    @PutMapping("/employee/{id}")
    public Optional<Employee> updateEmployee(@RequestBody Employee newEmployee, @PathVariable String id)
    {
        //TODO
        return Optional.of(newEmployee);
    }

    @DeleteMapping(value = "/employee/{id}", produces = "application/json; charset=utf-8")
    public String deleteEmployee(@PathVariable String id)
    {
        //TODO
        Boolean result = Boolean.FALSE;
        return "{ \"success\" : "+ (result ? "true" : "false") +" }";
    }

    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee newEmployee)
    {
        //TODO
        return newEmployee;
    }
}