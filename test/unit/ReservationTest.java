package unit;

import com.avaje.ebean.Ebean;
import helpers.DateTimeHelper;
import models.OrderTCFS;
import models.Reservation;
import org.joda.time.DateTime;
import org.junit.Test;
import play.test.Helpers;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alexander on 6/12/2015.
 */
public class ReservationTest extends CommonUnitTest {

    @Test
    public void testReservation() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                Reservation reservation = new Reservation();
                String reservator = "Test reservator";
                DateTime dateTime = new DateTime().plusDays(1);
                Integer tableId = 2;
                reservation.setReservator(reservator);
                reservation.setStartAt(dateTime);
                DateTime created = DateTime.now();
                reservation.setCreatedAt(created);
                reservation.setTableId(tableId);
                reservation.setIsActive(true);
                Ebean.save(reservation);
                Reservation newReservation = Reservation.findById(reservation.getId());
                assertNotNull(newReservation);
                assertEquals(reservator, newReservation.getReservator());
                assertEquals(tableId, newReservation.getTableId(), 0);
                assertTrue(newReservation.isActive());
            }
        });
    }
}