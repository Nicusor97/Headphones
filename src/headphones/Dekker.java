/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package headphones;

/**
 *
 * @author nicol
 */
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Dekker {
	private volatile int turn;
	private AtomicIntegerArray flag = new AtomicIntegerArray(106);
	
	public Dekker() {
		for (int i = 0; i < 106; i++) {
			flag.set(i, 0);
		}
		turn = 0;
	}

	public void Lock(int t) {
		int other;

		other = 106 - 1 - t;
		flag.set(t, 1);
		while (flag.get(other) == 1) {
			if (turn == other) {
				flag.set(t, 0);
				while (turn == other)
					;
				flag.set(t, 1);
			}
		}
	}

	public void Unlock(int t) {
		turn = 106 - 1 - t;
		flag.set(t, 0);
	}


}
