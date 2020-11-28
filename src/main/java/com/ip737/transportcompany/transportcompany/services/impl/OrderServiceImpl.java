package com.ip737.transportcompany.transportcompany.services.impl;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.dao.DriverDao;
import com.ip737.transportcompany.transportcompany.data.dao.OrderDao;
import com.ip737.transportcompany.transportcompany.data.dao.VehicleDao;
import com.ip737.transportcompany.transportcompany.data.entities.Coordinates;
import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.data.entities.Vehicle;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        Vehicle car = vehicleDao.getOwnerId(order.getPlate());
        if (car == null) {
            throw new ValidationException(String.format(Constants.CAR_NOT_FOUND_WITH_PLATE, order.getPlate()));
        } else  if (car.getUserId() == null) {
            throw new ValidationException("The car with plate " + order.getPlate() + " hasn't been assigned to anyone yet");
        } else if (!driverDao.driverWorksThisDay(car.getUserId().toString(), order.getDeadline())) {
            throw new ValidationException("The driver with id " + car.getUserId() + " has a day off on this day");
        } else if (driverDao.getOrderCountForTheDay(order.getPlate(), order.getDeadline()) == Constants.MAX_DRIVER_ORDERS_FOR_DAY) {
            throw new ValidationException("The driver with id " + car.getUserId() + " already has max orders per day assigned for this day");
        }
        order =  orderDao.insert(order);
        return orderDao.getOrder(order.getOrderId());
    }

    @Override
    public void assignDriver(String orderId, String driverId) {
        Order order = orderDao.getOrder(orderId);
        if(order == null ) {
            throw new ValidationException(String.format(Constants.CAR_NOT_FOUND_WITH_PLATE, orderId));
        } else if(! order.getStatus().equals(Constants.Status.PENDING_CONFIRMATION.toString())){
            throw new ValidationException("The order with id " + orderId + " cannot be changed as status is " + order.getStatus());
        } else {
           Vehicle vehicle = vehicleDao.getByOwnerId(UUID.fromString(driverId));
            if(vehicle == null) {
                throw new ValidationException("The car for user with  id " + driverId + " not found");

            }
            orderDao.assignDriver(orderId, vehicle.getPlate());
        }
    }
    @Override
    public Order getOrder(String orderId) {
      return orderDao.getOrder(orderId);
    }

    @Override
    public void changeStatus(String orderId, String status, String driverId) {
        Order order = orderDao.getOrder(orderId);
        if(order == null ) {
            throw new ValidationException(String.format(Constants.ORDER_NOT_FOUND_WITH_ID, orderId));
        } else if (!order.getDriver_id().equals(driverId)) {
            throw new ValidationException(String.format(Constants.ORDER_WRONG_DRIVER, driverId, orderId));
        } else if(order.getStatus().equals(Constants.Status.PENDING_CONFIRMATION.toString()) &
                (status.equals(Constants.Status.REJECTED.toString()) | status.equals(Constants.Status.CONFIRMED.toString()))) {
            setNewStatus(order, status);
        } else if(order.getStatus().equals(Constants.Status.CONFIRMED.toString()) & status.equals(Constants.Status.STARTED.toString())) {
            setNewStatus(order, status);
        } else if(order.getStatus().equals(Constants.Status.STARTED.toString()) &
                (status.equals(Constants.Status.FAILED.toString()) | status.equals(Constants.Status.FINISHED.toString()))) {
            setNewStatus(order, status);
        } else {
            throw new ValidationException(String.format(Constants.ORDER_CANNOT_CHANGE_STATUS, orderId, status, order.getStatus()));
        }
    }

    private void setNewStatus(Order order, String status) {
        order.setStatus(status);
        orderDao.chageStatus(order);
    }
}
