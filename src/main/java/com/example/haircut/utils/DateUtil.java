package com.example.haircut.utils;

import com.example.haircut.model.Appointment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtil {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Date getCompleteDate(Date scheduleDate, Date apptDate) {
        try {
            //GET DATE AND TIME FROM APPOINTMENT AND THEN COMBINE THEM
            long TIME_OFF = 25200000;
            String date = dateFormat.format(new Date(apptDate.getTime() - TIME_OFF));
            String time = timeFormat.format(new Date(scheduleDate.getTime() - TIME_OFF));
            Date datetime = datetimeFormat.parse(date + " " + time);
            return datetime;
        } catch (Exception e) {
        }
        return new Date();
    }

    public boolean employeeScheduleValid(Date scheduleStart, Date scheduleEnd, Date apptStart, int duration) {
        Date scheduleStartDate = getCompleteDate(scheduleStart, apptStart);
        Date scheduleEndDate = getCompleteDate(scheduleEnd, apptStart);
        Date apptStartDate = apptStart;
        Date appEndDate = new Date(apptStart.getTime() + 60000 * duration);

        return scheduleStartDate.compareTo(apptStartDate) < 1 && scheduleEndDate.compareTo(appEndDate) > -1;
    }

    public boolean employeeAppoinmentValid(List<Appointment> appointments, Date apptDate, int duration) {

        //GET APPOINTMENT'S COMPLETE DATETIME
        Date apptStartDate = new Date(apptDate.getTime());
        Date apptEndDate = new Date(apptDate.getTime() + 60000 * duration);

        for (Appointment appt : appointments) {
            //GET OTHER APPOINTMENT(COMPARING APPOINTMENT) COMPLETE DATETIME
            Date otherStart = getCompleteDate(appt.getStartTime(), appt.getDate());
            Date otherApptStartDate = new Date(otherStart.getTime());
            Date otherApptEndDate = new Date(otherStart.getTime() + 60000 * appt.getTotalDuration());

            //CONFLICT CONDITIONS
            boolean firstCondition = otherApptStartDate.compareTo(apptStartDate) == 1 && otherApptStartDate.compareTo(apptEndDate) == -1;
            boolean secondCondition = otherApptEndDate.compareTo(apptStartDate) == 1 && otherApptEndDate.compareTo(apptEndDate) == -1;
            boolean thirdConditon = otherApptStartDate.compareTo(apptStartDate) < 1 && otherApptEndDate.compareTo(apptEndDate) > -1;
            boolean fourthCondition = otherApptStartDate.compareTo(apptStartDate) > -1 && otherApptEndDate.compareTo(apptEndDate) < 1;
            boolean isConflict = (firstCondition || secondCondition || thirdConditon || fourthCondition);

            if (isConflict) {
                return false;
            }

        }
        return true;
    }

    public List<Appointment> removeAppointment(List<Appointment> appointments, String apptID) {
        //REMOVE INPUTTED APPOINTMENT FROM THE LIST -> CHECK SAME APPOINTMENT MAY CAUSE ERROR
        for (Appointment appt : new ArrayList<>(appointments)) {
            if (appt.getApptID().equals(apptID)) {
                appointments.remove(appt);
                return appointments;
            }
        }
        return appointments;
    }
}
