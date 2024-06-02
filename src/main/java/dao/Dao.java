package dao;

import model.Employee;

public interface Dao {
    void connect();
    Employee getEmployee(int USER, String password);
    void disconnect();
}
