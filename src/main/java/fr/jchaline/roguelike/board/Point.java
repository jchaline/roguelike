package fr.jchaline.roguelike.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {
	public int x;
	public int y;
	public int z;

	public Point(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point move(int x, int y, int z) {
		return new Point(this.x + x, this.y + y, this.z + z);
	}

	public List<Point> neighbors8() {
		List<Point> points = new ArrayList<>();

		for (int ox = -1; ox < 2; ox++) {
			for (int oy = -1; oy < 2; oy++) {
				if (ox == 0 && oy == 0)
					continue;

				points.add(new Point(x + ox, y + oy, z));
			}
		}

		Collections.shuffle(points);
		return points;
	}
	
	@Override
	public Point clone() {
		return new Point(this.x, this.y, this.z);
	}
	
	@Override
	public String toString() {
		return String.format("(%d,%d,%d)", x, y, z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point))
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
}
