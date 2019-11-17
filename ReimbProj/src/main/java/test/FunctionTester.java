package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import daos.ReimbDao;
import models.Reimbursement;

public class FunctionTester {

	ReimbDao reimbDao = ReimbDao.currentImplementation;

	@Test
	public void testMakeReimb() {
		assertEquals(reimbDao.makeReimb(300, null, 41, 1),300);
	}

	@Test
	public void testFindAll() {
		List<Reimbursement> reimbs = reimbDao.findAll();
		Reimbursement tester = null;
		for (Reimbursement r : reimbs) {
			if (r.getReimbId() == 4) {
				tester = r;
			}
		}
		assertEquals(tester.getReimbId(), 4);
	}

	@Test
	public void testResolve() {
		assertEquals(reimbDao.resolve(4, 21, 2), 4);
	}	
}
