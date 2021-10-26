package com.example.haircut.repository;

import com.example.haircut.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment,String> {
    Optional<Appointment> findAppointmentByApptID(String apptID);
    List<Appointment> findAppointmentByCusEmail(String cusEmail);

    Appointment findTopByOrderByIdDesc();
}
//Optional<T> mới được giới thiệu trong gói java.util.
// Nó được sử dụng để kiểm tra xem một biến có giá trị tồn tại giá trị hay không
//Mỗi collection là một Iterable. Ta có thể lấy đối tượng Iterable của nó để duyệt toàn bộ các phần tử trong collection.
//– Interface Collection kế thừa từ interface Iterable. interface Iterable định nghĩa phương thức iterator, trả về một iterator