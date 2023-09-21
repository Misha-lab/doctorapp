package com.misha.doctorapp.helpers;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeslotsServiceHelper {
    /**
     * Функция добавления времени в минутах к объекту XMLGregorianCalendar.
     */
    public static XMLGregorianCalendar plusDuration(XMLGregorianCalendar curTime, int duration) {
        GregorianCalendar curTimeGC = curTime.toGregorianCalendar();
        curTimeGC.add(Calendar.MINUTE, duration);
        try {
            curTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(curTimeGC);
            curTime.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            return curTime;
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Функция преобразования объекта XMLGregorianCalendar к LocalDateTime.
     */
    public static LocalDateTime xmlGregorianCalendarToLocalDateTime(XMLGregorianCalendar time) {
        if (time == null)
            return null;
        return time.toGregorianCalendar().toZonedDateTime().toLocalDateTime().plusMinutes(0);
    }
}
