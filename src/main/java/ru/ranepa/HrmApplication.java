package ru.ranepa;

import ru.ranepa.presentation.Menu;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.service.HRMService;
import ru.ranepa.model.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HrmApplication {
    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();
        addTestEmployees(repository);
        HRMService service = new HRMService(repository);
        Menu menu = new Menu(service);

        menu.start();
    }

    private static void addTestEmployees(EmployeeRepository repository) {
        repository.save(new Employee(null, "Ariana", "manager",
                BigDecimal.valueOf(300000), LocalDate.of(2006, 9, 24)));

    }
}