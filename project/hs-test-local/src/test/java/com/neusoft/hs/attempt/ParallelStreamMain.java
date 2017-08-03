package com.neusoft.hs.attempt;

import java.util.ArrayList;
import java.util.List;

public class ParallelStreamMain {

	public static void main(String[] args) {
		List<Integer> counts = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			counts.add(i);
		}
		counts.parallelStream().forEach(item -> {
			try {
				Thread.sleep(10000);
				System.out.println(Thread.currentThread().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
	}

}
