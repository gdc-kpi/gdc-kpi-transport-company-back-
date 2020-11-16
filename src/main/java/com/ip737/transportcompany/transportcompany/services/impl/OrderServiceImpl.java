package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.dao.OrderDao;
import com.ip737.transportcompany.transportcompany.data.dao.VehicleDao;
import com.ip737.transportcompany.transportcompany.data.dao.impl.DriverDao;
import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    final private OrderDao orderDao;
    final private DriverDao driverDao;
    final private VehicleDao vehicleDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, DriverDao driverDao, VehicleDao vehicleDao) {
        this.orderDao = orderDao;
        this.driverDao = driverDao;
        this.vehicleDao = vehicleDao;
    }

    @Override
    public List<Coordinates> getPath(UUID id, Boolean update) {
        LinkedList<Coordinates> list = orderDao.getPath(id);
        if(update || list.isEmpty()) {
            update = list.isEmpty();
            var temp = orderDao.getFirstLastPoint(id);
            Coordinates source = temp.source;
            Coordinates dest = temp.destination;
            list = new LinkedList<Coordinates>();
            var url = "https://route.arcgis.com/arcgis/rest/services/World/Route/NAServer/Route_World/solve";
            var urlParameters = "f=json"
                    + "&findBestSequence=true"
                    + "&token=LeCam3ARaRjculBfCYmNkvB_YepfFY0OkplLPwHrx6zhH5-XK4TNk7dJh8RI5PD7lY11gIlA80ZFnbw3q-_8F5NmhLXJRfopC43Ash86LQume7Mh4lRXhjDQK9D6aOSur4thMZha0vs_uyY4d0Unbh7fRikpxxm0LYLGmPVjlvYv_kHp9g_z-xCJSfImjoU9aub3TiggKKKyVbP0tq2Oj8k2gpMCl1KXZDaLRTWFcME."
                    + "&stops={\"type\":\"features\",\"features\":[{\"geometry\": {\"y\": " + String.valueOf(source.latitude) + ",\"x\": " + String.valueOf(source.longitude) + "}}, {\"geometry\": {\"y\": " + String.valueOf(dest.latitude) + ",\"x\": " + String.valueOf(dest.longitude) + "}}]}";
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            try {
                LinkedList<Coordinates> arr = new LinkedList<Coordinates>();
                HttpURLConnection con;
                var myurl = new URL(url);
                con = (HttpURLConnection) myurl.openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "Java client");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                try (var wr = new DataOutputStream(con.getOutputStream())) {

                    wr.write(postData);
                }
                StringBuilder content;
                try (var br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

                    String line;
                    content = new StringBuilder();

                    while ((line = br.readLine()) != null) {
                        content.append(line);
                        content.append(System.lineSeparator());
                    }
                }
                String res = content.toString();
                JSONObject jo = new JSONObject(res);
                var strArr = jo.getJSONObject("routes").getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("paths").getJSONArray(0).toString();
                for(int i = 1; i < strArr.length(); i++) {
                    if(strArr.charAt(i) == '[') {
                        int next = strArr.indexOf(']', i);
                        arr.add(new Coordinates(strArr.substring(i, next)));
                        i = next;
                    }
                }

                list = arr;
                con.disconnect();
            } catch(Exception e) {
                System.out.println(e.toString());
            }
            if(update)
                orderDao.insertPath(id, list);
            else
                orderDao.setPath(id, list);
        }
        return list;
    }

    @Override
    public Order insertOrder(Order order) {
        Vehicle car = vehicleDao.getOwnerId(order.getCar_id());
        if (car == null) {
            throw new ValidationException("No car with plate " + order.getCar_id());
        } else  if (car.getUserId() == null) {
            throw new ValidationException("The car with plate " + order.getCar_id() + " hasn't been assigned to anyone yet");
        } else if (!driverDao.driverWorksThisDay(car.getUserId().toString(), order.getDeadline())) {
            throw new ValidationException("The driver with id " + car.getUserId() + " has a day off on this day");
        } else if (driverDao.getOrderCountForTheDay(order.getCar_id(), order.getDeadline()) == Constants.MAX_DRIVER_ORDERS_FOR_DAY) {
            throw new ValidationException("The driver with id " + car.getUserId() + " already has max orders per day assigned for this day");
        }

        return orderDao.insert(order);
    }
}
