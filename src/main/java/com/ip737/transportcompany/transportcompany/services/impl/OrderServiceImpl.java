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
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Coordinates[] getPath(UUID id, Boolean update) {
        LinkedList<Coordinates[]> list = new LinkedList<Coordinates[]>();
        if(!update) list = orderDao.getPath(id);
        if(update || list == null || list.isEmpty()) {
            if(!update) update = (list == null || !list.isEmpty());
            //Coordinates [source, dest] = getFirstLastPoint(id);
            var temp = orderDao.getFirstLastPoint(id);
            Coordinates source = temp.source;
            Coordinates dest = temp.destination;
            //System.out.println(source.latitude);
            //System.out.println(dest.latitude);
            list = new LinkedList<Coordinates[]>();
            var url = "https://route.arcgis.com/arcgis/rest/services/World/Route/NAServer/Route_World/solve";
            var urlParameters = "f=json"
                    + "&findBestSequence=true"
                    + "&token=yA7X_-Sw63TFg5Tm-OKWDPcZrc4lU8rSSa8m-nGzD3tzO9mHE7CFHm0GETf4HvHLgB6Lb_aBHnyJcPxJH3IeSs5HjcvUS6R7I0eSPXFW_ga_TwiDCg6Uc38luJjoi0nliLxTxH_dbkLXFz8IZ3L2IKwLS_bEU0vogLz6qgp_kui_irK9utzh40fwPllY9g4IhilxE1k-TzvvJyu5GzGeCGJk4q22-xho7UotMlTxUtw."
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
                //System.out.println(res);
                Pattern regexPattern = Pattern.compile("\\[(\\[[0-9]*\\.[0-9]*,[0-9]*\\.[0-9]*\\],)*\\[[0-9]*\\.[0-9]*,[0-9]*\\.[0-9]*\\]\\]");
                Matcher matcher = regexPattern.matcher(res);
                matcher.find();
                String strArr = matcher.group();
                for(int i = 2, pos = 0; i < strArr.length(); i+=3, pos++) {
                    double x = 0;
                    long devider = 0;
                    char c = strArr.charAt(i);
                    while(c == '.' || ('0' <= c && c <= '9'))
                    {
                        if(c == '.')
                        {
                            devider = 1;
                        } else
                        {
                            x = 10*x + c-'0';
                            devider *= 10;
                        }
                        i++;
                        c = strArr.charAt(i);
                    }
                    x /= Math.max(1, devider);
                    double y = 0;
                    devider = 0;
                    i++;
                    c = strArr.charAt(i);
                    while(c == '.' || ('0' <= c && c <= '9'))
                    {
                        if(c == '.')
                        {
                            devider = 1;
                        } else
                        {
                            y = 10*y + c-'0';
                            devider *= 10;
                        }
                        i++;
                        c = strArr.charAt(i);
                    }
                    y /= Math.max(1, devider);
                    //System.out.printf("%f %f\n", x, y);
                    arr.add(new Coordinates(x, y));
                }
                Object[] objArr = arr.toArray();
                int length = objArr.length;
                Coordinates[] coorArr = new Coordinates[length];
                for(int i =0; i < length; i++) {
                    coorArr[i] = (Coordinates) objArr[i];
                }
                list.add(coorArr);
                con.disconnect();
            } catch(Exception e) {}
            list.add(new Coordinates[0]);
            if(update)
                orderDao.setPath(id, list.getFirst());
            else
                orderDao.insertPath(id, list.getFirst());
        }
        return list.getFirst();
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
