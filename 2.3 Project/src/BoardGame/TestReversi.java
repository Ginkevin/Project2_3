package BoardGame;

import static org.junit.Assert.*;

import org.junit.Test;
import BoardGame.Reversi;

public class TestReversi {

	@Test
	public void test() {
		Reversi.getReversi().setMoveHuman(21);
		boolean assertion = false;
		if (Reversi.getReversi().getBoardValue(21) == 0){
			assertion = true;
		}
		assertTrue(assertion);
		//fail("Not yet implemented");
	}

}
