package dev.shizhan.data;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanglikun
 */
public class DB {

    private List<Order> data = new ArrayList<>();

    private DB() {

    }

    public static DB create(int size) {
        DB db = new DB();
        db.data = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            db.data.add(new Order((long) i, OrderStatus.INIT));
        }
        return db;
    }


    public List<Order> selectAllInit(int size) {
        return data.stream()
                   .filter(d -> d.getStatus() == OrderStatus.INIT)
                   .map(d -> new Order(d.getId(), d.getStatus()))
                   .limit(size)
                   .collect(Collectors.toList());
    }

    public void update2Done(long id) {
        for (Order datum : data) {
            if (datum.getId().equals(id)) {
                datum.setStatus(OrderStatus.DONE);
                break;
            }
        }
    }

}
