package unit;

import models.OrderTCFS;
import models.TableTCFS;
import org.junit.Assert;
import org.junit.Test;
import play.test.Helpers;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alexander on 6/12/2015.
 */
public class TableTCFSTest extends CommonUnitTest{

    @Test
    public void testTables() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                List<TableTCFS> tables = TableTCFS.findAll();
                assertEquals(6, tables.size(), 0);
                for(TableTCFS table: tables){
                    TableTCFS newTable = TableTCFS.findById(table.id);
                    assertTrue(newTable.isActive);
                    assertEquals(table.id, newTable.id);
                    assertEquals(table.isActive, newTable.isActive);
                    assertEquals(table.reservations, newTable.reservations);
                }
            }
        });
    }
}