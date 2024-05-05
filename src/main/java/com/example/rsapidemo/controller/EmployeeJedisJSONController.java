package com.example.rsapidemo.controller;

import com.example.rsapidemo.model.Employee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.json.Path2;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.*;

@RestController
@RequestMapping(path = "redisjson")
public class EmployeeJedisJSONController
{
    //@Autowired
    //EmployeeRepository employeeRepository;
    static UnifiedJedis client = new UnifiedJedis("redis://127.0.0.1:6379");

    static Gson gson = new Gson();

    //TODO is that safe as static ^^ ^^ ?

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
                    List.of(Employee.Colors.BLUE)
            );

            client.jsonSet("empl_json:"+i, gson.toJson(e));
        }
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees()
    {
        Set<String> keys = getAllKeys("empl_json:*", ScanParams.SCAN_POINTER_START, client);
        List<Employee> employeeList = new ArrayList<>(keys.size());
        for (String key : keys) {
            Employee e = gson.fromJson(client.jsonGet(key).toString(), TypeToken.get(Employee.class).getType());
            employeeList.add(e);
        }
        return employeeList;
    }

    @GetMapping("/employee/{id}")
    public Optional<Employee> getEmployee(@PathVariable String id)
    {
        Object json = client.jsonGet(id);
        if (json == null)
            return Optional.ofNullable(null);
        Employee e = gson.fromJson(json.toString(), TypeToken.get(Employee.class).getType());
        return Optional.of(e);
    }

    @PutMapping("/employee/{id}")
    public Optional<Employee> updateEmployee(@RequestBody Employee newEmployee, @PathVariable String id)
    {
        Object json = client.jsonGet(id);
        if (json != null) {
            client.jsonMerge(id, Path2.ROOT_PATH, gson.toJson(newEmployee));
            // assume we don't want to return the merged object but just the one that was given
            return Optional.of(newEmployee);
        } else {
            return Optional.ofNullable(null);
        }
    }

    @DeleteMapping(value = "/employee/{id}", produces = "application/json; charset=utf-8")
    public String deleteEmployee(@PathVariable String id)
    {
        long count = client.unlink(id);
        return "{ \"success\" : "+ ((count==1) ? "true" : "false") +" }";
    }

    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee newEmployee)
    {
        String id = String.valueOf(new Random().nextInt());
        Employee emp = new Employee(id, newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getEmail(), newEmployee.getFavoriteColors());
        client.jsonSet(id, gson.toJson(emp));
        return emp;
    }

    private Set<String> getAllKeys(String pattern, String cursor, UnifiedJedis jedisResource)
    {
        Set<String> keysSet = new HashSet<>();
        // Scan params used to construct arguments to the scan command
        ScanParams scanParams = new ScanParams()
                .count(3)//TODO hardcoded SAMPLE here
                .match(pattern);
        // fetch the result (keys returned) from the scanResult and add it to the
        // list of existing keys
        ScanResult<String> scanResult = jedisResource.scan(cursor,scanParams);
        keysSet.addAll(scanResult.getResult());

        // If the cursor returned by the scan result is not START(0) then
        // recursively call the function with returned cursor and aggregate the results
        if(!ScanParams.SCAN_POINTER_START.equals(scanResult.getCursor())){
            keysSet.addAll(getAllKeys(pattern,scanResult.getCursor(),jedisResource));
        }
        return keysSet;
    }
}